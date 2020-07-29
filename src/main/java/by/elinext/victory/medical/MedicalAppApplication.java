package by.elinext.victory.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"by.elinext.victory.medical"})
public class MedicalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAppApplication.class, args);
    }

}
