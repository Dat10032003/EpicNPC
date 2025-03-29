package NguyenDat.EpicNPC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "NguyenDat.EpicNPC")
@EnableJpaRepositories(basePackages = "NguyenDat.EpicNPC.Repositories")
@EntityScan(basePackages = "NguyenDat.EpicNPC.Entities")
public class EpicNpcApplication {
	public static void main(String[] args) {
		SpringApplication.run(EpicNpcApplication.class, args);
	}
}


