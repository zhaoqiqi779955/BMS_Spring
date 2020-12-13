package service;

import af.sql.c3p0.AfSimpleDB;
import data.Book;
import data.LibrarySystem;
import utility.database.SqlUpdate;

import java.util.List;

public class BookService {
    /*
    向数据库添加一本书
     */
    public static void add(Book book)
    {
        try {
            AfSimpleDB.insert(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    根据id从数据库获取对应书籍
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
    /*
    根据id删除书籍
     */
    public static void delete(int id){
        String sql="delete from book where book_id="+id;
        try {
            AfSimpleDB.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    根据输入的关键字来查找书籍，关键字为图书类别或者书籍title,作者
    其中pageNumber为显示的第页码（从1起)，pageSize为每页的显示条数
     */
    public static List<Book> findOnWord(String word,int pageNumber,int pageSize)
    {
        int startIndex=(pageNumber-1)*pageSize;
        String query="select * from book where"+getLike("title",word)+"or"+getLike("author",word)+"or"+getLike("" +
                "category",word)+" order by book_id limit "+startIndex+","+pageSize;

        try {
            List<Book> bookList =AfSimpleDB.query(query,Book.class);
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static List<Book> findOnWord(String word,int pageNumber)
    {
        int pageSize= LibrarySystem.bookPageSize;
        int startIndex=(pageNumber-1)*pageSize;
        String query="select * from book where"+getLike("title",word)+"or"+getLike("author",word)+"or"+getLike("" +
                "category",word)+" order by book_id limit "+startIndex+","+pageSize;
        try {
            List<Book> bookList =AfSimpleDB.query(query,Book.class);
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public  static  String getLike(String column,String word)
    {
        String filter="%"+word+"%";
        return  String.format(" %s like '%s' ",column,filter);
    }
}