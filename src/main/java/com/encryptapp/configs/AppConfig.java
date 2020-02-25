package com.encryptapp.configs;

import lombok.RequiredArgsConstructor;
import com.encryptapp.utils.cryptography.asymmetric.AsymmetricCryptography;
import com.encryptapp.utils.cryptography.keypair.GenerateKeys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Configuration
@PropertySource("classpath:private.properties")
@RequiredArgsConstructor
public class AppConfig {

    private final Environment environment;

    @PostConstruct
    public void execute() throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        String writeToFilePublicKey = this.environment.getProperty("writeToFilePublicKey");
        String writeToFilePrivateKey = this.environment.getProperty("writeToFilePrivateKey");
        GenerateKeys generateKeys = generateKeys();
        generateKeys.createKeys();
        generateKeys.writeToFile(writeToFilePublicKey, generateKeys.getPublicKey().getEncoded());
        generateKeys.writeToFile(writeToFilePrivateKey, generateKeys.getPrivateKey().getEncoded());
    }

    @Bean
    public GenerateKeys generateKeys() throws NoSuchProviderException, NoSuchAlgorithmException {
        String keylenght = this.environment.getProperty("keylenght");
        GenerateKeys generateKeys = new GenerateKeys(Integer.valueOf(keylenght));
        return generateKeys;
    }

    @Bean
    public AsymmetricCryptography asymmetricCryptography() throws Exception {
        AsymmetricCryptography asymmetricCryptography = new AsymmetricCryptography();
        return asymmetricCryptography;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }

}
