package com.baeldung.newstack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.newstack.NewResourceServerApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NewResourceServerApp.class })
public class ContextIntegrationTest {

    @Test
    public void whenLoadApplication_thenSuccess() {

    }

}