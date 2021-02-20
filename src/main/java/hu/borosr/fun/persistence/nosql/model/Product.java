package hu.borosr.fun.persistence.nosql.model;

import hu.borosr.fun.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private BigDecimal price;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

    public static Product fromDto(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .createdAt(productDTO.getCreatedAt())
                .createdBy(productDTO.getCreatedBy())
                .build();
    }

    public ProductDTO toDto() {
        return ProductDTO.builder()
                .id(id)
                .name(name)
                .price(price)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .build();
    }
}
