package Chapter02;

import java.util.Random;

public class 행렬합곱 {
	
	// 행렬에 값 넣기
	static void setValue(int[][] arr, int a, int b) {
		Random r1 = new Random();
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arr[i][j] = r1.nextInt(10);
			}
		}
	}

	// 행렬 출력
	static void showArr(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	// 행렬합
	static void arrSum(int[][] arr1, int[][] arr2) {
		for (int i = 0; i < arr2.length; i++) {
			for (int j = 0; j < arr2[i].length; j++) {
				System.out.print(arr1[i][j] + arr2[i][j] + " ");
			}
			System.out.println();
		}
	}
	// 행렬곱
	static void arrMul(int[][] arr1, int[][] arr2) {
		int value = 0;
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr2[i].length; j++) {
				for (int k = 0; k < arr1[i].length; k++) {
					value = arr1[i][k] * arr2[k][j];
				}
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
	
	// 행렬 전치
	static void arrRev(int[][] arr, int a, int b) {
		int[][] reverse = new int[b][a];
		for (int i = 0; i < reverse.length; i++) {
			for (int j = 0; j < reverse[i].length; j++) {
				reverse[i][j] = arr[j][i];
				System.out.print(reverse[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] A = new int[2][3];
		int[][] A1 = new int[2][3];
		int[][] B = new int[3][4];

		setValue(A, 2, 3);
		setValue(A1, 2, 3);
		setValue(B, 3, 4);

		arrSum(A, A1);
		showArr(A);
		arrRev(A, 2, 3);
		arrMul(A, B);
	}

}
