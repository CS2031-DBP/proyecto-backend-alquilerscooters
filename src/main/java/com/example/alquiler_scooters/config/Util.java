package com.example.alquiler_scooters.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Util {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
