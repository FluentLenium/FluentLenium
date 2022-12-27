package io.fluentlenium.example.appium.config;

import io.fluentlenium.example.appium.device.Android;
import io.fluentlenium.example.appium.device.Iphone;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

//CHECKSTYLE.OFF: HideUtilityClassConstructor
@Configuration
@ComponentScan(value = {"io.fluentlenium.example.appium"})
@PropertySources(@PropertySource("config.properties"))
@SuppressWarnings("PMD.UseUtilityClass")
public class Config {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Profile("iphone")
    @Bean
    public Iphone iphone() {
        return new Iphone();
    }

    @Profile("android")
    @Bean
    public Android android() {
        return new Android();
    }

}
//CHECKSTYLE.ON: HideUtilityClassConstructor
