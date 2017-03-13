package cn.com.jiuyao.pay.common.util; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.math.BigDecimal;

/** 
* MathUtil Tester. 
* 
* @author <fanhongtao> 
* @since <pre>01/12/2017</pre> 
* @version 1.0 
*/ 
public class MathUtilTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: changeF2Y(BigDecimal v1) 
* 
*/ 
@Test
public void testChangeF2Y() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: changeY2F(BigDecimal v1) 
* 
*/ 
@Test
public void testChangeY2F() throws Exception { 
//TODO: Test goes here...
    System.out.println("元转分："+new BigDecimal(0.0100).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP));
} 

/** 
* 
* Method: add(BigDecimal v1, BigDecimal v2) 
* 
*/ 
@Test
public void testAdd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: sub(BigDecimal v1, BigDecimal v2) 
* 
*/ 
@Test
public void testSub() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: mul(BigDecimal v1, BigDecimal v2) 
* 
*/ 
@Test
public void testMul() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: div(BigDecimal v1, BigDecimal v2, int len) 
* 
*/ 
@Test
public void testDiv() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: compare(BigDecimal v1, BigDecimal v2) 
* 
*/ 
@Test
public void testCompare() throws Exception { 
//TODO: Test goes here... 
} 


} 
