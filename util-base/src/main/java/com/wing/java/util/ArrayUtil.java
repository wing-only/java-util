package com.wing.java.util;

/*
 * 数组操作工具类
 */
public class ArrayUtil {
	private ArrayUtil(){}
	
	/**
	 * 获取整型数组的最大值
	 * @param arr
	 *            接收一个元素为int类型的数组
	 * @return 该数组的最大值
	 */
	public static int getMax(int[] arr) {
		int maxIndex = 0;// 定义一个变量,记录最大值的角标,先进性赋初始值
		for (int x = 0; x < arr.length; x++) {
			if (arr[x] > arr[maxIndex])
				maxIndex = x;// 使maxIndex保持的是较大值的角标
		}
		return arr[maxIndex];
	}

	/**
	 * 对数组元素进行排序,使用选择排序
	 * @param arr	接收一个元素为int类型的数组
	 */
	public static void selectSort(int[] arr) {
		for (int x = 0; x < arr.length - 1; x++) {
			for (int y = x + 1; y < arr.length; y++) {
				if (arr[x] > arr[y]) {
					swap(arr, x, y);
				}
			}
		}
	}

	/*
	 * 用于给数组进行元素的位置置换
	 * @param arr 接收一个元素为int类型的数组
	 * @param a 置换的元素的角标
	 * @param b 置换元素的角标
	 */
	// 此方法是向selestSort服务的,不需要对外提供数据所以定义成private
	private static void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	/**
	 * 对数组中的元素进行排序,使用冒泡排序
	 * @param arr	接收一个元素为int类型的数组
	 */
	public static void bubbleSort(int[] arr) {
		for (int x = 0; x < arr.length - 1; x++) {
			// arr.length-1-x   减1的目的就是防止数组角标越界,减x的目的就是减少每次比较的次数
			for (int y = 0; y < arr.length - 1 - x; y++){
				if (arr[y] > arr[y + 1]) {
					swap(arr, y, y + 1);
				}
			}
		}
	}

	/**
	 * 获取指定的元素在指定数组中的索引,使用的是最为普通的查找法
	 * @param arr  接收一个整型的数组
	 * @param key  要查找的数值
	 * @return 返回查找的元素的位置,如果没有则返回-1
	 */
	public static int getIndex(int[] arr, int key) {
		for (int x = 0; x < arr.length; x++) {
			if (arr[x] == key) {
				return x;
			}
		}
		return -1;
	}

	/**
	 * 获取指定的元素在指定数组中的索引,使用的是二分查找法
	 * @param arr   接收一个整型的数组
	 * @param key   要查找的数值
	 * @return 返回查找的元素的位置,如果没有则返回-1
	 */
	public static int halfSearch(int[] arr, int key) {
		int min, mid, max;// 定义一个中间角标,和最大角标,最小角标
		min = 0;
		max = arr.length - 1;
		while (max > min) {
			mid = (max + min) >> 1;// 当满足条件时,则将数组的角标折半
			if (key > arr[mid])
				min = mid + 1;// 如果满足条件,则将数组的mid角标向右移动,并且作为新数组的最小的角标
			if (key < arr[mid])
				max = mid - 1;// 如果满足条件,则将数组的mid角标向左移动,并且作为新数组的最大的角标
			else
				return mid;
		}
		return -1;// 如果没有对应的值,则返回-1,表示所查找的元素不存在,要想将本方法的返回值与Array中的binarySearch方法的返回值保持一致,则将-1改成-mid-1
	}
	
	/**
	 * 获取指定数组的所有元素的和
	 * @param arr   接收一个整型类型的数组
	 * @return 		返回数组元素的和
	 */
	public static int add(int[] arr) {
		int mun = 0;
		for (int x = 0; x < arr.length - 1; x++) {
			mun += arr[x];
		}
		return mun;
	}

	/**
	 * 将int型的数组元素转换为字符串 格式是:[e1,e2,e3....]
	 * @param arr   接收一个整型类型的数组
	 * @return 		返回该数组的字符串表现形式
	 */
	public static String arrayToString(int[] arr) {
		String str = "[";// 思想就是任何元素+字符串都将返回字符串
		for (int x = 0; x < arr.length; x++) {
			if (x != arr.length - 1)
				str = str + arr[x] + ",";
			else
				str = str + arr[x] + "]";
		}
		return str;
	}

	// 数组翻转
	public static void revers(int[] arr){
		for (int start = 0, end = arr.length - 1; start < end; start++, end--) {
			int temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
		}
	}
}
