package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Admin;
import data.Book;
import data.Borrower;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AdminService;
import service.BookService;
import service.BorrowerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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
/*
图书管理员
 */
@RequestMapping("/librarian/login")
public String librarianLogin()
{
	return  "/librarian/login";
}
/*
工作台
 */
@RequestMapping("/librarian/workshop")
public String goWorkShop()
{
	return "/librarian/workshop";
}
//	处理借书
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
	/*
	个人信息
	 */
	@GetMapping("/admin/info")
	public Object info(HttpSession session, Model model)
	{

		Integer userID = (Integer)session.getAttribute("userID");
		Admin admin = AdminService.getAdmin(userID);
		model.addAttribute("admin",admin);
		System.out.println("准备展示的个人信息。。。。。");
		return "admin/info";
	}

	/*
	查询书籍
	 */
	@GetMapping("/admin/queryBooks")
	public String book()
	{
		return "admin/deleteBook";
	}

	@PostMapping("/admin/queryBooks")
	public Object bookQuery(Model model, HttpServletRequest request)
	{

		System.out.println(request.getParameter("title"));
		List<Book> books = BookService.findOnWord("",request.getParameter("title"),"","",1,5);
		if(books.equals(null) ||  books.size()==0){
			return new AfRestError("没有找到书籍");
		}
		model.addAttribute("bookList", books);
		return "admin/deleteBook";

	}

	/*
	添加书籍
	 */
	@GetMapping("/admin/addBook")
	public String addBook(){
		return "admin/addBook";
	}

	@PostMapping("/admin/addBook")
	public Object addBook(HttpServletRequest request){
		String title = request.getParameter("title");
		String totalNum = request.getParameter("totalNum");
		String author = request.getParameter("author");
		String category = request.getParameter("category");
		String price = request.getParameter("price");
		String ISBN = request.getParameter("ISBN");
		String desc=request.getParameter("desc");
		Book book = new Book();
		book.setTitle(title);
		book.setTotalNum(Byte.valueOf(totalNum));
		book.setPresentNum(Byte.valueOf(totalNum));
		book.setAuthor(author);
		book.setCategory(category);
		book.setPrice(Integer.valueOf(price));
		book.setDescription(desc);
		book.setISBN(ISBN);

		boolean res=BookService.add(book);
		if(!res) return new AfRestError("添加失败");
		return "admin/addBook";
	}
	/*
	删除书籍
	 */
	@GetMapping("/admin/deleteBook")
	public String deleteBook(){
		return "admin/deleteBook";
	}

	@PostMapping("/admin/deleteBook")
	public Object deleteBook(@RequestBody JSONObject jreq){
		String book_id = jreq.getString("book_id");
		int id = Integer.parseInt(book_id);
		if(BookService.isExistent(id)==false){
			return new AfRestError("删除书籍失败，没有找到该书籍");
		}else{
			BookService.delete(Integer.parseInt(book_id));
		}

		System.out.println("正在删除书籍。。。。");
		return new AfRestData("");
	}
	/*
	查询Borrower
	 */
	@GetMapping("/admin/queryBorrower")
	public String borrowerQuery()
	{
		return "admin/deleteBorrower";
	}

	@PostMapping("/admin/queryBorrower")
	public Object borrowerQuery(Model model, HttpServletRequest request)
	{
		String borrower_id = request.getParameter("borrower_id");
		Borrower borrower = BorrowerService.getBorrower(Integer.valueOf(borrower_id));
		if(borrower==null){
			return new AfRestError("没有找到用户");
		}
		model.addAttribute("borrower", borrower);
		return "admin/deleteBorrower";
	}

	/*
	删除Borrower
	 */
	@GetMapping("/admin/deleteBorrower")
	public String deleteBorrower(){
		return "admin/deleteBorrower";
	}

	@PostMapping("/admin/deleteBorrower")
	public Object deleteBorrower(@RequestBody JSONObject jreq){
		String borrower_id = jreq.getString("borrower_id");
		int id = Integer.valueOf(borrower_id);
		if(BorrowerService.isExistent(id)==false){
			return new AfRestError("删除用户失败，该用户不存在");
		}else{
			BorrowerService.delete(Integer.valueOf(borrower_id));
		}

		System.out.println("正在删除借书者。。。。");
		return new AfRestData("");
	}

	/*
	添加用户
	 */
	@GetMapping("/admin/addBorrower")
	public String addBorrower(){
		return "admin/addBorrower";
	}

	@PostMapping("/admin/addBorrower")
	public Object addBorrower(HttpServletRequest request){
		Integer borrower_id = Integer.valueOf(request.getParameter("borrower_id"));
		String name = request.getParameter("name");
		String adr = request.getParameter("adr");
		String tel = request.getParameter("tel");
		String path = request.getParameter("path");
		String password = request.getParameter("pw");
		Byte level = Byte.valueOf(request.getParameter("level"));
		Byte maxBook = Byte.valueOf(request.getParameter("maxBook"));

		Borrower borrower = new Borrower();
		borrower.setBorrower_id(borrower_id);
		borrower.setName(name);
		borrower.setAdr(adr);
		borrower.setTel(tel);
		borrower.setPath(path);
		borrower.setPw(password);
		borrower.setLevel(level);
		borrower.setMaxBook(maxBook);
		borrower.setBorrowedNum(Byte.valueOf("0"));

		BorrowerService.add(borrower);

		System.out.println(borrower.toString());
		return "admin/addBorrower";
	}
}



