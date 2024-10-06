package com.example.PraCodeProc.Services.Imp;

import com.example.PraCodeProc.Dtos.*;
import com.example.PraCodeProc.Entites.*;
import com.example.PraCodeProc.Repositories.*;
import com.example.PraCodeProc.Services.OrderProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProductServiceimp implements OrderProductService {

    private final CustomerRespository customerRespository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ErrorRepository errorRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    @Override
    public BaseResponse<Void> getOrderProduct(OrderProductDto orderProductDto) {
        BaseResponse<Void> response = new BaseResponse<>();
        Map<String, Object> dataValidated;
        Integer stock;
        BigDecimal balance;
        BigDecimal totalPrice;

        try {
            // Retrieve  data
            Optional<Customers> customers = customerRespository.findById(orderProductDto.getCustomerId());
            Optional<Product> product = productRepository.findById(orderProductDto.getProductId());
            Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(orderProductDto.getPaymentMethodId());

            // Validate data
            dataValidated = validateData(customers, product, orderProductDto.getQuantity(), orderProductDto.getDiscount(), orderProductDto.getPaymentMethodId());
            totalPrice = (BigDecimal) dataValidated.get("totalPrice");

            if (!dataValidated.get("errMsg").toString().isEmpty()) {
                response.setMsg(dataValidated.get("errMsg").toString());
                return response;
            }

            // Set balance and stock values
            balance = orderProductDto.getPaymentMethodId() == 6 ? customers.get().getBalance().subtract(totalPrice) : customers.get().getBalance();
            stock = product.get().getStockQuantity() - orderProductDto.getQuantity();

            customers.get().setBalance(balance);
            product.get().setStockQuantity(stock);

            // Create  entities
            Order orderEntity = new Order();
            Payment paymentEntity = new Payment();

            OrderDto orderDto = OrderDto.builder()
                    .orderDate(LocalDateTime.now())
                    .customer(customers.get())
                    .orderStatus("Pending")
                    .shippingDate(null)
                    .build();

            PaymentDto paymentDto = PaymentDto.builder()
                    .paymentDate(LocalDateTime.now())
                    .amount(totalPrice)
                    .paymentMethod(paymentMethod.get())
                    .paymentStatus("Completed")
                    .build();

            // Copy properties
            BeanUtils.copyProperties(orderDto, orderEntity);
            BeanUtils.copyProperties(paymentDto, paymentEntity);

            OrderDetailDto orderDetailDto = OrderDetailDto.builder()
                    .order(orderEntity)
                    .payment(paymentEntity)
                    .product(product.get())
                    .quantity(orderProductDto.getQuantity())
                    .unitPrice(product.get().getPrice())
                    .discount(orderProductDto.getDiscount())
                    .totalPrice(totalPrice)
                    .build();

            OrderDetail orderDetailEntity = new OrderDetail();
            BeanUtils.copyProperties(orderDetailDto, orderDetailEntity);

            // Save entities
            orderRepository.save(orderEntity);
            paymentRepository.save(paymentEntity);
            orderDetailRepository.save(orderDetailEntity);
            productRepository.save(product.get());
            customerRespository.save(customers.get());

            response.setMsg("Success");
            response.setStatus(true);
        } catch (Exception e) {
            response.setMsg(e.getMessage());
            throw e; // Re-throw to ensure transaction rollback
        }

        return response;
    }

    private Map<String, Object> validateData(Optional<Customers> customers, Optional<Product> product, Integer quantity, Float discount, Integer paymentMethod){

        String errMsg = "";
        Integer errCode = 0;
        Map<String, Object> respone = new HashMap<>();
        Float tax = 10F;
        BigDecimal totalPrice = BigDecimal.ZERO;

        if(!customers.isPresent()) errCode = 4;
        if(!product.isPresent()) errCode = 12;
        if(product.get().getStockQuantity() < quantity) errCode = 2;

        totalPrice = product.get().getPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100))))
                .multiply(BigDecimal.ONE.add(BigDecimal.valueOf(tax).divide(BigDecimal.valueOf(100))));

        if(totalPrice.compareTo(customers.get().getBalance()) > 1 && paymentMethod == 6) errCode = 5;

        errMsg = errorRepository.findById(errCode).map(Errors::getErrMsg).orElse(null);
        respone.put("errMsg",errMsg);
        respone.put("totalPrice",totalPrice);
        return respone;
    }


}
