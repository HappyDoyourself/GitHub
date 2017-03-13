package cn.com.jiuyao.util.payments.ehk;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fanhongtao
 * Date 2017-01-13 14:37
 */
public class B2CBank {
    public  static Map<String,String> bankMap(){
        Map<String,String> map=new ConcurrentHashMap<String, String>();
        map.put("BANK_CARD-B2C-POST-P2P","邮政储蓄银行");
        map.put("BANK_CARD-B2C-SDB-P2P","深圳发展银行");
        map.put("BANK_CARD-B2C-CMBC-P2P","民生银行");
        map.put("BANK_CARD-B2C-BCCB-P2P","北京银行");
        map.put("BANK_CARD-B2C-SHB-P2P","上海银行");
        map.put("BANK_CARD-B2C-CMBCHINA-P2P","招商银行");
        map.put("BANK_CARD-B2C-ECITIC-P2P","中信银行");
        map.put("BANK_CARD-B2C-SPDB-P2P","浦发银行");
        map.put("BANK_CARD-B2C-CIB-P2P","兴业银行");
        map.put("BANK_CARD-B2C-HXB-P2P","华夏银行");
        map.put("BANK_CARD-B2C-ABC-P2P","农业银行");
        map.put("BANK_CARD-B2C-GDB-P2P","广发银行");
        map.put("BANK_CARD-B2C-ICBC-P2P","工商银行");
        map.put("BANK_CARD-B2C-BOC-P2P","中国银行");
        map.put("BANK_CARD-B2C-BOCO-P2P","交通银行");
        map.put("BANK_CARD-B2C-CCB-P2P","建设银行");
        map.put("BANK_CARD-B2C-PINGANBANK-P2P","平安银行");
        map.put("BANK_CARD-B2C-CEB-P2P","光大银行");
        return map;
    }
}
