package example.micronaut.mypoc;

import example.micronaut.mypoc.domain.Book;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class UserControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testUserEndpointIsSecured() {
        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
            client.toBlocking().exchange(HttpRequest.GET("/books/list"));
        });

        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getResponse().getStatus());
    }

    @Test
    public void testAuthenticatedCanFetchUsername() {

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sherlock", "password");
        // bearer token "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaGVybG9jayIsIm5iZiI6MTYwNTI3MDUwMywicm9sZXMiOltdLCJpc3MiOiJnYXRld2F5IiwiZXhwIjoxNjA1MzU2OTAzLCJpYXQiOjE2MDUyNzA1MDN9.vRUSBiRxk4GQQEORAGwc9en94PSR8ljhzq26R9mXzRM"
        HttpRequest request = HttpRequest.POST("/login", credentials);

        BearerAccessRefreshToken bearerAccessRefreshToken = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);

        client.toBlocking().retrieve(HttpRequest.POST("/books",new Book("666666", "title", "sherlock"))
                .header("Authorization", "Bearer " + bearerAccessRefreshToken.getAccessToken()), String.class)
//                .body(new Book("666666", "title", "sherlock"))
        ;

        //assertNotNull(book);
    }
}