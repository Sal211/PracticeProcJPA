package com.example.PraCodeProc.Services.Imp;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.OrderDto;
import com.example.PraCodeProc.Entites.Order;
import com.example.PraCodeProc.Repositories.OrderRepository;
import com.example.PraCodeProc.Services.UpdateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UpdateOrderServiceImp implements UpdateOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public BaseResponse<?> updateOrder(OrderDto orderDto) {
        BaseResponse<Order> response = new BaseResponse<>();

        try{
            Order order = orderRepository.findById(orderDto.getOrderId()).orElse(null);
            if(order == null){
                response.setMsg("Invalid Customer");
                response.setStatus(false);
                return response;
            }

            if(order.getShippingDate() == null && orderDto.getOrderStatus().equals("Completed")){
                response.setMsg("Order Not Shipped Yet");
                response.setStatus(false);
                return response;
            }

            if(order.getShippingDate().isAfter(LocalDateTime.now()) && orderDto.getOrderStatus().equals("Completed")){
                response.setMsg("Order Not Shipped Yet");
                response.setStatus(false);
                return response;
            }

            order.setOrderStatus(orderDto.getOrderStatus());
            orderRepository.save(order);
            response.setMsg("Update Success");
            response.setStatus(true);
        }catch (Exception e){
            response.setMsg(e.getMessage());
            response.setStatus(false);
        }

        return response;
    }
}
