package com.example.PraCodeProc.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "TblProducts", schema = "dbo")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    Integer ProductID;

    @Column(name = "ProductName")
    String productName;

    @Column(name = "Description")
    String description;

    @Column(name = "Price")
    BigDecimal price;

    @Column(name = "StockQuantity")
    Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "CategoryID",foreignKey = @ForeignKey(name = "FK_Products_Category") )
    Category category;

    @Column(name = "CreatedDate")
    LocalDateTime createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Reviews> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}
