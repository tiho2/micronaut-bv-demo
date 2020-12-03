package page.app.domain;

public class Status {
    // entity id
    private Long id;

    private BookProcessingStatusType statusType;
    // book processing status; false - page processing status
    private Boolean completeBookStatus;


    private boolean batchProcessing;
    private Integer batchSize;
    // metaData
    private String title;
    private Integer numberOfPages;
    // TODO: interest in some other meta data?

    private Integer pageNumber;
    private Boolean success;
    private String message;
    private Boolean finished;
    private String url;
    private String author;

    public Status() {
    }

    public Status(Long id, BookProcessingStatusType statusType, Integer pageNumber, String url) {
        this.id = id;
        this.statusType = statusType;
        this.pageNumber = pageNumber;
        this.url = url;
    }

    public Status(Long id, Boolean success, String message) {
        this.id = id;
        this.success = success;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookProcessingStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(BookProcessingStatusType statusType) {
        this.statusType = statusType;
    }

    public Boolean getCompleteBookStatus() {
        return completeBookStatus;
    }

    public void setCompleteBookStatus(Boolean completeBookStatus) {
        this.completeBookStatus = completeBookStatus;
    }

    public boolean isBatchProcessing() {
        return batchProcessing;
    }

    public void setBatchProcessing(boolean batchProcessing) {
        this.batchProcessing = batchProcessing;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}


