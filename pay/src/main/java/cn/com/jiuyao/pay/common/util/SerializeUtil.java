package cn.com.jiuyao.pay.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * 序列化工具类
 * */
public class SerializeUtil {

	public static byte[] serialize(Object object) {
		try {
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			byte bytes[];
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object unserialize(byte bytes[]) {
		try {
			ByteArrayInputStream bais = null;
			ObjectInputStream ois;
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
