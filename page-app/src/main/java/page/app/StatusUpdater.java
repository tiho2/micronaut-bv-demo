package page.app;

import io.micronaut.http.annotation.Header;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import page.app.domain.Status;

// queue pages for further processing

// Producer
@RabbitClient("micronaut")
public interface StatusUpdater {
    @Binding("status")
    void update(Status status);

}
