package wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wechat.service.CoreService;
import wechat.util.SignUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by darlingtld on 2015/2/10.
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private CoreService coreService;


    @RequestMapping(method = RequestMethod.GET)
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
        out.close();
        out = null;
    }


    @RequestMapping(method = RequestMethod.POST)
    public void respond(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        // 请求校验
        String respXml = coreService.processRequest(request);
        out.print(respXml);
        out.close();
        out = null;

    }

}


//request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        /** 读取接收到的xml消息 */
//        StringBuffer sb = new StringBuffer();
//        InputStream is = request.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
//        BufferedReader br = new BufferedReader(isr);
//        String s = "";
//        while ((s = br.readLine()) != null) {
//        sb.append(s);
//        }
//        String xml = sb.toString(); //次即为接收到微信端发送过来的xml数据
//
//        String result = wechatService.processWechatMag(xml);
//
//        try {
//        OutputStream os = response.getOutputStream();
//        os.write(result.getBytes("UTF-8"));
//        os.flush();
//        os.close();
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
