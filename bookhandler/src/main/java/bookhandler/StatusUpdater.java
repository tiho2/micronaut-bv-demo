package bookhandler;

import bookhandler.domain.Book;
import bookhandler.domain.Status;
import io.micronaut.http.annotation.Header;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

// queue pages for further processing

// Producer
@RabbitClient("micronaut")
public interface StatusUpdater {
    @Binding("status")
    void update(@Header("Id") Long Id, @Header("offset") Integer offset, @Header("pageNumber") Integer pageNumber, Status status);

}
