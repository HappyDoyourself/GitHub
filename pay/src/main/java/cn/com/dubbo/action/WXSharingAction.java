package cn.com.dubbo.action;

import cn.com.dubbo.service.payment.platform.WXSharingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by fanhongtao
 * Date 2017-03-16 10:11
 */
@Controller
public class WXSharingAction {

    Logger logger = LoggerFactory.getLogger(WXSharingAction.class);

    @Autowired
    public WXSharingService wXSharingService;

    @RequestMapping(value = "/wxSharing")
    public String sharing(HttpServletRequest request, HttpServletResponse response){
        String url = request.getParameter("url"); //分享链接url
        try {
            logger.info("beforeDecode:" + url);
            url = URLDecoder.decode(url,"utf-8");
            logger.info("afterEncode:" + url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return wXSharingService.sharing(url,response);
    }
}


