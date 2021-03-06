package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.BookService;
import service.BorrowerServiceImpl;
import utility.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BorrowerController {

    @Autowired
    BorrowerServiceImpl borrowerServiceImpl;
    static  Map<Integer,String> message=new HashMap<>();
    static {
        message.put(-1,"服务器异常");
        message.put(0,"成功");
        message.put(1,"用户不存在");
        message.put(2,"存在不良记录");
        message.put(3,"已达最大借书量");
        message.put(4,"图书余量不足");
        message.put(5,"重复预约");
        message.put(6,"重复借阅");
    }
/*
处理注册
 */
    @PostMapping("/register.do")
    public  Object register(@RequestBody JSONObject jreq, HttpSession session)
    {

         String tem;
         Integer id=jreq.getInteger("id");
         if(BorrowerServiceImpl.isExistent(id)){
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
        borrower.setTel(tel);
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
            session.setAttribute("path",String.format("%010d",id)+"/"+fileName);
        }

        if(!(borrowerServiceImpl.add(borrower)))
        {
            return  new AfRestError("注册失败");
        };
        //报存用户信息到session

        session.setAttribute("userName",name);
        session.setAttribute("userID",id);
        session.setAttribute("level",1);
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
        Borrower borrower = borrowerServiceImpl.getBorrower(userID);

        if(borrower==null){
            return  new AfRestError("用户不存在");
        }
        if(!borrower.getPw().equals(password)){
            return new AfRestError("密码错误");
        }
        String userName=borrower.getName();
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
        Borrower borrower = borrowerServiceImpl.getBorrower(borrower_id);
        System.out.println("id值"+borrower.getBorrower_id());
        model.addAttribute("borrower",borrower);
        return "borrower/info";
    }

    @GetMapping("borrower/updateInfo")
    public String updateInfo(){return "borrower/updateInfo";}
    @PostMapping("/borrower/updateInfo")
    public Object borrowerUpdate(HttpSession session, HttpServletRequest request){
        Integer borrower_id = (Integer)session.getAttribute("userID");
        if(borrower_id==null) return new AfRestError("");
        Borrower borrower = borrowerServiceImpl.getBorrower(borrower_id);

        String name = request.getParameter("name");
        if(!name.equals("")){
            borrower.setName(name);
        }
        String adr = request.getParameter("adr");
        if(!adr.equals("")){
            borrower.setAdr(adr);
        }
        String tel = request.getParameter("tel");
        if(!tel.equals("")){
            borrower.setTel(tel);
        }
        String password = request.getParameter("pw");
        if(!password.equals("")){
            borrower.setPw(password);
        }
        String birth = request.getParameter("birth");
        if(!birth.equals("")){
            borrower.setBirth(java.sql.Date.valueOf(birth));
        }
        String sex = request.getParameter("sex");
        if(!sex.equals("")){
            if(sex.equals("1")){
                borrower.setSex(true);
            }else{
                borrower.setSex(false);
            }
        }
        BorrowerServiceImpl.update(borrower);
        
        return "borrower/updateInfo";
    }

    //-------------查询书籍---------------
    @GetMapping("/borrower/queryBooks")
    public String book()
    {
        return "book/list";
    }

    //-------------借阅信息---------------
    @GetMapping("/borrower/reservation")
    public Object reserve(HttpSession session,Model model){
        Integer borrower_id = (Integer)session.getAttribute("userID");
        List<Reservation> reservation = BorrowerServiceImpl.getReservation(borrower_id,1);
        model.addAttribute("reservation", reservation);
        return "borrower/reservation";
    }

    @PostMapping("/borrower/queryBooks")
    public Object bookQuery(Model model, HttpServletRequest request)
    {
        String bookName = request.getParameter("title");
        List<Book> bookList = BookService.findOnWord("",bookName,"","",1,5);
        if(bookList.equals(null) ||  bookList.size()==0){
            return new AfRestError("没有找到书籍");
        }
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
    /*
    预约
     */
    @PostMapping("/borrower/reserve")
    public Object reserve(@RequestBody JSONObject jreq,HttpSession session)
    {
        Integer book_id=jreq.getInteger("book_id");
        Integer userID=(Integer)session.getAttribute("userID");
        if (userID==null) return new AfRestError(-2,"未登录");
        int res= BorrowerServiceImpl.reserveBook(userID,book_id);
        if(res==0){
            return new AfRestData();
        }
        else return new AfRestError(message.get(res));

    }
    /*
    取消预约
     */
    @PostMapping("borrower/removeReservation")
    public void removeReservation(@RequestBody JSONObject jreq,HttpSession session)
    {
        Integer reservation_id = jreq.getInteger("reservation_id");
        try{
            BorrowerServiceImpl.removeReservation(reservation_id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    借书信息
     */
    @GetMapping("/borrower/borrowInfo")
    public String borrowInfo(HttpSession session,Model model)
    {
        Integer borrower_id = (Integer)session.getAttribute("userID");
        List<Borrow> reservation = BorrowerServiceImpl.getBorrow(borrower_id,1);
        model.addAttribute("reservation", reservation);
        return "borrower/borrowInfo";
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
