package com.example.PraCodeProc.Services;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.OrderDto;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface UpdateOrderService {
    BaseResponse<?> updateOrder(OrderDto orderDto);
}
