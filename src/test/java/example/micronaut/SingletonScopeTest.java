package example.micronaut;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest // <1>
class SingletonScopeTest {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void onlyOneInstanceOfTheBeanExistsForSingletonBeans() {
        BlockingHttpClient client = httpClient.toBlocking();
        Set<String> responses = new HashSet<>(executeRequest(client));
        assertEquals(1, responses.size());
        responses.addAll(executeRequest(client));
        assertEquals(1, responses.size());
    }

    List<String> executeRequest(BlockingHttpClient client) {
        return client.retrieve(HttpRequest.GET("/singleton"), Argument.listOf(String.class));
    }
}
