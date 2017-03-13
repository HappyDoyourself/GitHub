package cn.com.dubbo.model;


import java.math.BigDecimal;

import cn.com.jiuyao.pay.common.framework.util.J1PayAssert;

/**
 * Created by Larry on 2015/9/16.
 * 支付接口参数对象
 */
public class PaymentParam {
    private String businessType;//支付业务类型
    private Long businessId;//业务单据id
    private Integer paymentTypeId;//支付方式id
    private String paymentTypeNo;//支付方式编码
    private String paymentNo;//支付订单号（支付流水号）
    private BigDecimal paymentFee;//支付金额（应付）
    private Long memberId;//会员id
    private String channel;//支付渠道
    private String returnUrl;//支付完成后的回调路径
    private String token;
    private Long orderId;
    private BigDecimal money;
    private String commitTime;
    private String tradePassword;
    private String callback;
    private String sign;//签名

    //Builder Pattern
    public static class Builder {
        // Required parameters
        private Long orderId;
        private String businessType;
        private String channel;
        // Optional parameters - initialized to default values
        private Long businessId;
        private Integer paymentTypeId;
        private String paymentTypeNo;
        private String paymentNo;
        private BigDecimal paymentFee;
        private Long memberId;
        private String returnUrl;
        private String token;
        private BigDecimal money;
        private String commitTime;
        private String tradePassword;
        private String callback;
        private String sign;

        public Builder(String orderIdVal,String businessTypeVal,String channelVal) {
            J1PayAssert.orderId(orderIdVal);
            J1PayAssert.businessType(businessTypeVal);
            J1PayAssert.channel(channelVal);

            orderId = Long.valueOf(orderIdVal);
            businessType = businessTypeVal;
            channel = channelVal;
        }

        public Builder businessId(String val) {
            J1PayAssert.businessId(val);
            businessId = Long.valueOf(val);
            return this;
        }

        public Builder paymentTypeId(String val) {
            J1PayAssert.paymentTypeId(val);
            paymentTypeId = Integer.valueOf(val);
            return this;
        }

        public Builder paymentTypeNo(String val) {
            J1PayAssert.paymentTypeNo(val);
            paymentTypeNo = val;
            return this;
        }

        public Builder paymentNo(String val) {
            J1PayAssert.paymentNo(val);
            paymentNo = val;
            return this;
        }

        public Builder paymentFee(String val) {
            J1PayAssert.paymentFee(val);
            paymentFee = new BigDecimal(val);
            return this;
        }

        public Builder memberId(String val) {
            J1PayAssert.memberId(val);
            memberId = Long.valueOf(val);
            return this;
        }

        public Builder returnUrl(String val) {
            J1PayAssert.returnUrl(val);
            returnUrl = val;
            return this;
        }

        public Builder token(String val) {
            J1PayAssert.token(val);
            token = val;
            return this;
        }

        public Builder money(String val) {
            J1PayAssert.money(val);
            money = new BigDecimal(val);
            return this;
        }

        public Builder commitTime(String val) {
            J1PayAssert.commitTime(val);
            commitTime = val;
            return this;
        }

        public Builder tradePassword(String val) {
            J1PayAssert.tradePassword(val);
            tradePassword = val;
            return this;
        }

        public Builder callback(String val) {
            callback = val;
            return this;
        }

        public Builder sign(String val) {
            J1PayAssert.sign(val);
            sign = val;
            return this;
        }

        public PaymentParam build() {
            return new PaymentParam(this);
        }
    }

    private PaymentParam(Builder builder) {
        businessType = builder.businessType;
        businessId = builder.businessId;
        paymentTypeId = builder.paymentTypeId;
        paymentTypeNo = builder.paymentTypeNo;
        paymentNo = builder.paymentNo;
        paymentFee = builder.paymentFee;
        memberId = builder.memberId;
        channel = builder.channel;
        returnUrl = builder.returnUrl;
        token = builder.token;
        orderId = builder.orderId;
        money = builder.money;
        commitTime = builder.commitTime;
        tradePassword = builder.tradePassword;
        callback = builder.callback;
        sign = builder.sign;
    }

    public String getBusinessType() {
        return businessType;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }


    public String getPaymentTypeNo() {
        return paymentTypeNo;
    }


    public String getPaymentNo() {
        return paymentNo;
    }


    public BigDecimal getPaymentFee() {
        return paymentFee;
    }


    public Long getMemberId() {
        return memberId;
    }


    public String getChannel() {
        return channel;
    }


    public String getReturnUrl() {
        return returnUrl;
    }

    public String getToken() {
        return token;
    }


    public Long getOrderId() {
        return orderId;
    }


    public BigDecimal getMoney() {
        return money;
    }


    public String getCommitTime() {
        return commitTime;
    }


    public String getTradePassword() {
        return tradePassword;
    }

    public String getCallback() {
        return callback;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*********PaymentParam:");
        stringBuilder.append("orderId=");
        stringBuilder.append(orderId);
        stringBuilder.append(";businessType=");
        stringBuilder.append(businessType);
        stringBuilder.append(";channel=");
        stringBuilder.append(channel);
        stringBuilder.append(";paymentTypeNo=");
        stringBuilder.append(paymentTypeNo);
        stringBuilder.append(";returnUrl=");
        stringBuilder.append(returnUrl);
        stringBuilder.append(";token=");
        stringBuilder.append(token);
        stringBuilder.append(";memberId=");
        stringBuilder.append(memberId);
        stringBuilder.append("*********");
        return stringBuilder.toString();
    }
}
