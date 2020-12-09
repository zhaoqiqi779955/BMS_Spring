import data.Book;
import service.BookService;

public class Main {
    public static void main(String[] args) throws Exception {
        Book book= BookService.getBook(0);
        book.setAuthor("金庸");
        BookService.update(book);


    }

}
