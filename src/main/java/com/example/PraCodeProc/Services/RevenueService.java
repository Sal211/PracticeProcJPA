package com.example.PraCodeProc.Services;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.GetRevenueResponse;
import com.example.PraCodeProc.Dtos.RevenueDto;
import org.springframework.stereotype.Service;

@Service
public interface RevenueService {
    BaseResponse<GetRevenueResponse<?>> getRevenue(RevenueDto revenueDto);
}
