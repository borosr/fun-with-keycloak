package hu.borosr.fun.persistence.sql.entity;

import hu.borosr.fun.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @CreatedBy
    private User createdBy;
    @CreatedDate
    private LocalDateTime createdAt;

    public static Product fromDto(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .createdAt(productDTO.getCreatedAt())
                .build();
    }

    public ProductDTO toDto() {
        return ProductDTO.builder()
                .id(id)
                .name(name)
                .price(price)
                .createdAt(createdAt)
                .createdBy(createdBy.getUsername())
                .build();
    }
}
