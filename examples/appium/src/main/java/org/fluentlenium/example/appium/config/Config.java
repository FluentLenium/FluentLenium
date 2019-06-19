package org.fluentlenium.example.appium.config;

import org.fluentlenium.example.appium.device.Android;
import org.fluentlenium.example.appium.device.Iphone;
import org.springframework.context.annotation.*;
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
