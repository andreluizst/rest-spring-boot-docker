package br.com.restsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import br.com.restsb.config.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableAutoConfiguration // Permite que o application context do spring seja automaticamente carregado
							// baseado nos JARs e configurações que definimos. Isso é feito depois que os
							// beans forem registrados no application context
@ComponentScan // scaneia pacotes em busca de aruqivos de configuração. ex.: WebConfig.java
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);
	}
}
