package epicode.BW5T1;

import com.cloudinary.Cloudinary;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("application.properties")//dove prendere le proprietà
public class AppConfig {
    @Bean

    public Cloudinary getCloudinary(@Value("${cloudinary.cloud_name}") String cloudName,
                                    @Value("${cloudinary.api_key}") String apiKey,
                                    @Value("${cloudinary.api_secret}") String apiSecret){
        Map<String, String> configCloudinary = new HashMap<>();
        configCloudinary.put("cloud_name",cloudName);
        configCloudinary.put("api_key", apiKey);
        configCloudinary.put("api_secret",apiSecret);

        return new Cloudinary(configCloudinary);
    }



    @Bean(name = "provinceCsvFile")
    public File provinceCsvFile() {
        return new File("src/main/resources/province-italiane.csv");
    }

    @Bean(name = "comuniCsvFile")
    public File comuniCsvFile() {
        return new File("src/main/resources/comuni-italiani.csv");
    }
}
