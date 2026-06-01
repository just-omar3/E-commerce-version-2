package org.example.Ecommerce2.Product.ProductService;

import org.example.Ecommerce2.Product.ProductRepository.ProductRepository;
import org.example.Ecommerce2.Product.ProductModels.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product saveProductToDatabase(Product p) {
        return productRepo.save(p);
    }

    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepo.findAll(pageable);
    }

    public Optional<Product> findProductById(Long id) {
        return productRepo.findById(id);
    }

    public Product updateProduct(Product product) {

        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public Product modifyProductStock(Long id, Integer quantity) {
        Product product = productRepo.findById(id).orElseThrow();
        product.setStockQuantity(quantity);
        return productRepo.save(product);
    }
}