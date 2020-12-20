package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Book;
import data.Borrow;
import data.Borrower;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.BookService;
import service.BorrowerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("borrower")
public class BorrowerController {

    //-------------登录---------------
    @GetMapping("/login")
    public String borrowerLogin(){return "borrower/login";}

    @PostMapping("/login.do")
    public Object borrowerLogin(@RequestBody JSONObject jreq, Model model, HttpServletRequest req, HttpSession session){
        String username = jreq.getString("username");
        String password = jreq.getString("password");

        Borrower borrower = BorrowerService.getBorrower(Integer.parseInt(username));
        if(borrower==null){
            return  new AfRestError("用户不存在");
        }
        if(!borrower.getPw().equals(password)){
            return new AfRestError("密码错误");
        }
        Integer id = Integer.valueOf(username);
        session.setAttribute("borrower", id);
        System.out.println("登陆成功");
        return new AfRestData("");
    }

    //-------------个人信息---------------
    @GetMapping("/info")
    public Object borrowerInfo(HttpSession session,Model model){
        Integer borrower_id = (Integer)session.getAttribute("borrower");
        Borrower borrower = BorrowerService.getBorrower(borrower_id);
        model.addAttribute("borrower",borrower);
        return "borrower/info";
    }

    //-------------查询书籍---------------
    @GetMapping("/queryBooks")
    public String book()
    {
        return "borrower/book";
    }

    @PostMapping("/queryBooks")
    public Object bookQuery(Model model, HttpServletRequest request)
    {
        String bookName = request.getParameter("title");
        List<Book> bookList = BookService.findOnWord("",bookName,"","",1,5);
        if(bookList.size()==0){
            return new AfRestError("没有找到书籍");
        }
        model.addAttribute("bookList", bookList);
        return "borrower/book";
    }

    //-------------借阅信息---------------
    @GetMapping("reservation")
    public Object reserve(HttpSession session,Model model){
        Integer borrower_id = (Integer)session.getAttribute("borrower");
        List<Borrow> BorrowInfo = BorrowerService.getBorrow(borrower_id,1);
        if(BorrowInfo.size()==0){
            return new AfRestError("暂时没有借阅信息");
        }
        System.out.println("借书信息长度：  "+BorrowInfo.size());

        model.addAttribute("BorrowInfo", BorrowInfo);
        return "borrower/reservation";
    }

}
