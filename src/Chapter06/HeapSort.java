
package Chapter06;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

class Heap {

	public ArrayList<Integer> heapArr;
	public int n; // current length of heap
	public int maxSize; // Maximum allowable size of heap

	public Heap(int sz) {
		sz = maxSize;
		heapArr = new ArrayList<Integer>();
	}

	public void insert(int x) {
		heapArr.add(x);
	}

	public void display() {
		for (int i = 0; i < heapArr.size(); i++) {
			System.out.print(heapArr.get(i) + " ");
		}
		System.out.println();
	}

	// 힙으로 만들기
	private void downHeap(int left, int right) {
		int temp = heapArr.get(left);
		int child;
		int parent;

		for (parent = left; parent < (right + 1) / 2; parent = child) {
			int cl = parent * 2 + 1; // 왼쪽 자식
			int cr = cl + 1; // 오른쪽 자식
			child = (cr <= right && heapArr.get(cr) > heapArr.get(cl)) ? cr : cl; // 둘 중에 큰 자식

			if (temp >= heapArr.get(child)) {
				break;
			}
			// root자리의 값보다 자식값이 더 크면 자리를 바꾼다.
			heapArr.set(parent, heapArr.get(child));
		}
		heapArr.set(parent, temp);
	}

	// root값 빼기
	public int deleteMax() {
		try {
			n = heapArr.size();
			for (int i = n - 1; i >= 0; i--) {
				downHeap(i, n - 1);
			}
			int max = heapArr.get(0);
			heapArr.remove(0);
			return max;
		} catch (Exception e) {
			heapEmpty();
			return 0;
		}
	}

	private void heapEmpty() {
		System.out.println("Heap Empty");
	}
}

public class HeapSort {

	public static void main(String[] args) {
		int select = 0;
		Scanner scanner = new Scanner(System.in);
		Heap heap = new Heap(10);
		final int count = 10;
		Stack<Integer> sorted = new Stack<Integer>(); // 정렬 후, 힙의 root 값을 끝에서부터 채운다.

		do {
			System.out.println("Max Tree. Select: 1(insert), 2(display), 3(sort), 4(exit) => ");
			select = scanner.nextInt();
			switch (select) {
			case 1:
				for (int i = 0; i < count; i++) {
					if (heap.heapArr.size() >= count) {
						System.out.println("Heap is full.");
						break;
					}
					heap.insert((int) (Math.random() * 130));
				}
				break;
			case 2:
				heap.display();
				break;
			case 3:
				for (int i = 0; i < count; i++) {
					sorted.add(heap.deleteMax());
				}
				while (!sorted.isEmpty()) {
					System.out.print(sorted.pop() + " ");
				}
				System.out.println();
				break;

			case 4:
				System.out.println("프로그램 종료");
				break;
			}
		} while (select < 4);
		scanner.close();
	}
}
