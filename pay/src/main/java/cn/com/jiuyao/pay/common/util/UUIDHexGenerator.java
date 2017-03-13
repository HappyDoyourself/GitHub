package cn.com.jiuyao.pay.common.util;

import java.net.InetAddress;

import org.apache.log4j.Logger;

/**
 * 自动生成不重复主键ID
 * 
 * @author <a href="mailto:TianXiang.Mr@gmail.com">TianXiang.Mr@gmail.com</a>
 * @serial 1.0
 * @date Aug 3, 2009 12:16:15 PM
 */
public class UUIDHexGenerator {
	private static Logger logger = Logger.getLogger(UUIDHexGenerator.class);
	private static final int IP;

	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	private String sep = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}

	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	protected int getJVM() {
		return JVM;
	}

	protected short getCount() {
		synchronized (UUIDHexGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	protected int getIP() {
		return IP;
	}

	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public static void main(String[] args) throws Exception {

		UUIDHexGenerator gen = new UUIDHexGenerator();
		UUIDHexGenerator gen2 = new UUIDHexGenerator();

		for (int i = 1; i <= 3; i++) {
			String id = gen.generate();
			System.out.println(id);
			// String id2 = gen2.generate();
			// logger.info(id2);
		}

		// for (int i = 1; i <= 136; i++) {
		// // logger.info("ff808081284e3ea3012872308f5c1c45");
		// // logger.info("5");
		// // logger.info("saitseo@hotmail.com");
		// logger.info("#FF0000");
		// // logger.info("40284b81288b81bc01288b81bca6032b");
		// // logger.info("2");
		//
		// }

	}

}
