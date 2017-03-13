package cn.com.dubbo.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by fanhongtao
 * Date 2017-02-13 21:55
 */

@Component("pinganConfig")
public class PinganConfig {

    private String pinganUrl; //订单支付成功后，该订单的用户属于平安用户地址

    private String pinganPrintUrl; //平安用户订单支付成功后，调用打印接口的地址

    private  String refundUrl;

    @Value("#{pinganUrl.pinganUserURL}")
    public void setPinganUrl(String pinganUrl) {
        this.pinganUrl = pinganUrl;
    }

    public String getPinganUrl() {
        return pinganUrl;
    }

    public String getPinganPrintUrl() {
        return pinganPrintUrl;
    }

    @Value("#{pinganUrl.pinganPrintUrl}")
    public void setPinganPrintUrl(String pinganPrintUrl) {
        this.pinganPrintUrl = pinganPrintUrl;
    }

    @Value("#{pinganUrl.refundUrl}")
    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }

    public String getRefundUrl() {

        return refundUrl;
    }
}
