package example.micronaut.mypoc.domain;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class Book {

    public Book() {}

    public Book(@NotNull String isbn, @NotNull String title, @NotNull String owner) {
        this.owner = owner;
        this.isbn = isbn;
        this.title = title;
    }

    private Long id;

    private String owner;

    private String isbn;


    private String title;

    private byte[] file;

    private Boolean processed = Boolean.FALSE;

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

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    //    public Set<BookPage> getBookPages() {
//        return bookPages;
//    }
//
//    // FIXME - remove method
//    public void setBookPages(Set<BookPage> bookPages) {
//        this.bookPages = bookPages;
//    }
//
//    public void addBookPages(BookPage[] bookPages){
//        for (BookPage p : bookPages ) {
//            this.getBookPages().add(p);
//
//        }
//    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}

