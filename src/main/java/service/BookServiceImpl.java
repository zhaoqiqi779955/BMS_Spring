package service;

import dao.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pojo.Book;
import pojo.LibrarySystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    /*
    向数据库添加一本书
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public  boolean add(Book book)
    {
       return bookMapper.add(book)==1 ? true: false;

    }
/*
    根据id从数据库获取对应书籍
 */
    @Override
    public  Book getBook(int id){
        return bookMapper.query(id);
    }
    /*
    更新书籍
     */
    @Override
    public  void update(Book book){
         bookMapper.update(book);

    }
    /*
    根据id删除书籍
     */
    @Override
    public boolean delete(int id){

        if(  bookMapper.delete(id) ==1) return true;
        else return false;

    }
    /*
    根据输入的关键字来查找书籍，关键字为图书类别或者书籍category,title,author,ISBN
    其中pageNumber为显示的第页码（从1起)，pageSize为每页的显示条数
     */
    @Override
    public  List<Book> findOnWord(String category, String title, String autuor, String ISBN, int pageNumber, int pageSize)
    {
        title=title.trim();
        category=category.trim();
        autuor=autuor.trim();
        ISBN=ISBN.trim();
        int startIndex=(pageNumber-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("title",title);
        map.put("category",category);
        map.put("author",autuor);
        map.put("ISBN",ISBN);
        map.put("start",startIndex);
        map.put("pageSize",pageSize);
        return bookMapper.queryAll(map);
    }
    @Override
    public List<Book> findOnWord(String category, String title, String autuor, String ISBN, int pageNumber)
    {
        return  findOnWord(category,title,autuor,ISBN,pageNumber,LibrarySystem.bookPageSize);
    }

    //按照一个关键字查找书籍
    @Override
    public  List<Book> findOnKeyWord(String filter, int pageNumber)
    {
        int pageSize= LibrarySystem.bookPageSize;
        int startIndex=(pageNumber-1)*pageSize;
        return bookMapper.queryAnyField(filter,startIndex,pageSize);


    }

}
