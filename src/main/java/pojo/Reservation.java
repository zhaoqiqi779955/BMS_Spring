package pojo;

import af.sql.annotation.AFCOLUMNS;
import af.sql.annotation.AFTABLE;

import java.util.Date;
@AFTABLE(name="reservation")
@AFCOLUMNS(generated="reservation_id")
public class Reservation {
    private Integer reservation_id;
    private Date date;
    private Integer borrower_id;
    private Integer book_id;
    private Date expire;
    private String title;

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
