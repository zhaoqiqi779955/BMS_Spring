package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Book;
import data.Borrow;
import data.Borrower;
import data.LibrarySystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.BookService;
import service.BorrowerService;
import utility.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
public class BorrowerController {
/*
处理注册
 */
    @PostMapping("/register.do")
    public  Object register(@RequestBody JSONObject jreq, HttpSession session)
    {

         String tem;
         Integer id=jreq.getInteger("id");
         if(BorrowerService.isExistent(id)){
             return new AfRestError("账号已存在");
         }
         String name=jreq.getString("name");
         tem=jreq.getString("sex");
         Boolean sex=tem.endsWith("女");
         Date birth =jreq.getDate("birth");
         String tel=jreq.getString("tel");
         String password=jreq.getString("password");

        Borrower borrower=new Borrower();
        borrower.setName(name);
        borrower.setPw(password);
        borrower.setBorrower_id(id);
        borrower.setBirth(birth);
        borrower.setLevel(new Byte("1"));
        borrower.setSex(sex);
        borrower.setMaxBook((byte)LibrarySystem.MAX_Book);
        String path=(String) session.getAttribute("path");
        String fileName;
        if(path!=null)
        {
            String suffix= Util.getSuffix(path);
            fileName="head."+suffix;
            String dbPath=String.format("%010d",id)+"/"+fileName;
            borrower.setPath(dbPath);
            File src=new File(Common.tmpDir,path);
            File desDir=new File(Common.userFile,String.format("%010d",id));
            desDir.mkdirs();
            File desFile=new File(desDir,fileName);
            Common.exector.submit(new SaveFileTask(src,desFile));
            //报存用户信息到session
            session.setAttribute("path",String.format("%010d",id)+"/"+fileName);
            session.setAttribute("userName",name);
            session.setAttribute("userID",id);
            session.setAttribute("level",1);
        }

        if(!(BorrowerService.add(borrower)))
        {
            return  new AfRestError("注册失败");
        };

        return new AfRestData("");
    }
/*
注册
 */
    @GetMapping("/register")
    public String register()
    {
        return "borrower/register";
    }



    //-------------登录---------------
    @GetMapping("/borrower/login")
    public String borrowerLogin(){return "borrower/login";}

    @PostMapping("/borrower/login.do")
    public Object borrowerLogin(@RequestBody JSONObject jreq, Model model, HttpServletRequest req, HttpSession session){
        Integer userID = jreq.getInteger("userID");
        String password = jreq.getString("password");
        Borrower borrower = BorrowerService.getBorrower(userID);
        String userName=borrower.getName();
        if(borrower==null){
            return  new AfRestError("用户不存在");
        }
        if(!borrower.getPw().equals(password)){
            return new AfRestError("密码错误");
        }

        session.setAttribute("userID", userID);
        session.setAttribute("userName",userName);
        session.setAttribute("level",1);
        session.setAttribute("path",borrower.getPath());
        System.out.println("登陆成功");
        return new AfRestData("");
    }

    //-------------个人信息---------------
    @GetMapping("borrower/info")
    public Object borrowerInfo(HttpSession session,Model model){
        Integer borrower_id = (Integer)session.getAttribute("userID");
        if(borrower_id==null) return new AfRestError("");
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

    //-------------借阅信息---------------
    @GetMapping("/reservation")
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

    @PostMapping("/queryBooks")
    public Object bookQuery(Model model, HttpServletRequest request)
    {
        String bookName = request.getParameter("title");
        List<Book> bookList = BookService.findOnWord("",bookName,"","",1,5);
        if(bookList.size()==0){
            return new AfRestError("没有找到书籍");
        }
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
}
class SaveFileTask implements Runnable {
    File src = null;
    File des = null;

    public SaveFileTask(File src, File des) {
        this.src = src;
        this.des = des;
    }

    @Override
    public void run() {
        Common.saveFileTo(src, des);
    }

}
