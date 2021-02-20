package hu.borosr.fun.service;

import hu.borosr.fun.dto.ProductDTO;
import hu.borosr.fun.entity.Product;
import hu.borosr.fun.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO create(@NonNull ProductDTO productDTO) {
        return toDto(
                productRepository.save(toEntity(productDTO))
        );
    }

    public ProductDTO update(@NonNull String id, @NonNull ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.update(id, productDTO.getName(), productDTO.getPrice());
        return productDTO.toBuilder()
                .createdBy(product.getCreatedBy().getUsername())
                .createdAt(product.getCreatedAt())
                .build();
    }

    public void delete(@NonNull String id) {
        productRepository.deleteById(id);
    }


    public Optional<ProductDTO> findById(@NonNull String id) {
        return productRepository.findById(id).map(this::toDto);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private Product toEntity(ProductDTO product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .build();
    }

    private ProductDTO toDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy().getUsername())
                .build();
    }
}
