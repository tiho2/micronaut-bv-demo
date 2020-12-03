package bookhandler;

import bookhandler.domain.Book;
import bookhandler.domain.BookProcessingStatusType;
import bookhandler.domain.Status;
import io.micronaut.context.annotation.Property;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.PDFMerger;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static bookhandler.domain.BookProcessingStatusType.BOOK;

@RabbitListener()
public class BookInput {

    final private static Logger LOG = LoggerFactory.getLogger(BookInput.class);

    List<String> messageLengths = Collections.synchronizedList(new ArrayList<>());
    private BookService bookService;

    public BookInput(BookService bookService) {
        this.bookService = bookService;
    }

    @Property(name = "application.batch.size")
    private Integer batchSize;

    @Queue("book-input")
    public void receive(Book book) {

        System.out.println("Java received " + book.getSrcFile().length + " bytes from RabbitMQ");
        Long id = book.getId();
        System.out.println("book id: " + book.getId());
        Status status = new Status();
        status.setId(book.getId());

        try (
                final PDDocument document = PDDocument.load(book.getSrcFile());
                ) {
            Integer numberOfPages = document.getNumberOfPages();
            status.setStatusType(BOOK);
            status.setNumberOfPages(document.getNumberOfPages());
            String title = document.getDocumentInformation().getTitle();
            status.setTitle(title);
            String author = document.getDocumentInformation().getAuthor();
            status.setAuthor(author);

            System.out.format("update book data: %d, %d, %s", id, status.getId(), status.getTitle());
            System.out.println();
            this.bookService.updateBookMetaData(id, 0,0,status);

            Integer counter = 0;

            //test block[

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(book.getSrcFile());

            System.out.println("mimeTye:" + URLConnection.guessContentTypeFromStream(byteArrayInputStream));
            //test block]


            Splitter splitter = new Splitter();
            long start = System.nanoTime();
            final List<PDDocument> pages = splitter.split(document);


            if (batchSize == null || batchSize < 1) {
                // batch processing is not used
                batchSize = pages.size();
            }
            LOG.info("batch size: " + batchSize);
            // FIXME remove outputs - durations are pritty acceptable
            System.out.println("splitting duration (ms): " + ((System.nanoTime() - start) / 1000000));
            // TODO 1 - create batch of pages & publish it
            // src PDDocument array -> buckets + conversion to byte[]

            int batchLength = pages.size() / batchSize;
            final int srcLength = pages.size();
            if (pages.size() % batchSize > 0) {
                batchLength++;
            }
            // create batches

            PDDocument[][] batch = new PDDocument[batchLength][];

            int batchIndex = 0;
            for (int j = 0; j < srcLength; j = j + batchSize) {
                final int startInclusive = j;
                int endExclusive = j + batchSize;
                if (endExclusive > srcLength) {
                    endExclusive = srcLength;
                }
                System.out.println("startInclusive=" + startInclusive);
                System.out.println("endExclusive=" + endExclusive);
                try (
                        PDDocument batchDoc = new PDDocument();
                ) {
                    start = System.nanoTime();
                    PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
                    for (int l = startInclusive; l < endExclusive; l++) {
                        // batch[batchIndex][l] = pages.get(l);
                        pdfMergerUtility.appendDocument(batchDoc, pages.get(l));

                    }
                    batchDoc.close();
                    System.out.println("PDF Merging time: " + (System.nanoTime() - start) / 1000000);
                }catch (IOException ioException) {
                    ioException.printStackTrace();
                }


                ++batchIndex;
            }
            Integer cnt = 1;
            for (PDDocument p : pages) {
                Book b = new Book();
                b.setSrcFile(documentToByteArray(p));
                b.setId(book.getId());
                b.setPage(cnt);
                // queue page for processing
                this.bookService.processPdfDocument(book.getId(), -1, cnt, b);
                cnt++;
                p.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();

            // FIXME - status, report processing progress, update meta data

        }



    }

    private byte[] documentToByteArray(PDDocument document) throws IOException {
        ByteArrayOutputStream pdfPageOutputStream = new ByteArrayOutputStream();
        document.save(pdfPageOutputStream);
        document.close();
        return pdfPageOutputStream.toByteArray();
    }


}