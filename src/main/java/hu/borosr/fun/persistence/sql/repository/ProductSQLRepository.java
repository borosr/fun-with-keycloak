package hu.borosr.fun.persistence.sql.repository;

import hu.borosr.fun.persistence.common.repository.ProductRepository;
import hu.borosr.fun.persistence.sql.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductSQLRepository extends ProductRepository, JpaRepository<Product, String> {
    @Override
    @Modifying
    @Query("update Product p set p.name = ?2, p.price = ?3 where p.id = ?1")
    void update(String id, String name, BigDecimal price);
}
