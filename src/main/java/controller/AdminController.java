package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AdminService;
import service.BorrowerService;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController
{
	@GetMapping("/admin/login")
	public String login()
	{
		return "admin/login";
	}
	
	@PostMapping("/admin/login.do")
	public Object login(@RequestBody JSONObject jreq
			, HttpSession session)
	{
		Integer userID = jreq.getInteger("userID");
		String password = jreq.getString("password");
		Admin admin= AdminService.getAdmin(userID);
		if(admin==null){
			return  new AfRestError("用户不存在");
		}
		if(!admin.getPw().equals(password)){
			return new AfRestError("密码错误");
		}
		// 登录成功
		session.setAttribute("userID", userID);
		session.setAttribute("userName",admin.getName());
		session.setAttribute("path",admin.getPath());
		session.setAttribute("level",admin.getLevel());
		return new AfRestData(admin.getLevel());
	}
	@RequestMapping("/librarian/login")
	public String librarianLogin()
	{
		return  "/librarian/login";
	}
	@RequestMapping("/librarian/workshop")
	public String goWorkShop()
	{
		return "/librarian/workshop";
	}
	@PostMapping("/librarian/borrow")
	public Object borrow(@RequestBody JSONObject jreq)
	{
		Integer book_id=jreq.getInteger("book_id");
		Integer userID=jreq.getInteger("borrower_id");
		int res= BorrowerService.borrowBook(userID,book_id);
		if(res==0){
			return new AfRestData();
		}
		else return new AfRestError(BorrowerController.message.get(res));
	}
	@PostMapping("/librarian/returnBook")
	public Object returnBook(@RequestBody JSONObject jreq)
	{
		System.out.println("还书");
		Integer book_id=jreq.getInteger("book_id");
		Integer userID=jreq.getInteger("borrower_id");
		int res= BorrowerService.returnBook(userID,book_id);
		if(res==1){
			return new AfRestData();
		}
		else return new AfRestError();
	}


}

