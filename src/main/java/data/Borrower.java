package data;

import af.sql.annotation.AFCOLUMNS;
import af.sql.annotation.AFTABLE;

import java.sql.Date;
@AFTABLE(name="borrower")
@AFCOLUMNS(generated="borrower_id")
public class Borrower {
    private Integer borrower_id;
    private String name;
    private Integer sex;
    private Date birth;
    private String adr;
    private String path;
    private String tel;
    private String pw;
    private String badRecord;
    private Integer level;
    private Integer maxBook;
    private Integer borrowedNum;

    public Integer getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(Integer borrower_id) {
        this.borrower_id = borrower_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getBadRecord() {
        return badRecord;
    }

    public void setBadRecord(String badRecord) {
        this.badRecord = badRecord;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMaxBook() {
        return maxBook;
    }

    public void setMaxBook(Integer maxBook) {
        this.maxBook = maxBook;
    }

    public Integer getBorrowedNum() {
        return borrowedNum;
    }

    public void setBorrowedNum(Integer borrowedNum) {
        this.borrowedNum = borrowedNum;
    }
}
