package controller;

import af.spring.AfRestError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import utility.web.AfSimpleDownload;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Controller
public class PhotoController
{
	private File tmpDir ;
	private ServletContext sc;
	private String contextPath;
	File dataDir=null;
	public String getWebLocation()
	{
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
		path=path.replace('/', '\\'); // 将/换成\
		path=path.replace("file:", ""); //去掉file:
		path=path.replace("classes\\", ""); //去掉class\
		path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...
		path+="store";
		//System.out.println(path);
		return path;
	}
	
	public PhotoController(ServletContext sc)
	{
		this.sc = sc;
		this.contextPath = sc.getContextPath();

	}


	@RequestMapping("/download")
	public Object download(@RequestParam("name") String fileName)
	{

		File file=new File(dataDir,fileName);
		InputStream in;
		try {
			 in=new FileInputStream(file);
			AfSimpleDownload simpleDownload=new AfSimpleDownload(in,"image/jpg");
			return  simpleDownload;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new AfRestError();
	}


}
