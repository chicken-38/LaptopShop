package com.laptopshop.laptopshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "The name field is required.")
    private String name;
    @Positive(message = "Price must be greater than 0")
    private double price;
    private String image;
    @NotBlank(message = "The detail description field is required.")
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;
    @NotBlank(message = "The short description field is required.")
    private String shortDesc;
    @Positive(message = "Quantity must be greater than 0")
    private long quantity;
    private long sold;
    @ManyToOne
    @JoinColumn(name = "factory_id")
    private Factory factory;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Transient
    private String imageURL;
}
