package Chapter03;

import java.util.Arrays;
import java.util.Comparator;

class Fruit {
	String name;
	int price;
	String date;

	Fruit(String name, int price, String date) {
		this.name = name;
		this.price = price;
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public static void showData(Fruit[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i].name + " " + data[i].price + " " + data[i].date);
		}
	}

	public static int binarySearch(Fruit[] data, Fruit key, Comparator<Fruit> comparator) {
		int start = 0;
		int end = data.length - 1;
		while (start <= end) {
			int idx = (start + end) / 2;
			if (comparator.compare(data[idx], key) == 0) {
				return idx;
			} else if (comparator.compare(data[idx], key) > 0) {
				end = idx - 1;
			} else {
				start = idx + 1;
			}
		}
		return -1;
	}

}

public class Fruit객체배열이진탐색_Test {

	public static void main(String[] args) {
		Fruit[] arr = { new Fruit("사과", 200, "2023-5-8"), new Fruit("키위", 500, "2023-6-8"),
				new Fruit("오렌지", 230, "2023-7-8"), new Fruit("바나나", 50, "2023-5-18"), new Fruit("수박", 880, "2023-5-28"),
				new Fruit("체리", 10, "2023-9-8"), new Fruit("바나나", 500, "2023-5-18") };
		System.out.println("정렬전 객체 배열: ");
		Fruit.showData(arr);

		// 익명 클래스 생성
		Comparator<Fruit> cc_name = new Comparator<Fruit>() {
			@Override
			public int compare(Fruit f1, Fruit f2) {
				int value = f1.name.compareTo(f2.name);
				return value;
			}
		};

		// Comparator로 정렬
		Arrays.sort(arr, cc_name);
		System.out.println("\nComparator 정렬(이름)후 객체 배열: ");
		Fruit.showData(arr);

		// 람다식으로 정렬
		// 람다식은 오직 하나의 메서드만 선언한 인터페이스를 implement할 수 있다
		// Fruit에 compareTo()가 있어도 람다식 우선 적용
		// 여기서는 매개변수가 두 개 필요한 메서드가 compare 하나라서 가능
		Arrays.sort(arr, (a, b) -> a.getPrice() - b.getPrice());
		System.out.println("\n람다식 정렬(가격)후 객체 배열: ");
		Fruit.showData(arr);

		// binarySearch 실행하기
		Fruit newFruit = new Fruit("키위", 230, "2012-5-18");

		// Comparator
		int result3 = Fruit.binarySearch(arr, newFruit, cc_name);
		System.out.println("\nFruit.binarySearch() 조회결과(이름): " + result3);
		int result4 = Arrays.binarySearch(arr, newFruit, cc_name);
		System.out.println("\nArrays.binarySearch() 조회결과(이름): " + result4);

		// 람다식
		int result5 = Fruit.binarySearch(arr, newFruit, (a, b) -> a.getPrice() - b.getPrice());
		System.out.println("\nFruit.binarySearch() 조회결과(가격): " + result5);
		int result6 = Arrays.binarySearch(arr, newFruit, (a, b) -> a.getPrice() - b.getPrice());
		System.out.println("\nArrays.binarySearch() 조회결과(가격): " + result6);
	}
}