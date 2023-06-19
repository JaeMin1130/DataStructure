package Chapter02;


public class String배열정렬 {

	static void showData(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

	static void swap(String[] arr, int i, int j) {
		for (int k = 0; k < arr.length; k++) {
			String value;
			value = arr[i];
			arr[i] = arr[j];
			arr[j] = value;
		}
	}

	static void sortData(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i].compareTo(arr[j]) < 0) {
					swap(arr, i, j);
				}
			}
		}

	}

	public static void main(String[] args) {
		String[] data = { "grape", "band", "melon", "bancna", "apple" };
		sortData(data);
		showData(data);
	}
}
