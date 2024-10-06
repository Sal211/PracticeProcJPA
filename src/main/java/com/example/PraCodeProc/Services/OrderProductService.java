package com.example.PraCodeProc.Services;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.OrderProductDto;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface OrderProductService {
    public BaseResponse<Void> getOrderProduct(OrderProductDto productDto);
}
