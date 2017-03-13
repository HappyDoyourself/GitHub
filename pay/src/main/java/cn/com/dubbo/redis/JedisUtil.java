package cn.com.dubbo.redis;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import cn.com.jiuyao.pay.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtil {
	public static Properties properties = new Properties();
	private static JedisPool jedisPool = null;
	
	static{
		InputStream is = JedisUtil.class.getClassLoader().getResourceAsStream("config/system.properties");
		try {
			properties.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取redis连接池
	 * @return
	 */
	private static synchronized JedisPool getJedisPool(){
		if(jedisPool==null){
			//阿里云：JedisPool连接池示例-Jedis-2.7.2示例
			JedisPoolConfig config = new JedisPoolConfig();
			//最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
			config.setMaxIdle(200);
			//最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
			config.setMaxTotal(300);
			config.setTestOnBorrow(false);
			config.setTestOnReturn(false);

			String host = properties.getProperty("redisHost");
			String port = properties.getProperty("redisPort");
			String password = properties.getProperty("redisPassword");//instance_id:password
//			String password = "9ab75955ba064d68:Saitseo2015";//instance_id:password
            jedisPool = new JedisPool(config, host, Integer.parseInt(port), 3000, StringUtil.isBlank(password)?null:password);
		}
		return jedisPool;
	}
	
    
    /**
     * 存放数据
     * @param key
     * @param value
     * @throws Exception 
     */
	public static void set(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			jedis.set(key, value);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	
	/**
	 * 读取数据
	 * @param key
	 * @throws Exception 
	 */
	public static String get(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			String result = jedis.get(key);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	/**
	 * 往list中存放数据
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public static void push(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			jedis.rpush(key, value);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	/**
	 * 从list中获取数据
	 * @param key
	 * @param startOffset
	 * @param endOffset
	 * @throws Exception 
	 */
	public static List<String> lrange(String key,int startOffset,int endOffset) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			List<String> result = jedis.lrange(key, startOffset, endOffset);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	/**
	 * 获取list中的所有数据
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAll(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			List<String> result = jedis.lrange(key, 0, -1);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	public static void set(byte[] key, byte[] value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			jedis.set(key, value);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	public static byte[] get(byte[] key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			byte[] result = jedis.get(key);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	public static Long expire(String key, int seconds) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			Long result = jedis.expire(key, seconds);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
	    	if (jedis != null) {
	    		jedis.close();
	    	}
	    }
	}
	
	
	public static Long expire(byte[] key, int seconds) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			Long result = jedis.expire(key, seconds);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (jedis != null) {
	    		jedis.close();
	    	}
		}
	}
	
	public static Long del(String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			Long result = jedis.del(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (jedis != null) {
	    		jedis.close();
	    	}
		}
	}
	
	public static Long del(byte[] key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = getJedisPool().getResource();
			Long result = jedis.del(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (jedis != null) {
	    		jedis.close();
	    	}
		}
	}

	
/*	public static void main(String[] args) {
		try{
//			JedisUtil.set("foo","test");
//			String str = JedisUtil.get("foo");
//			System.out.println("获取缓存数据成功------"+str);
			
			String stockcodelist = JedisUtil.get(StockConstant.KEY_STOCK_CODE);
			JSONArray array = JSONArray.fromObject(stockcodelist);
			System.out.println("缓存中的股票代码总数："+array.size());
			for(int i=0;i<array.size();i++){
				JSONObject json = JSONObject.fromObject(array.get(i));
				String code = json.getString("code");
				String type = json.getString("type");
				String key = StockConstant.PREFIX_KEY_StockMinuteBar+code;
				if("000001".equals(code) && "sh".equals(type)){
					key = StockConstant.PREFIX_KEY_StockMinuteBar+"sh000001";
				}
				
				JedisUtil.del(key);
				System.out.println("从缓存中删除分时数据成功------"+key);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("从缓存中删除分时数据异常:"+e);
		}
		System.out.println("从缓存中删除分时数据完成");
	}*/
	
	
}
