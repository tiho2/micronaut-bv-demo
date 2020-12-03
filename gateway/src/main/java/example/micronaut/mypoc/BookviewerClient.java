package example.micronaut.mypoc;

import example.micronaut.mypoc.domain.Book;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import io.reactivex.Single;
// FIXME make it bookviewer client
@Client(id = "bookviewer")
@Requires(notEnv = Environment.TEST)
public interface BookviewerClient extends BooksFetcher {

    @Post("/books")
    Flowable<Book> createBook(@Body Book book);
}
