package hu.borosr.fun.persistence.common.repository;

import hu.borosr.fun.persistence.sql.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void update(String id, String name, BigDecimal price);
    Product save(Product product);
    Optional<Product> findById(String id);
    void deleteById(String id);
    List<Product> findAll();
}
