package hu.borosr.fun.persistence.common.repository;


import hu.borosr.fun.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void update(String id, String name, BigDecimal price);
    ProductDTO save(ProductDTO product);
    Optional<ProductDTO> findById(String id);
    void deleteById(String id);
    List<ProductDTO> findAll();
}
