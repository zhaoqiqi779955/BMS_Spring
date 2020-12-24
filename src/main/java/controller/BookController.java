package controller;

import data.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.BookService;

import java.util.List;

@Controller
public class BookController {

    /*
    头部的标题搜索框
     */
    @GetMapping("/search")
    public String query(Model model, @RequestParam("filter")  String filter,Integer pageNumber)
    {

        System.out.println("filter:"+filter+" NUM:"+pageNumber);
        List<Book> bookList= BookService.findOnKeyWord(filter,pageNumber);
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
}
