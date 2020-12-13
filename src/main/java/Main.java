import service.BorrowerService;

public class Main {
    public static void main(String[] args) throws Exception {


//        BorrowerService.removeReservation(4,1);
//        System.out.println(BookService.getBook(1).getAuthor());
//        Book book= BookService.getBook(1);
//        book.setPresentNum((byte) 25);
//        BookService.update(book);
//        Borrower borrower= BorrowerService.getBorrower(1000);
//        System.out.println(borrower.getName());
//        BorrowerService.update(borrower);
//        System.out.println(BorrowerService.reserveBook(1000,1));
//        System.out.println(BorrowerService.borrowBook(1000,1));
//       List<Book> bookList= BookService.findOnWord(null,null,"罗贯中",null,1,10);
//       System.out.println(bookList.get(0).getAuthor());
        System.out.println(BorrowerService.getBorrow(1000,1).size());


    }

}
