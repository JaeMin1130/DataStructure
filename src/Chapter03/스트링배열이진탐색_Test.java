package Chapter03;

//3장 - 1번 실습 과제 > 2번 실습: 스트링 객체의 정렬과 이진 탐색 > 3번 실습: 객체 정렬과 이진 탐색
//comparator 구현 실습
import java.util.Arrays;
import java.util.Random;

public class 스트링배열이진탐색_Test {

	public static void showData(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	public static void sortData(String[] arr) {
		Arrays.sort(arr);
	}

	public static int linearSearch(String[] arr, String key) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i].equals(key)) return i;
		return -1;
	}

	public static int binarySearch(String[] arr, String key) {
		int start = 0;
		int end = arr.length - 1;

		while (start <= end) {
			int idx = (start + end) / 2;
			if (arr[idx].compareTo(key) == 0) {
				return idx; // return 하면 while 빠져나온다.
			} else if (arr[idx].compareTo(key) > 0) {
				end = idx - 1;
			} else {
				start = idx + 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		String []data = {"apple","grape","persimmon", "감", "배", "사과", "포도", "pear","blueberry", "strawberry", "melon", "oriental melon"};

		sortData(data);
		showData(data);
		String key = "감";
		int result = linearSearch(data, key);
		System.out.println("\nlinearSearch(): result = " + result);
		
		key = "배";
		int result1 = binarySearch(data, key);
		System.out.println("\nbinarySearch(): result = " + result1);
		
		int idx = Arrays.binarySearch(data, key);
		System.out.println("\nArrays.binarySearch(): result = " + idx);
		
		
	}
}