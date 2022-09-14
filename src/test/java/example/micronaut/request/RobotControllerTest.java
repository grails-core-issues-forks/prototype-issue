package example.micronaut.request;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest // <1>
class RobotControllerTest {
    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void requestScopeScopeIsACustomScopeThatIndicatesANewInstanceOfTheBeanIsCreatedAndAssociatedWithEachHTTPRequest() {
        BlockingHttpClient client = httpClient.toBlocking();
        HttpResponse<List<String>> response = client.exchange(createRequest(), Argument.listOf(String.class));
        assertEquals(HttpStatus.OK, response.status());
        Set<String> responses = new HashSet<>(response.body());
        assertEquals(1, responses.size());

        response = client.exchange(createRequest(), Argument.listOf(String.class));
        assertEquals(HttpStatus.OK, response.status());
        responses.addAll(response.body());
        assertEquals(2, responses.size());
    }

    private HttpRequest<?> createRequest() {
        return HttpRequest.GET("/request").header("UUID", UUID.randomUUID().toString());
    }
}