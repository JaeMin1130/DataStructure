package Chapter04;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//int형 고정 길이 큐

class Queue {
	private ArrayList<Integer> que;
	private final int capacity; // 큐의 크기
	private int front; // 맨 처음 요소 커서
	private int rear; // 맨 끝 요소 커서
	private int num; // 현재 데이터 개수

	// 사용자 정의 예외
	public class OverflowQueueException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public OverflowQueueException() {
		}
	}

	public class EmptyQueueException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public EmptyQueueException() {
		}
	}

	// 생성자
	Queue(int capacity) {
		this.capacity = capacity;
		num = 0;
		front = 0;
		rear = 0;
		try {
			que = new ArrayList<Integer>();
		} catch (OutOfMemoryError e) {
			capacity = 0;
		}
	}

	// 메서드
	public int getSize() {
		return num;
	}

	public int getCapacity() {
		return capacity;
	}

	public int enQue(int i) throws OverflowQueueException {
		if (front >= capacity - 1) {
			front = 0;
		}
		if (num == capacity) {
			throw new OverflowQueueException();
		}
		que.add(i);
		num++;
		return que.get(front++);
	}

	public int deQue() {
		if (rear >= capacity - 1) {
			rear = 0;
		}
		if (num == 0) {
			throw new EmptyQueueException();
		}
		num--;
		return que.get(rear++);
	}

	public int peek() {
		if (num == 0) {
			throw new EmptyQueueException();
		}
		return que.get(rear);
	}

	public void dump() {
		if (num == 0) {
			throw new EmptyQueueException();
		}
		for (int i = rear; i <= front; i++) {
			try{System.out.print(que.get(i) + " ");}
			catch(Exception e) {}
		}
	}
}

//int형 고정 길이 큐의 사용 예
public class 큐정수_test {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		Queue s = new Queue(7);
		Random rd = new Random();
		while (true) {
			System.out.printf("\n현재 데이터 개수: %d / %d\n", s.getSize(), s.getCapacity());
			System.out.print("(1)인큐　(2)디큐　(3)피크　(4)덤프　(0)종료: ");

			int menu = stdIn.nextInt();
			if (menu == 0) {
				System.out.println("프로그램을 종료합니다.");
				break;
			}

			int x;
			switch (menu) {
			case 1: // 인큐
				System.out.print("데이터: ");
				try {
					x = s.enQue(rd.nextInt() % 100);
					System.out.println("인큐한 데이터는 " + x + "입니다.");
				} catch (Queue.OverflowQueueException e) {
					System.out.println("큐가 가득 찼습니다.");
				}
				break;

			case 2: // 디큐
				try {
					x = s.deQue();
					System.out.println("디큐한 데이터는 " + x + "입니다.");
				} catch (Queue.EmptyQueueException e) {
					System.out.println("큐가 비어 있습니다.");
				}
				break;

			case 3: // 피크
				try {
					x = s.peek();
					System.out.println("피크한 데이터는 " + x + "입니다.");
				} catch (Queue.EmptyQueueException e) {
					System.out.println("큐가 비어 있습니다.");
				}
				break;

			case 4: // 덤프
				try {
					s.dump();
				} catch (Queue.EmptyQueueException e) {
					System.out.println("큐가 비어 있습니다.");
				}
				break;
			}
		}
	}
}