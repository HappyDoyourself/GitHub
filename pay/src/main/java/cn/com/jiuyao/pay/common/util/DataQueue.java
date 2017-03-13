package cn.com.jiuyao.pay.common.util;

import java.util.LinkedList;

public class DataQueue <T> {

	private LinkedList<T> storage = new LinkedList<T>();
	
	//入队操作
	public void enQueue(T t){
		storage.offer(t);
	}
	
	//出对操作（不删除）
    public T peek() {
       return storage.getFirst();
    }
	
	//出对操作(删除队列中的内容)
	public T deQueue(){
		return storage.removeFirst();
	}
	
	//判断队列是否为空
	public boolean isEmpty(){
		return storage.isEmpty();
	}
	
	//打印元素
	public String toString(){
		return storage.toString();
	}
	
	//清空队列
	public void clear(){
		storage.clear();
	}
	
	//获取队列的长度
	public int getLength(){
		return storage.size();
	}
	
	public static void main(String[] args) {
		
		DataQueue<String> queue = new DataQueue<String>(); 
		for(int i=0;i<10;i++){
			queue.enQueue("入队列1:"+i);
		}
		
		DataQueue<String> queue2 = new DataQueue<String>(); 
		for(int i=0;i<10;i++){
			queue2.enQueue("入队列2:"+i);
		}
		
		while(!queue.isEmpty()){
			String tt = queue.peek();
			System.out.println("tt : "+tt);
			queue.deQueue();
		}
		
		while(!queue2.isEmpty()){
			String tt = queue2.peek();
			System.out.println("tt : "+tt);
			queue2.deQueue();
		}
		
	}
	
}
