package org.example.Ecommerce2.Product.ProductController;

import jakarta.validation.constraints.Min;
import org.example.Ecommerce2.Product.ProductService.ProductService;
import org.example.Ecommerce2.Product.ProductModels.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.getAllProducts(page, size);
        return ResponseEntity.ok(productPage);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {

        return productService.findProductById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN','PRODUCT_MANAGER')")
    public Product saveProduct(@RequestBody Product product) {

        return productService.saveProductToDatabase(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN','PRODUCT_MANAGER')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {

        product.setProductId(id);
        return productService.updateProduct(product);

    }

    @Transactional
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN','PRODUCT_MANAGER')")
    public void deleteProductById(@PathVariable Long id) {

        productService.deleteProduct(id);
    }

    @Transactional
    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN','PRODUCT_MANAGER')")
    public Product modifyProductStock(@PathVariable Long id, @RequestParam @Min(0) Integer stock) {
        return productService.modifyProductStock(id, stock);
    }
}