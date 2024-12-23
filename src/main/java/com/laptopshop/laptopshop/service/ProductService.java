package com.laptopshop.laptopshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptopshop.domain.Category;
import com.laptopshop.laptopshop.domain.Factory;
import com.laptopshop.laptopshop.domain.Product;

public interface ProductService {
    public List<Product> getAllProduct();

    public List<Factory> getAllFactory();

    public List<Category> getAllCategory();

    public void createProduct(Product product, MultipartFile file);

    public Product getProductById(long id);

    public void deleteProductById(long id);

    public String updateProduct(Product product, MultipartFile file);
}
