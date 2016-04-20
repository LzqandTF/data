/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码
 * 
 * @author dongrui
 * @version $Id: VerifyCode.java, v 0.1 2012-8-18 下午12:58:01 Snow Exp $
 */
public class VerifyCode extends HttpServlet {

    private static final long serialVersionUID = -9126207965931217648L;
    // 设置验证码的字体与大小
    private Font              myFont           = new Font("宋体", Font.BOLD, 18);

    // 设置颜色
    // fc字体，bc背景
    Color getColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }

        Random random = new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置文件头
        response.setHeader("Pragma", "No_cache");//设定禁止浏览器从本地机的缓存中调阅页面内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);//设定网页的到期时间
        response.setContentType("image/jpeg");

        //设置图片长宽
        int height = 20;
        int width = 65;

        //在内存中生成图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //开始制作图片
        Graphics graphics = image.getGraphics();
        Random random = new Random();
        graphics.setColor(getColor(200, 250));
        graphics.fillRect(1, 1, width - 1, height - 1);
        graphics.setColor(new Color(102, 102, 102));
        graphics.drawRect(0, 0, width - 1, height - 1);
        graphics.setFont(myFont);
        graphics.setColor(getColor(160, 200));

        //生随机线
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        //另一个方向的随机线
        for (int j = 0; j < 70; j++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(12) + 1;
            int yl = random.nextInt(6) + 1;
            graphics.drawLine(x, y, x - xl, y - yl);
        }
        //转成字母
        String imgCode = "";
        for (int n = 0; n < 4; n++) {
            int itmp = random.nextInt(10);
            imgCode += String.valueOf(itmp);
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //字母放入图片中
            graphics.drawString(String.valueOf(itmp), 15 * n + 5, 16);
        }
        //将产生的字符放入在Session中
        request.getSession().setAttribute("imgCode", imgCode);

        graphics.dispose();

        //将图像输出到Servlet
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
