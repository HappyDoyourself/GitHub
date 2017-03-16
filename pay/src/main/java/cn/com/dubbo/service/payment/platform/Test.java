package cn.com.dubbo.service.payment.platform;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by fanhongtao
 * Date 2017-03-16 12:51
 */
public class Test {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("expires_in","7200");
        jsonObject.put("access_token","Mdb6L62YH7iGt9i1k78NUC1XCoNBR0ODG2aGiGBMW1-6W-UokKeeohfvkvwEORgoM6pi1Q4ZSWE35Gydhqcuq90056wKo1U4Dd80KIBBlVqIS56bwJZjkUUq42ehOsckFCLgAEAYVE");

        if (jsonObject.get("access_token") !=null){
            System.out.println(jsonObject.get("access_token") +""+ Integer.valueOf(jsonObject.get("expires_in").toString()));
        }else {
        }
    }
}
