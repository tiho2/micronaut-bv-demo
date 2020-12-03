package page.app;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import page.app.domain.Book;
import page.app.domain.BookProcessingStatusType;
import page.app.domain.Status;

import javax.activation.MimeType;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

@RabbitListener()
public class PageListener {


    private BookService bookService;


    public PageListener(BookService bookService) {
        this.bookService = bookService;
    }

    // @Header Long Id, @Header Integer offset, @Header Integer pageNumber, byte[] pages
    @Queue("book-processing")
    public void processPagePdf(Book bookCmd){
    

        System.out.println(bookCmd.getSrcFile().length);
        System.out.println(bookCmd.getPage());
        System.out.println(bookCmd.getId());
        char[]line = new char[50];
        Arrays.fill(line,'-');
        System.out.println(new String(line));
//        System.out.println("================");
//        bookService.outputBuckets();
//        System.out.println("================");

        //byte[] -> pdf

        try (
                PDDocument document = PDDocument.load(bookCmd.getSrcFile())
        ){
            //load() can throw exception
            ;
            Integer pageNumber = 0;
//            for (int i=0; i<document.getNumberOfPages(); i++) {
            //pageNumber = bookCmd.getPage() + i;

            // kopija iz prethodnog projekta

            ByteArrayOutputStream pdfPageOutputStream = new ByteArrayOutputStream();

            document.save(pdfPageOutputStream);

            System.out.println("page byte array length: " + pdfPageOutputStream.toByteArray().length);
            long start = System.nanoTime();
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim;


            bim = pdfRenderer.renderImageWithDPI(0, 78, ImageType.RGB);
            System.out.println("rendering image, ms: " + ((System.nanoTime() - start) / 1000000));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // reuse previous local variable
            ImageIOUtil.writeImage(bim, "jpg", byteArrayOutputStream, 300);

            byte[] pageImage = byteArrayOutputStream.toByteArray();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(pageImage.length);
            objectMetadata.setContentType("image/jpeg");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pageImage);

            String objectKey = bookCmd.getId() + "-" + bookCmd.getPage();
            bookService.storeObject(bookService.getBucketName(), objectKey, byteArrayInputStream, objectMetadata);

            String url = bookService.createPresignedUrl(bookService.getBucketName(), objectKey);
            System.out.format("PresignedURL: %s objectKey: %s \n", url, objectKey);
            System.out.println("Listener thread name: " + Thread.currentThread().getName());
            bookService.statusUpdate(new Status(bookCmd.getId(), BookProcessingStatusType.PAGE, bookCmd.getPage(), url));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            bookService.statusUpdate(new Status(bookCmd.getId(),Boolean.FALSE, e.getMessage()));
        }
//            } // for



        //pdf -> render image

        //save image
        // Spremiti u S3

        // Kreirati URL i poslati ga u statusu




    }
}
