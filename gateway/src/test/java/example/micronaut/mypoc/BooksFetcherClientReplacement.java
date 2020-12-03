//package example.micronaut.mypoc;
//
//import example.micronaut.mypoc.domain.Book;
//import io.micronaut.context.annotation.Requires;
//import io.micronaut.context.env.Environment;
//import io.micronaut.http.annotation.Body;
//import io.reactivex.Flowable;
//import io.reactivex.Single;
//
//import javax.inject.Singleton;
//
//@Requires(env = Environment.TEST)
//@Singleton
//public class BooksFetcherClientReplacement implements BooksFetcher {
//
//    @Override
//    public Flowable<Book> createBook(@Body Book book) {
//        book = new Book("11111","title n", "sherlock");
//        return Flowable.just(book);
//    }
//}
