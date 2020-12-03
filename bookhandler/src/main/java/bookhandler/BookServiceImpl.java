package bookhandler;

import bookhandler.domain.Book;
import bookhandler.domain.Status;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.Header;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.inject.Singleton;

@Singleton
public class BookServiceImpl implements BookService {
    private BookProcessing bookProcessing;
    private StatusUpdater statusUpdater;

    public BookServiceImpl(BookProcessing bookProcessing, StatusUpdater statusUpdater) {
        this.bookProcessing = bookProcessing;
        this.statusUpdater = statusUpdater;
    }

    @Override
    public void processPdfDocument(Long id, Integer offset, Integer pageNumber, Book pages) {
        bookProcessing.update(id, offset, pageNumber, pages);

    }

    @Override
    public void updateBookMetaData(Long id, Integer offset, Integer pageNumber, Status status) {
        statusUpdater.update(id, offset, pageNumber, status);
    }
}
