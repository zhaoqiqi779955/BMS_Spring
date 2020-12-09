package data;

import af.sql.annotation.AFCOLUMNS;
import af.sql.annotation.AFTABLE;

import java.util.Date;


@AFTABLE(name="borrow")
@AFCOLUMNS(generated="book_id")
public class Borrow {
    private Integer borrow_id;
    private Date date;
    private Integer borrower_id;
    private Integer book_id;
    private Date expire;
    boolean isReturned;

    public Integer getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(Integer borrow_id) {
        this.borrow_id = borrow_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(Integer borrower_id) {
        this.borrower_id = borrower_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
