package cn.com.jiuyao.pay.common.framework.util;

import cn.com.dubbo.service.payment.constant.Constants;
import cn.com.jiuyao.pay.common.util.MatchUtil;

/**
 * Created on 2015/12/21.
 * 参数格式校验工具类
 */
public class J1PayAssert {
    public static void businessType(String val) {
        PayAssert.notNull(val, " The businessType must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.BUSINESSTYPE_REGEX, val), "Invalid format of businessType.");
    }

    public static  void businessId(String val) {
        PayAssert.notNull(val, " The businessId must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.ORDERID_REGEX, val), "Invalid format of businessId.");
    }

    public static  void paymentTypeId(String val) {
        PayAssert.notNull(val, " The paymentTypeId must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.PAYMENTTYPEID_REGEX, val), "Invalid format of paymentTypeId.");
    }

    public static  void paymentTypeNo(String val) {
        PayAssert.notNull(val, " The paymentTypeNo must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.PAYMENTTYPENO_REGEX, val), "Invalid format of paymentTypeNo.");
    }

    public static  void paymentNo(String val) {
        PayAssert.notNull(val, " The paymentNo must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.PAYMENTNO_REGEX, val), "Invalid format of paymentNo.");
    }

    public static  void paymentFee(String val) {
        PayAssert.notNull(val, " The paymentFee must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.PAYMENTFEE_REGEX, val), "Invalid format of paymentFee.");
    }

    public static  void memberId(String val) {
        PayAssert.notNull(val, " The memberId must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.MEMBERID_REGEX, val), "Invalid format of memberId.");
    }

    public static  void channel(String val) {
        PayAssert.notNull(val, " The channel must not be null,this argument is required.");
    }

    public static  void returnUrl(String val) {
        PayAssert.notNull(val, " The returnUrl must not be null,this argument is required.");
    }

    public static  void cardNo(String val) {
        PayAssert.notNull(val, " The cardNo must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.CARDNO_REGEX, val), "Invalid format of cardNo.");
    }

    public static  void cardPass(String val) {
        PayAssert.notNull(val, " The cardPass must not be null,this argument is required.");
    }

    public static  void token(String val) {
        PayAssert.notNull(val, " The token must not be null,this argument is required.");
    }

    public static  void orderId(String val) {
        PayAssert.notNull(val, " The orderId must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.ORDERID_REGEX, val), "Invalid format of orderId.");
    }

    public static  void money(String val) {
        PayAssert.notNull(val, " The money must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.PAYMENTFEE_REGEX, val), "Invalid format of money.");
    }

    public static  void commitTime(String val) {
        PayAssert.notNull(val, " The commitTime must not be null,this argument is required.");
        PayAssert.argument(MatchUtil.matche(Constants.COMMITTIME_REGEX, val), "Invalid format of commitTime.");
    }

    public static  void tradePassword(String val) {
        PayAssert.notNull(val, " The tradePassword must not be null,this argument is required.");
    }

    public static  void callback(String val) {
        PayAssert.notNull(val, " The callback must not be null,this argument is required.");
    }

    public static  void sign(String val) {
        PayAssert.notNull(val, " The sign must not be null,this argument is required.");
    }
}
