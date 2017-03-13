package cn.com.jiuyao.pay.common.util;


import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class MathUtil {
	
	protected final static Logger _log = Logger.getLogger(MathUtil.class);
	
	/**
	 * 将分转化为元
	 * @param v1
	 * @return
	 */
	public static BigDecimal changeF2Y(BigDecimal v1) {
		return v1.divide(new BigDecimal(100)).setScale(4,BigDecimal.ROUND_HALF_UP);
    }

	/**
	 * 将元转化为fen
	 * @param v1
	 * @return
	 */
	public static BigDecimal changeY2F(BigDecimal fen) {
		//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(ROUND_HALF_UP四舍五入) ROUND_HALF_UP: 遇到.5的情况时往上近似,例: 1.5 ->;2
		return fen.multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 加法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		DecimalFormat df = new DecimalFormat("0.0000");
        return new BigDecimal(df.format(v1.add(v2)));
    }
	
	/**
	 * 减法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2) {
		DecimalFormat df = new DecimalFormat("0.0000");
		
        return new BigDecimal(df.format(v1.subtract(v2)));
    }
	
	/**
	 * 乘法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal mul(BigDecimal v1, BigDecimal v2) {
		DecimalFormat df = new DecimalFormat("0.0000");
        return new BigDecimal(df.format(v1.multiply(v2)));
    }
	
	/**
	 * 除法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal div(BigDecimal v1,BigDecimal v2,int len) {
		return v1.divide(v2,len,BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * BigDecimal v1,BigDecimal v2,int len
	 * @param v1
	 * @param v2
	 * @return int 1:大于  0：等于  -1小于
	 */
	public static int compare(BigDecimal v1,BigDecimal v2){
		return v1.compareTo(v2);
	}
	
}
