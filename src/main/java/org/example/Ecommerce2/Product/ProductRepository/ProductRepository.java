package org.example.Ecommerce2.Product.ProductRepository;

import org.example.Ecommerce2.Product.ProductModels.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



}
