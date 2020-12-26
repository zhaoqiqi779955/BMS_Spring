import data.Book;
import service.BookService;

public class Main {
    public static void main(String[] args) throws Exception {

        Book ls=BookService.getBook(1);
       System.out.println(ls.getPrice());
        
    }

}
