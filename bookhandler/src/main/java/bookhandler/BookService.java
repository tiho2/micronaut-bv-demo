package bookhandler;

import bookhandler.domain.Book;
import bookhandler.domain.Status;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.inject.Singleton;


public interface BookService {

    /**
     * - opens uploaded file byte[] as pdf document
     * - write result to status queue, meta data (number of pages, title, - error (exception) in case of exception - write error
     * - takes pdf pages and sends them to queue for processing
     *
     * @param srcFile
     */
    void processPdfDocument(Long id, Integer offset, Integer pageNumber, Book pdfPage);

    void updateBookMetaData(Long id, Integer offset, Integer pageNumber, Status status);
}
