package com.example.PraCodeProc.Services.Imp;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.OrderDto;
import com.example.PraCodeProc.Entites.Order;
import com.example.PraCodeProc.Repositories.*;
import com.example.PraCodeProc.Services.RefundOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundOrderServiceImp implements RefundOrderService {

    private final CustomerRespository customerRespository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;


    @Override
    public BaseResponse<?> getRefund(Integer orderId) {
        BaseResponse<Order> response = new BaseResponse<>();
        try{
            Order order = orderRepository.findById(orderId).orElse(null);


        }catch (Exception e){
            response.setMsg(e.getMessage());
        }
        return response;
    }
}
