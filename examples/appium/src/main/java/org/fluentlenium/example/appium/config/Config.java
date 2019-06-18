package org.fluentlenium.example.appium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

//CHECKSTYLE.OFF: HideUtilityClassConstructor
@Configuration
@ComponentScan(value = {"org.fluentlenium.example.appium"})
@PropertySources(@PropertySource("config.properties"))
@SuppressWarnings("PMD.UseUtilityClass")
public class Config {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
//CHECKSTYLE.ON: HideUtilityClassConstructor
