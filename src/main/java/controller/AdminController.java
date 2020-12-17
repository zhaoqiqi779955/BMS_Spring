package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.AdminService;

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
		String username = jreq.getString("username");
		String password = jreq.getString("password");
		Admin admin= AdminService.getAdmin(Integer.parseInt(username));
		if(admin==null){
			return  new AfRestError("用户不存在");
		}
		if(!admin.getPw().equals(password)){
			return new AfRestError("密码错误");
		}
		// 登录成功
		session.setAttribute("admin", username);
		return new AfRestData("");
	}
}
