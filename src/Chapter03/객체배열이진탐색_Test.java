package Chapter03;

import java.util.Arrays;

//5번 실습 - 2장 실습 2-14를 수정하여 객체 배열의 정렬 구현
class PhyscData implements Comparable<PhyscData> {
	String name;
	int height;
	double vision;

	PhyscData() {
	}

	PhyscData(String name, int height, double vision) {
		this.name = name;
		this.height = height;
		this.vision = vision;
	}

	@Override
	public int compareTo(PhyscData data) {
		int value = this.name.compareTo(data.name);
		if (value == 0) {
			value = this.height - data.height;
		}
		if (value == 0) {
			value = (int) this.vision - (int) data.vision;
		}
		return value;
	}

	public static void showData(PhyscData[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i].name + " " + data[i].height + " " + data[i].vision);
		}
	}

	public static int linearSearch(PhyscData[] data, PhyscData key) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].name.equals(key.name) && data[i].height == key.height && data[i].vision == key.vision) {
				return i;
			}
		}
		return -1;
	}

	public static int binarySearch(PhyscData[] data, PhyscData key) {
		int start = 0;
		int end = data.length - 1;
		while (start <= end) {
			int idx = (start + end) / 2;
			if (data[idx].compareTo(key) == 0) {
				return idx;
			} else if (data[idx].compareTo(key) > 0) {
				end = idx - 1;
			} else {
				start = idx + 1;
			}
		}
		return -1;
	}
}

public class 객체배열이진탐색_Test {

	public static void main(String[] args) {
		PhyscData[] data = { new PhyscData("홍길동", 162, 1.3), new PhyscData("홍동", 164, 1.3),
				new PhyscData("홍길", 152, 0.7), new PhyscData("길동", 167, 0.2), new PhyscData("김홍길동", 172, 0.3),
				new PhyscData("홍길동", 162, 1.5), new PhyscData("길동", 182, 0.6), new PhyscData("길동", 167, 0.5), };
		
		Arrays.sort(data);
		PhyscData.showData(data);
		PhyscData key = new PhyscData("홍길동", 162, 1.3);
		int result = PhyscData.linearSearch(data, key);
		System.out.println("\nlinearSearch(): result = " + result);

		key = new PhyscData("길동", 167, 0.5);
		result = PhyscData.binarySearch(data, key);
		System.out.println("\nbinarySearch(): result = " + result);
		int idx = Arrays.binarySearch(data, key);
		System.out.println("\nArrays.binarySearch(): result = " + idx);
	}
}
