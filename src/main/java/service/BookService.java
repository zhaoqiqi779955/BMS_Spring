package service;


import pojo.Book;

import java.util.List;

public interface BookService {


    /*
        向数据库添加一本书
         */
    boolean add(Book book);

    /*
            根据id从数据库获取对应书籍
         */
    Book getBook(int id);

    /*
        更新书籍
         */
    void update(Book book);

    /*
        根据id删除书籍
         */
    boolean delete(int id);

    /*
        根据输入的关键字来查找书籍，关键字为图书类别或者书籍category,title,author,ISBN
        其中pageNumber为显示的第页码（从1起)，pageSize为每页的显示条数
         */
    List<Book> findOnWord(String category, String title, String autuor, String ISBN, int pageNumber, int pageSize);

    List<Book> findOnWord(String category, String title, String autuor, String ISBN, int pageNumber);

    //按照一个关键字查找书籍
    List<Book> findOnKeyWord(String filter, int pageNumber);
}
