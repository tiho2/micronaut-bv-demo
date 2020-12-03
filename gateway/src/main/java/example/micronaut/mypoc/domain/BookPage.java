package example.micronaut.mypoc.domain;

import javax.validation.constraints.NotNull;


public class BookPage {

    public BookPage() {}

    public BookPage(@NotNull Integer page, @NotNull String url, Book book) {
        this.page = page;
        this.url = url;
        this.book = book;
    }


    private Long id;

   private Integer page;

    private String url;


    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    
    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", page=" + page +
                ", url=" + url +
                ", book=" + book +
                '}';
    }
}