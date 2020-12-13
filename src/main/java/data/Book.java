package data;

import af.sql.annotation.AFCOLUMNS;
import af.sql.annotation.AFTABLE;

@AFTABLE(name="book")
@AFCOLUMNS(generated="book_id")
public class Book {
    private Integer book_id;
    private String title;
    private Byte totalNum;
    private Byte presentNum;
    private String author;
    private String category;
    private Integer price;
    private String path;
    private String ISBN;
    private String description;

    public Byte getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Byte totalNum) {
        this.totalNum = totalNum;
    }

    public Byte getPresentNum() {
        return presentNum;
    }

    public void setPresentNum(Byte presentNum) {
        this.presentNum = presentNum;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
