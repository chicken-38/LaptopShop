package com.laptopshop.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laptopshop.laptopshop.domain.Category;
import com.laptopshop.laptopshop.domain.Factory;
import com.laptopshop.laptopshop.domain.Product;
import com.laptopshop.laptopshop.helper.ImageProcessing;
import com.laptopshop.laptopshop.repository.CategoryRepository;
import com.laptopshop.laptopshop.repository.FactoryRepository;
import com.laptopshop.laptopshop.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FactoryRepository factoryRepository;
    private final CategoryRepository categoryRepository;
    private final ImageProcessing imageProcessing;
    private final ImageStorageService imageStorageService;

    public ProductServiceImpl(ProductRepository productRepository, FactoryRepository factoryRepository,
            CategoryRepository categoryRepository, ImageProcessing imageProcessing,
            ImageStorageService imageStorageService) {
        this.productRepository = productRepository;
        this.factoryRepository = factoryRepository;
        this.categoryRepository = categoryRepository;
        this.imageProcessing = imageProcessing;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> products = this.productRepository.findAll();
        products.forEach(product -> product.setImageURL(this.imageProcessing.getUrl(product.getImage())));
        return products;
    }

    @Override
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }

    @Override
    public List<Factory> getAllFactory() {
        return this.factoryRepository.findAll();
    }

    @Override
    public void createProduct(Product product, MultipartFile file) {
        try {
            product.setImage(this.imageStorageService.storeFile(file));
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }
        this.productRepository.save(product);

    }

    @Override
    public Product getProductById(long id) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found."));
        product.setImageURL(this.imageProcessing.getUrl(product.getImage()));
        return product;
    }

    @Override
    public void deleteProductById(long id) {
        Product currentProduct = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found."));
        try {
            this.imageStorageService.deleteFileByName(currentProduct.getImage());
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }
        this.productRepository.deleteById(id);
    }

    @Override
    public String updateProduct(Product product, MultipartFile file) {
        Product currentProduct = this.productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found."));
        currentProduct.setPrice(product.getPrice());
        currentProduct.setShortDesc(product.getShortDesc());
        currentProduct.setDetailDesc(product.getDetailDesc());
        currentProduct.setQuantity(product.getQuantity() + currentProduct.getQuantity());
        try {
            String currentImage = currentProduct.getImage();
            currentProduct.setImage(this.imageStorageService.storeFile(file));
            this.imageStorageService.deleteFileByName(currentImage);
        } catch (Exception exception) {
            System.out.println(">>>> exception: " + exception.getMessage());
        }
        this.productRepository.save(currentProduct);
        return "Successfully updated product!";
    }

}
