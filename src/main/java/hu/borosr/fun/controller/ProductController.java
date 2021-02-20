package hu.borosr.fun.controller;

import hu.borosr.fun.dto.ProductDTO;
import hu.borosr.fun.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@RolesAllowed({"ADMIN", "USER"})
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductDTO> findAll() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Optional<ProductDTO> findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable String id) {
        productService.delete(id);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("/products/{id}")
    public ProductDTO update(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }
}
