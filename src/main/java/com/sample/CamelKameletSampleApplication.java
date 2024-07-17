package com.sample;

import org.apache.camel.CamelContext;
import org.apache.camel.dsl.yaml.YamlRoutesBuilderLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelKameletSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamelKameletSampleApplication.class, args);
    }

    @Bean
    public YamlRoutesBuilderLoader yamlRoutesBuilderLoader(CamelContext camelContext) {
        YamlRoutesBuilderLoader loader = new YamlRoutesBuilderLoader();
        camelContext.getRegistry().bind("yaml", loader);
        return loader;
    }
}