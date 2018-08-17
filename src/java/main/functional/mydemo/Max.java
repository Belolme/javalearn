package java.main.functional.mydemo;

import java.util.stream.IntStream;

import fj.Ord;
import fj.data.List;

//探讨java递归的最大深度
public class Max {

//	public static int deep;
	public static int LENGTH = 100000 + 1;

	public static int max(List<Integer> l1){
//		deep++;
		return l1.isEmpty() ? Integer.MIN_VALUE : Integer.max(l1.head(), max(l1.tail()));
	}

	public static void main(String[] args) {

		//初始化列表使用的时间最多,是计算的百倍
		List<Integer> l = List.range(0, LENGTH);
		int [] array = IntStream.range(0, LENGTH).toArray();

		//使用-Xss1G增大栈内存
		long startTime = System.currentTimeMillis();
		System.out.println("mydemo.Max number is: " + max(l));
		long endTime = System.currentTimeMillis();
		//128 10,000,000
		//266 20,000,000
		System.out.println("Calculate time is: " + (endTime-startTime));

		//寻找List最大值时,使用递归比使用循环要高效得多得多
		//1,000,000 递归 - 7 millisecond
		//1,000,000 循环 - 147,652 millisecond
		//使用java原生的list - 1,000,000 - 507957
		startTime = System.currentTimeMillis();
		java.util.List<Integer> lJava = l.toJavaList();
		int cache = l.head();
		int lNum;
		for(int i=1; i<LENGTH; i++){
			lNum = lJava.get(i);
			cache = lNum>cache ? lNum : cache;
		}
		endTime = System.currentTimeMillis();
		System.out.println("mydemo.Max number is: " + cache);
		System.out.println("Calculate time is: " + (endTime-startTime));
		
		//自己写得递归比库的效率要高10倍
		startTime = System.currentTimeMillis();
		System.out.println("mydemo.Max number is: " + l.maximum(Ord.intOrd));
		endTime = System.currentTimeMillis();
		System.out.println("Calculate time is: " + (endTime-startTime));

		//然而在纯数组循环里, 效率则只为1/10
		startTime = System.currentTimeMillis();
		cache = array[0];
		for(int i=1; i<LENGTH; i++){
			cache = array[i]>cache ? array[i] : cache;
		}
		endTime = System.currentTimeMillis();
		System.out.println("mydemo.Max number is: " + cache);
		System.out.println("Calculate time is: " + (endTime-startTime));
	}
}
