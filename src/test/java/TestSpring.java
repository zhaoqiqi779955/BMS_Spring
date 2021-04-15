import dao.BookMapper;
import dao.BorrowMapper;
import dao.BorrowerMapper;
import dao.ReservationMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pojo.Admin;
import pojo.Book;
import pojo.Borrower;
import pojo.Reservation;
import service.AdminService;
import service.BookServiceImpl;
import service.BorrowerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
//这个注解很重要，获取web项目配置
@WebAppConfiguration
@Transactional
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:mybatis-config.xml"})
public class TestSpring extends AbstractJUnit4SpringContextTests {

    @Autowired
     BorrowerService borrowerService;
    @Autowired
    BorrowerMapper borrowerMapper;
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookServiceImpl bookService;
    @Autowired
    AdminService adminService;
    @Test
    public void test1()
    {
        Borrower borrower = borrowerService.getBorrower(1000);

    }
    @Test
    public void test2()
    {
        List<Reservation> reservations=reservationMapper.queryByPage(1000,0,2);
        for (Reservation reservation : reservations) {
            System.out.println(reservation.getTitle());
        }
                
    }
    @Test
    public void test3()
    {
//        int res=reservationMapper.reserve(1000,1, LibrarySystem.RESERVATION_VALIDITY_TIME);
//        System.out.println(res);
        Map<String,Object> map=new HashMap<>();
        map.put("author","罗贯中");
        List<Book> bookList = bookMapper.queryAll(map);
        for (Book book : bookList) {
            System.out.println(book.getTitle());
        }

    }
    @Test
    public void test4()
    {
        Book book = bookMapper.query(1);
        book.setCategory("经典名著");
        book.setISBN("1536236");
        System.out.println(bookMapper.update(book));
    }
    @Test
    public void test5()
    {

        Admin admin=new Admin();
        admin.setWork_id(100);
        admin.setLevel( (byte) 2);
        admin.setPw("2355");
        adminService.add(admin);

    }
    
    
    


}
