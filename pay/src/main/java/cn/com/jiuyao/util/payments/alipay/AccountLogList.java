package cn.com.jiuyao.util.payments.alipay;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;

/**
 * 支付宝对账查询记录
 * Created by Larry on 2015/3/27.
 */
public class AccountLogList {

    @XStreamImplicit(itemFieldName="AccountQueryAccountLogVO")
    private ArrayList<AccountQueryAccountLogVO> queryAccountLogVOList;

    public ArrayList<AccountQueryAccountLogVO> getQueryAccountLogVOList() {
        return queryAccountLogVOList;
    }

    public void setQueryAccountLogVOList(ArrayList<AccountQueryAccountLogVO> queryAccountLogVOList) {
        this.queryAccountLogVOList = queryAccountLogVOList;
    }
}
