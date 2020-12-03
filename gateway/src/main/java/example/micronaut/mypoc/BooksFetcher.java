package example.micronaut.mypoc;

import example.micronaut.mypoc.domain.Book;
import io.micronaut.http.annotation.Body;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BooksFetcher {
    Flowable<Book> createBook(@Body Book book);
}
