package Chapter02;

import java.util.Arrays;
import java.util.Random;

public class 메소드배열전달 {
	static void getData(int[] arr) {
		Random r1 = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r1.nextInt(10);
		}
	}

	static void showData(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
	
	static void swap(int[] arr, int i, int j) {
		int value = 0;
		value = arr[i];
		arr[i] = arr[j];
		arr[j] = value;
	}
	
	static void sortData(int[] arr) {
		int value = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if(arr[i] > arr[j]) {
					swap(arr, i, j);
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}


	public static void main(String[] args) {
		int[] data = new int[10];
			
		getData(data);
		showData(data);
		sortData(data);

	}
}