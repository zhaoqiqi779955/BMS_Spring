package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import data.Borrower;
import data.LibrarySystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import service.BorrowerService;
import utility.Util;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

@Controller
public class BorrowerController {
    @PostMapping("/register.do")
    public  Object register(@RequestBody JSONObject jreq, HttpSession session)
    {
         String tem;
         Integer id=jreq.getInteger("id");
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
        borrower.setPath(path);
        if(!(BorrowerService.add(borrower)))
        {
            return  new AfRestError("账户已存在");
        };


        if(path!=null){
            File src=new File(Common.tmpDir,path);
            String suffix= Util.getSuffix(path);
            path=Common.userFile+"/"+String.format("%010d",id);
            File desDir=new File(path);
            desDir.mkdirs();
            String fileName="head."+suffix;
            File desFile=new File(desDir,fileName);

            Common.exector.submit(new SaveFileTask(src,desFile));

            session.setAttribute("path",String.format("%010d",id)+"/"+fileName);
            session.setAttribute("id",id);
            session.setAttribute("level",1);

        }

        return new AfRestData("");
    }

    @GetMapping("/register")
    public String register()
    {
        return "borrower/register";
    }

}
class SaveFileTask implements Runnable{
    File src=null;
    File des=null;

    public SaveFileTask(File src, File des) {
        this.src = src;
        this.des = des;
    }

    @Override
    public void run() {
        Common.saveFileTo(src,des);
    }
}
