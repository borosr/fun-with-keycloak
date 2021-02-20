package hu.borosr.fun.persistence.nosql.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@EnableMongoRepositories(
    basePackages = "hu.borosr.fun.persistence.nosql.repository"
)
@ConditionalOnExpression("${app.mongodb.enabled:false}")
public class NoSQLConfiguration {
    private final Environment environment;

    @Bean
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(Optional.ofNullable(
                environment.getProperty("spring.data.mongodb.uri")
        ).orElseThrow());
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        return new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
    }

}
