package com.example.PraCodeProc.Services;

import com.example.PraCodeProc.Dtos.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface RefundOrderService {
    public BaseResponse<?> getRefund(Integer orderId);
}
