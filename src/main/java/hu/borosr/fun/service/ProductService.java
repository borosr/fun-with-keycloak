package hu.borosr.fun.service;

import hu.borosr.fun.dto.ProductDTO;
import hu.borosr.fun.persistence.common.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO create(@NonNull ProductDTO productDTO) {
        return productRepository.save(productDTO);
    }

    public ProductDTO update(@NonNull String id, @NonNull ProductDTO productDTO) {
        ProductDTO product = productRepository.findById(id).orElseThrow();
        productRepository.update(id, productDTO.getName(), productDTO.getPrice());
        return product.toBuilder().name(productDTO.getName()).price(productDTO.getPrice()).build();
    }

    public void delete(@NonNull String id) {
        productRepository.deleteById(id);
    }


    public Optional<ProductDTO> findById(@NonNull String id) {
        return productRepository.findById(id);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll();
    }
}
