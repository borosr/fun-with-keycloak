package hu.borosr.fun.persistence.nosql.repository;

import hu.borosr.fun.persistence.nosql.model.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnExpression("${app.mongodb.enabled:false}")
public interface ProductNoSQLRepository extends MongoRepository<Product, String> {
}
