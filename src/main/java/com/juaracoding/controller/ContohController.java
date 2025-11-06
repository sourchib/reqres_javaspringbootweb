package com.juaracoding.controller;

import com.juaracoding.core.SMTPCore;
import com.juaracoding.util.ReadTextFileSB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contoh")
public class ContohController {

    @Autowired
    private SMTPCore s;

    @GetMapping
    public String sendEmail() throws Exception {
        try {

//            String [] strImage = {"D:\\Batch28\\pcmspringboot6\\src\\main\\resources\\image\\image-1.png","D:\\Batch28\\pcmspringboot6\\src\\main\\resources\\image\\image-2.png"};
//            String[] attachmentPaths = {
//                    System.getProperty("user.dir")+"/image/image-1.png",
//                    System.getProperty("user.dir")+"/image/image-2.png"
//            };

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String fileHtml = "ver_regis.html";
                    String  strContent = null;
                    String [] strImage = {"classpath:image/image-1.png",
                            "classpath:image/image-2.png"};
                    String [] strEmail = {"poll.chihuy@gmail.com","bintangmokodompit.skom@gmail.com"};
                    try {
                        strContent = new ReadTextFileSB(fileHtml).getContentFile();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    String subject = "INI SUBJECT YA";
                    strContent = strContent.replace("#JKVM3NH",subject);
                    strContent = strContent.replace("XF#31NN","BINTANG");
                    strContent = strContent.replace("8U0_1GH$","803312");
                    s.sendMailWithAttachment(
                            strEmail,
                            subject,
                            strContent,
                            strImage
                    );
                }
            });
            t.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "OK";
    }
}