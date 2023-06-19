package Chapter03;

//3장 - 1번 실습 과제 > 2번 실습: 스트링 객체의 정렬과 이진 탐색 > 3번 실습: 객체 정렬과 이진 탐색
//comparator 구현 실습
import java.util.Arrays;
import java.util.Random;

public class 정수배열이진탐색_Test {

	public static void inputData(int[] arr) {
		Random random = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = random.nextInt(10);
		}
	}

	public static void showData(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	public static void sortData(int[] arr) {
		Arrays.sort(arr);
		System.out.println("Sort Array :");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	public static String linearSearch(int[] arr, int key) {
		int idx = 0;
		boolean flag = false;
		StringBuilder result = new StringBuilder();
		while (idx < arr.length) {
			if (arr[idx] == key) {
				result.append(idx + " ");
				idx++;
				flag = true;
			} else
				idx++;
		}
		if (!flag) {
			result.append("-1");
		}
		return result.toString();
	}

	public static int binarySearch(int[] arr, int key) {
		int start = 0;
		int end = arr.length - 1;

		while (start <= end) {
			int idx = (start + end) / 2;
			if (arr[idx] == key) {
				return idx;
			} else if (arr[idx] > key) {
				end = idx - 1;
			} else {
				start = idx + 1;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] data = new int[10];
		inputData(data);
//		showData(data);
		System.out.println();
		sortData(data);

		int key = 3;
		String result = linearSearch(data, key);
		System.out.println("\nlinearSearch(): result = " + result);

		key = 9;
		int result1 = binarySearch(data, key);
		System.out.println("\nbinarySearch(): result = " + result1);

		int idx = Arrays.binarySearch(data, key);
		System.out.println("\nArrays.binarySearch(): result = " + idx);

	}

}
