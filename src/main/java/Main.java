import data.Book;
import service.BookService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        List<Book> bookList= BookService.findOnKeyWord("三国演义",1);
        System.out.println(bookList.get(0).getTitle());
    }

}
