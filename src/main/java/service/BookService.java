package service;

import af.sql.c3p0.AfSimpleDB;
import data.Book;
import utility.database.SqlUpdate;

public class BookService {
    public static void add(Book book)
    {
        try {
            AfSimpleDB.insert(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    根据id获取对应书籍
 */
    public  static  Book getBook(int id){
        String sql="select * from book where book_id="+id;
        try {
           return (Book) AfSimpleDB.get(sql,Book.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
    更新书籍
     */
    public static  void update(Book book){
        SqlUpdate sql=new SqlUpdate("book");
        sql.add("title",book.getTitle());
        sql.add2("totalNum",book.getTotalNum());
        sql.add2("presentNum",book.getPresentNum());
        sql.add("author",book.getAuthor());
        sql.add("category",book.getCategory());
        sql.add2("price",book.getPrice());
        sql.add("path",book.getPath());
        sql.add("ISBN",book.getISBN());
        sql.add("description",book.getDescription());
        String s1 = sql + " where book_id=" + book.getBook_id();
        try {
            AfSimpleDB.execute(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void delete(int id){
        String sql="delete from book where book_id="+id;
        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
