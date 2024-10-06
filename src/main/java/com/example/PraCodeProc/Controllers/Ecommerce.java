package com.example.PraCodeProc.Controllers;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.OrderDto;
import com.example.PraCodeProc.Dtos.OrderProductDto;
import com.example.PraCodeProc.Dtos.RevenueDto;
import com.example.PraCodeProc.Services.OrderProductService;
import com.example.PraCodeProc.Services.RevenueService;
import com.example.PraCodeProc.Services.RevenueStatisticsService;
import com.example.PraCodeProc.Services.UpdateOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ABC")
public class Ecommerce {

    @Autowired
    private RevenueStatisticsService revenueStatisticsService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private UpdateOrderService updateOrderService;

    @GetMapping("/statistics")
    public List<Object[]> getRevenueStatistics(
            @RequestParam(name = "dateType", required = false) String pDateType,
            @RequestParam(name = "specificDate", defaultValue = "0") int pSpecificDate,
            @RequestParam(name = "customerId", defaultValue = "0") int pCustomerId,
            @RequestParam(name = "productId", defaultValue = "0") int pProductId,
            @RequestParam(name = "categoryId", defaultValue = "0") int pCategoryId,
            @RequestParam(name = "filterType", defaultValue = "") String pFilterType) {

        return revenueStatisticsService.getRevenueStatisticsService(pDateType, pSpecificDate, pCustomerId, pProductId, pCategoryId, pFilterType);
    }

    @PostMapping("/Order")
    public BaseResponse<Void> PostOrderProduct(@RequestBody OrderProductDto orderProductDto){
        return orderProductService.getOrderProduct(orderProductDto);
    }

    @GetMapping("/Revenue")
    public BaseResponse<?> GetRevenue(@RequestBody RevenueDto revenueDto){
        return revenueService.getRevenue(revenueDto);
    }

    @PostMapping("/OrderStatus")
    public BaseResponse<?> updateOrderStatus(@RequestBody OrderDto orderDto){
        return updateOrderService.updateOrder(orderDto);
    }
}
