package com.example.PraCodeProc.Services.Imp;

import com.example.PraCodeProc.Entites.*;
import com.example.PraCodeProc.Entites.Order;
import com.example.PraCodeProc.Repositories.CategoryRepository;
import com.example.PraCodeProc.Services.RevenueStatisticsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevenueStatisticsServiceimp implements RevenueStatisticsService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> getRevenueStatisticsService(String pDateType, int pSpecificDate, int pCustomerId, int pProductId, int pCategoryId, String pFilterType) {

        //  Building the Criteria Query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        // Creating the CriteriaQuery
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        // Defining the Query Elements
        Root<OrderDetail> orderDetailRoot = query.from(OrderDetail.class);

        // Join Table
        Join<OrderDetail, Order> orderJoin = orderDetailRoot.join("order", JoinType.INNER);
        Join<OrderDetail, Product> productJoin = orderDetailRoot.join("product", JoinType.INNER);
        Join<Order, Customers> customersJoin = orderJoin.join("customer", JoinType.INNER);
        Join<Product, Category> categoryJoin = productJoin.join("category", JoinType.INNER);

        // Select clause based on filter type
        if ("Category".equalsIgnoreCase(pFilterType)) {
            query.multiselect(
                    categoryJoin.get("CategoryID"),
                    categoryJoin.get("CategoryName"),
                    cb.sum(orderDetailRoot.get("TotalPrice")).alias("Revenue"),
                    cb.prod(cb.sum(orderDetailRoot.get("totalPrice")), 100).alias("AverageRevenue")
            );
            query.groupBy(categoryJoin.get("CategoryID"),categoryJoin.get("CategoryName"));
        } else if ("Customer".equalsIgnoreCase(pFilterType)) {
            query.multiselect(
                    customersJoin.get("customerId"),
                    customersJoin.get("firstName"),
                    customersJoin.get("lastName"),
                    cb.sum(orderDetailRoot.get("totalPrice")).alias("Revenue"),
                    cb.prod(cb.sum(orderDetailRoot.get("totalPrice")), 100).alias("AverageRevenue")
            );
            query.groupBy(customersJoin.get("customerId"), customersJoin.get("firstName"), customersJoin.get("lastName"));
        } else if ("Product".equalsIgnoreCase(pFilterType)) {
            query.multiselect(
                    productJoin.get("ProductID"),
                    productJoin.get("ProductName"),
                    cb.sum(orderDetailRoot.get("TotalPrice")).alias("Revenue"),
                    cb.prod(cb.sum(orderDetailRoot.get("totalPrice")), 100).alias("AverageRevenue")
            );
            query.groupBy(productJoin.get("ProductID"),productJoin.get("ProductName"));
        }

        // Where clause
        Predicate predicate = cb.conjunction();

        if(pSpecificDate > 0){
            predicate = cb.and(predicate, cb.equal(cb.function("DATEPART", Integer.class, cb.literal(pDateType), orderJoin.get("orderDate")), pSpecificDate));
        }

        if (pCustomerId > 0) {
            predicate = cb.and(predicate, cb.equal(customersJoin.get("customerId"), pCustomerId));
        }
        if (pCategoryId > 0) {
            predicate = cb.and(predicate, cb.equal(categoryJoin.get("categoryId"), pCategoryId));
        }
        if (pProductId > 0) {
            predicate = cb.and(predicate, cb.equal(productJoin.get("productId"), pProductId));
        }
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }


}
