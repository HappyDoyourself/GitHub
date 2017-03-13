package cn.com.jiuyao.pay.common.constant;

/**
 * Created by Larry on 2015/12/18.
 * 支付渠道
 */
public enum PayChannel {

    WEB ("web","主站"),
    WAP ("wap","M站"),
    APP ("app","APP");

    PayChannel(String channel, String desc) {
        this.channel = channel;
        this.desc = desc;
    }
    private String channel;
    private String desc;

    public String getChannel() {
        return channel;
    }

    public String getDesc() {
        return desc;
    }
}
