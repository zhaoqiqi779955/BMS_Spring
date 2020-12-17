package controller;

import af.spring.AfRestData;
import af.spring.AfRestError;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class Common {
    @GetMapping("/code")
    public void verifyCode(HttpServletResponse response,HttpSession session)
    {
        int width = 100;
        int height = 30;

        String data = "abcdefghijklmnopqrst01234567890";

        Random random = new Random();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, width, height);

        StringBuffer code=new StringBuffer();
        graphics.setColor(Color.black);
        for (int i = 0; i < 4; i++) {

            int position = random.nextInt(data.length());
            String randomStr = data.substring(position, position + 1);
            graphics.drawString(randomStr, width / 5 * (i + 1), 15);
            code.append(randomStr);
        }
        session.setAttribute("code",code.toString());


        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("/code.do")
    public Object verify(@RequestBody JSONObject jreq, HttpSession session)
    {
        String right=(String)session.getAttribute("code");
        String code= jreq.getString("code");
        if(right.equals(code))
        {
            return new AfRestData("");
        }
        else {
            return new AfRestError("wrong");
        }
    }
}
