package com.example.PraCodeProc.Services.Imp;

import com.example.PraCodeProc.Dtos.BaseResponse;
import com.example.PraCodeProc.Dtos.CustomerRevenueResponse;
import com.example.PraCodeProc.Dtos.GetRevenueResponse;
import com.example.PraCodeProc.Dtos.RevenueDto;
import com.example.PraCodeProc.Entites.Category;
import com.example.PraCodeProc.Entites.Customers;
import com.example.PraCodeProc.Entites.Errors;
import com.example.PraCodeProc.Entites.Product;
import com.example.PraCodeProc.Repositories.*;
import com.example.PraCodeProc.Services.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueServiceImp implements RevenueService {

    private final CustomerRespository customerRespository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ErrorRepository errorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BaseResponse<GetRevenueResponse<?>> getRevenue(RevenueDto revenueDto) {
        BaseResponse<GetRevenueResponse<?>> response = new BaseResponse<>();
        Map<String, Object> DateData = null;
        BigDecimal totalRevenue = BigDecimal.ZERO;
        LocalDateTime startDate, endDate;

        try {

            DateData = getDataDate(revenueDto.getSpecificDate(), revenueDto.getDateType());
            startDate = (LocalDateTime) DateData.get("startDate");
            endDate = (LocalDateTime) DateData.get("endDate");

            totalRevenue = orderDetailRepository.findTotalRevenueByOrderDateBetween(startDate, endDate);

            switch (revenueDto.getFilterType()) {
                case "Customer":
                    List<CustomerRevenueResponse> customers = getRevenuseCustomer(revenueDto.getCustomerId(), totalRevenue, startDate, endDate);
                    GetRevenueResponse<List<CustomerRevenueResponse>> customerRevenueResponse = new GetRevenueResponse<>();
                    customerRevenueResponse.setData(customers);
                    customerRevenueResponse.setTotalRevenue(totalRevenue);
                    response.setData(customerRevenueResponse);
                    break;
                case "Category":
                    // Code category
                    break;
                case "Product":
                    // Code Product
                    break;
            }
        } catch (Exception e) {
            response.setMsg(e.getMessage());
        }
        return response;
    }

    private Map<String, Object> getDataDate(LocalDateTime specificDate, String DateType) {

        Map<String, Object> response = new HashMap<>();

        if (specificDate != null) {
            response.put("startDate", specificDate);
            response.put("endDate", specificDate);
            return response;
        } else if ("Month".equals(DateType)) {
            response.put("startDate", LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0));
            response.put("endDate", LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999));
            return response;
        }

        response.put("startDate", LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
        response.put("endDate", LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999));
        return response;
    }

    private List<CustomerRevenueResponse> getRevenuseCustomer(Integer customerId, BigDecimal totalRevenue, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        List<CustomerRevenueResponse> response = new ArrayList<>();

        if (customerId > 0) {
            Optional<Customers> customers = customerRespository.findById(customerId);
            if (!customers.isPresent()) {
                throw new Exception(getErrMessage(4));
            }

            // Write Logic (CustomerID,FirstName,LastName,Sum,PercentageOfTotal)
            BigDecimal totalCustomerRevenue = customerRespository.findTotalRevenueByOrderDateBetweenById(startDate, endDate,customerId);
            Float percentageOfTotal = (totalCustomerRevenue.multiply(BigDecimal.valueOf(100)).subtract(totalRevenue)).floatValue();
            CustomerRevenueResponse customerRevenueResponse = CustomerRevenueResponse.builder()
                    .customerId(customerId)
                    .totalRevenue(totalCustomerRevenue)
                    .FullName(customers.get().getFirstName() + "" + customers.get().getLastName())
                    .percentageOfTotal(percentageOfTotal)
                    .build();

            response.add(customerRevenueResponse);
            return response;
        }

        response = customerRespository.findAllByOrderDate(startDate,endDate).stream()
                .map(customer -> {
                    BigDecimal totalCustomerRevenue = (BigDecimal) customer[3];
                    float percentageOfTotal = (totalCustomerRevenue.multiply(BigDecimal.valueOf(100)).subtract(totalRevenue)).floatValue();

                    return CustomerRevenueResponse.builder()
                            .customerId((Integer) customer[0])
                            .totalRevenue(totalCustomerRevenue)
                            .FullName( customer[1] + "" +  customer[2])
                            .percentageOfTotal(percentageOfTotal)
                            .build();
                })
                .collect(Collectors.toList());

        return response;
    }

    private String getErrMessage(Integer errCode) {
        return errorRepository.findById(errCode).map(Errors::getErrMsg).orElse(null);
    }

}
