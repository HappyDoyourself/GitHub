package cn.com.dubbo.model;


public class PaymentResult {
    private static final long serialVersionUID = -3538923811030116356L;
    private String isSuccess;//查询结果
    private String error;//查询交易信息失败的错误代码
    private String tradeNo;//第三方支付流水号
    private String outTradeNo;//商户订单流水号
    private String totalFee;//交易总金额
    private String currency;//交易币种
    private String subject;//商品名称
    private String extension;//商户扩展信息
    private String tradeStatus;//交易状态
    private String tradeMode;//交易模式
    private String refundStatus;//退款交易状态
    private String refundFee;//退款总金额
    private Boolean isDone;//完成状态（支付完成，退款完成等）

    @Override
    public String toString() {
        return "PaymentResult{" +
                "isSuccess='" + isSuccess + '\'' +
                ", error='" + error + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", currency='" + currency + '\'' +
                ", subject='" + subject + '\'' +
                ", extension='" + extension + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", tradeMode='" + tradeMode + '\'' +
                ", refundStatus='" + refundStatus + '\'' +
                ", refundFee='" + refundFee + '\'' +
                ", isDone=" + isDone +
                '}';
    }



    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        if (null == isDone) {
            this.isDone = false;
        } else {
            this.isDone = isDone;
        }
    }

}