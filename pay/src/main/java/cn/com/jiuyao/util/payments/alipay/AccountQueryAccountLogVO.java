package cn.com.jiuyao.util.payments.alipay;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.math.BigDecimal;

/**
 * 支付宝对账查询对象
 * Created by Larry on 2015/3/27.
 */
@XStreamAlias("AccountQueryAccountLogVO")
public class AccountQueryAccountLogVO {
    private BigDecimal balance;
    private String buyer_account;
    private String currency;
    private String deposit_bank_no;
    private String goods_title;
    private BigDecimal income;
    private String iw_account_log_id;
    private String memo;
    private String merchant_out_order_no;
    private BigDecimal outcome;
    private String partner_id;
    private BigDecimal rate;
    private String seller_account;
    private String seller_fullname;
    private BigDecimal service_fee;
    private String service_fee_ratio;
    private String sign_product_name;
    private String sub_trans_code_msg;
    private BigDecimal total_fee;
    private String trade_no;
    private BigDecimal trade_refund_amount;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBuyer_account() {
        return buyer_account;
    }

    public void setBuyer_account(String buyer_account) {
        this.buyer_account = buyer_account;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeposit_bank_no() {
        return deposit_bank_no;
    }

    public void setDeposit_bank_no(String deposit_bank_no) {
        this.deposit_bank_no = deposit_bank_no;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getIw_account_log_id() {
        return iw_account_log_id;
    }

    public void setIw_account_log_id(String iw_account_log_id) {
        this.iw_account_log_id = iw_account_log_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMerchant_out_order_no() {
        return merchant_out_order_no;
    }

    public void setMerchant_out_order_no(String merchant_out_order_no) {
        this.merchant_out_order_no = merchant_out_order_no;
    }

    public BigDecimal getOutcome() {
        return outcome;
    }

    public void setOutcome(BigDecimal outcome) {
        this.outcome = outcome;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getSeller_account() {
        return seller_account;
    }

    public void setSeller_account(String seller_account) {
        this.seller_account = seller_account;
    }

    public String getSeller_fullname() {
        return seller_fullname;
    }

    public void setSeller_fullname(String seller_fullname) {
        this.seller_fullname = seller_fullname;
    }

    public BigDecimal getService_fee() {
        return service_fee;
    }

    public void setService_fee(BigDecimal service_fee) {
        this.service_fee = service_fee;
    }

    public String getService_fee_ratio() {
        return service_fee_ratio;
    }

    public void setService_fee_ratio(String service_fee_ratio) {
        this.service_fee_ratio = service_fee_ratio;
    }

    public String getSign_product_name() {
        return sign_product_name;
    }

    public void setSign_product_name(String sign_product_name) {
        this.sign_product_name = sign_product_name;
    }

    public String getSub_trans_code_msg() {
        return sub_trans_code_msg;
    }

    public void setSub_trans_code_msg(String sub_trans_code_msg) {
        this.sub_trans_code_msg = sub_trans_code_msg;
    }

    public BigDecimal getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(BigDecimal total_fee) {
        this.total_fee = total_fee;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public BigDecimal getTrade_refund_amount() {
        return trade_refund_amount;
    }

    public void setTrade_refund_amount(BigDecimal trade_refund_amount) {
        this.trade_refund_amount = trade_refund_amount;
    }

    public String getTrans_code_msg() {
        return trans_code_msg;
    }

    public void setTrans_code_msg(String trans_code_msg) {
        this.trans_code_msg = trans_code_msg;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    private String trans_code_msg;
    private String trans_date;

}
