package com.baeldung.newstack.spring;

import java.util.Map;

import org.springframework.core.convert.converter.Converter;

public class UsernameClaimConverter implements Converter<Object, Map<String, String>> {

    @Override
    public Map<String, String> convert(Object source) {
        String[] email = ((String) source).split("@");
        return Map.of("username", email[0], "domain", email.length >= 2 ? email[1] : "");
    }

}
