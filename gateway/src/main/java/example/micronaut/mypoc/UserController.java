package example.micronaut.mypoc;

import example.micronaut.mypoc.domain.Book;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Controller("/books")
public class UserController {

    private final BooksFetcher booksFetcher;

    public UserController(BooksFetcher booksFetcher) {
        this.booksFetcher = booksFetcher;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Produces(MediaType.APPLICATION_JSON)
    @Post
    Flowable<Book> index(@Body Book book) {
        return booksFetcher.createBook(book);
    }
}
