package com.baeldung.newstack.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class UsernameClaimConverterUnitTest {

    private UsernameClaimConverter usernameClaimConverter = new UsernameClaimConverter();

    @Test
    public void givenClaimWithEmailFormat_whenMapped_thenClaimConvertedToMapWithDomain() throws Exception {
        String originalValue = "user@baeldung.com";

        Map<String, String> convertedValue = usernameClaimConverter.convert(originalValue);

        assertThat(convertedValue).containsEntry("username", "user")
            .containsEntry("domain", "baeldung.com");
    }

    @Test
    public void givenClaimWithoutEmailFormat_whenMapped_thenClaimConvertedToMapWithoutDomain() throws Exception {
        String originalValue = "user123";

        Map<String, String> convertedValue = usernameClaimConverter.convert(originalValue);

        assertThat(convertedValue).containsEntry("username", "user123")
            .containsEntry("domain", "");
    }
}
