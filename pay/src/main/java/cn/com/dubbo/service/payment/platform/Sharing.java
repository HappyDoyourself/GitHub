package cn.com.dubbo.service.payment.platform;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by fanhongtao
 * Date 2017-03-16 11:11
 */
public interface Sharing {
     String sharing(String url, HttpServletResponse response);
}
