package com.freelance.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(ctx -> {
                    Object value = ctx.getSource();
                    if (value instanceof String) {
                        return !((String) value).isBlank();
                    }
                    return value != null;
                });        return mapper;
    }
}