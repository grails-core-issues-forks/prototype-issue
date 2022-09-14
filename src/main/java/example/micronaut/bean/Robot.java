package example.micronaut.bean;

import io.micronaut.context.annotation.Bean;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.runtime.http.scope.RequestAware;

import java.util.Objects;

@Bean
public class Robot implements RequestAware {
    private String serialNumber;

    @NonNull
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setRequest(HttpRequest<?> request) {
        serialNumber = Objects.requireNonNull(request.getHeaders().get("UUID"));
    }
}
