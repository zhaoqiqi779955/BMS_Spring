package controller;

import data.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BookController {

    /*
    头部的标题搜索框
     */
    @GetMapping("/search")
    public String query(Model model, @RequestParam("filter")  String filter,Integer pageNumber)
    {


        List<Book> bookList= BookService.findOnKeyWord(filter,pageNumber);
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
    @PostMapping("/book/query")
    public String bookQuery(Model model, Integer pageNumber, HttpServletRequest request)
    {

        String bookName = request.getParameter("title");
        String author=request.getParameter("author");
        String ISBN=request.getParameter("ISBN");
        String category=request.getParameter("category");
        List<Book> bookList = BookService.findOnWord(category,bookName,author,ISBN,1,5);
        model.addAttribute("bookList", bookList);
        return "book/list";

    }
}
