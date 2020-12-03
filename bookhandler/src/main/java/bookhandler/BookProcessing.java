package bookhandler;

import bookhandler.domain.Book;
import io.micronaut.http.annotation.Header;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import org.apache.pdfbox.pdmodel.PDPage;

// queue pages for further processing

// Producer
@RabbitClient("micronaut")
public interface BookProcessing {
    @Binding("book-processing")
    void update(@Header("Id") Long Id, @Header("offset") Integer offset, @Header("pageNumber") Integer pageNumber, Book pages);

}
