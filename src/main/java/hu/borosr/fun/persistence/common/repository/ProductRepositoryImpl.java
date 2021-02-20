package hu.borosr.fun.persistence.common.repository;

import hu.borosr.fun.dto.ProductDTO;
import hu.borosr.fun.persistence.nosql.repository.ProductNoSQLRepository;
import hu.borosr.fun.persistence.sql.entity.Product;
import hu.borosr.fun.persistence.sql.repository.ProductSQLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Value("${app.mongodb.enabled:false}")
    private boolean nosqlEnabled;
    private final ProductSQLRepository productSQLRepository;
    private final ProductNoSQLRepository productNoSQLRepository;

    public ProductRepositoryImpl(
            @Autowired(required = false) ProductSQLRepository productSQLRepository,
            @Autowired(required = false) ProductNoSQLRepository productNoSQLRepository) {
        this.productSQLRepository = productSQLRepository;
        this.productNoSQLRepository = productNoSQLRepository;
    }

    @Override
    public void update(String id, String name, BigDecimal price) {
        if (nosqlEnabled) {
            productNoSQLRepository.findById(id).ifPresent(product ->
                    productNoSQLRepository.save(product.toBuilder()
                            .name(name)
                            .price(price)
                            .build())
            );
        } else {
            productSQLRepository.update(id, name, price);
        }
    }

    @Override
    public ProductDTO save(ProductDTO product) {
        return nosqlEnabled ?
                productNoSQLRepository.save(hu.borosr.fun.persistence.nosql.model.Product.fromDto(product)).toDto() :
                productSQLRepository.save(Product.fromDto(product)).toDto();
    }

    @Override
    public Optional<ProductDTO> findById(String id) {
        return nosqlEnabled ?
                productNoSQLRepository.findById(id).map(hu.borosr.fun.persistence.nosql.model.Product::toDto) :
                productSQLRepository.findById(id).map(Product::toDto);
    }

    @Override
    public void deleteById(String id) {
        if (nosqlEnabled) {
            productNoSQLRepository.deleteById(id);
        } else {
            productSQLRepository.deleteById(id);
        }
    }

    @Override
    public List<ProductDTO> findAll() {
        return nosqlEnabled ?
                productNoSQLRepository.findAll().stream()
                        .map(hu.borosr.fun.persistence.nosql.model.Product::toDto).collect(Collectors.toList()) :
                productSQLRepository.findAll().stream().map(Product::toDto).collect(Collectors.toList());
    }

}
