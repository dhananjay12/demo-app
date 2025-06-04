package com.djcodes.spring.demoapp;

import com.djcodes.spring.demoapp.openapi.model.BookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.docker.compose.enabled=true"
})
class BookControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndGetBook() {
        // Step 1: Create a book
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("E2E Testing with Docker Compose");
        bookRequest.setAuthor("John Doe");
        bookRequest.setIsbn("978-3-16-148410-0");
        bookRequest.setPublicationYear(2023);

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/books", bookRequest, Void.class);
        assertThat(createResponse.getStatusCode().is2xxSuccessful()).isTrue();

        // Step 2: Retrieve the book
        ResponseEntity<Map> getResponse = restTemplate.getForEntity("/books/1", Map.class);
        assertThat(getResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(getResponse.getBody()).containsEntry("title", "E2E Testing with Docker Compose");
        assertThat(getResponse.getBody()).containsEntry("author", "John Doe");
    }

}
