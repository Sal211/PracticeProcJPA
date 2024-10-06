package com.example.PraCodeProc.Services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RevenueStatisticsService {
    public List<Object[]> getRevenueStatisticsService(String pDateType, int pSpecificDate, int pCustomerId, int pProductId, int pCategoryId, String pFilterType) ;

}
