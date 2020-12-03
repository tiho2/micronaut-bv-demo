package bookhandler.domain;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Introspected
public class Book {

    public Book() {
    }

    public Book(String isbn, String title, String owner) {
        this.owner = owner;
        this.isbn = isbn;
        this.title = title;
    }

    private Long id;
    private Integer page;
    private Integer offset = -1;

    private String owner;

    private String isbn;


    private String title;

    private Boolean processed = Boolean.FALSE;


    private byte[] srcFile;

    private byte [][] pages;

    private Set<BookPage> bookPages = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String user) {
        this.owner = user;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public byte[] getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(byte[] srcFile) {
        this.srcFile = srcFile;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public byte[][] getPages() {
        return pages;
    }

    public void setPages(byte[][] pages) {
        this.pages = pages;
    }

    public Set<BookPage> getBookPages() {
        return bookPages;
    }

    public void setBookPages(Set<BookPage> bookPages) {
        this.bookPages = bookPages;
    }



    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title +
                ", isbn='" + isbn +
                ", srcFileLength='" + srcFile.length +
                '}';
    }
}

