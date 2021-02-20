package hu.borosr.fun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
		exclude = {
				MongoAutoConfiguration.class,
				MongoDataAutoConfiguration.class,
				JpaRepositoriesAutoConfiguration.class,
		}
)
public class FunApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunApplication.class, args);
	}

}
