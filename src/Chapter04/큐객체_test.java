package Chapter04;

/*
 * Queue of ArrayList of Point
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//int형 고정 길이 큐
class objectQueue {
	private List<Point> que;
	private int capacity; // 큐의 크기
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
	objectQueue(int capacity) {
		this.capacity = capacity;
		num = 0;
		front = 0;
		rear = 0;
		try {
			que = new ArrayList<Point>();
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

	public Point enQue(Point p) throws OverflowQueueException {
		if (front >= capacity - 1) {
			front = 0;
		}
		if (num == capacity) {
			throw new OverflowQueueException();
		}
		que.add(p);
		num++;
		return que.get(front++);
	}

	public Point deQue() {
		if (rear >= capacity - 1) {
			rear = 0;
		}
		if (num == 0) {
			throw new EmptyQueueException();
		}
		num--;
		return que.get(rear++);
	}

	public Point peek() {
		if (num == 0) {
			throw new EmptyQueueException();
		}
		return que.get(rear);
	}

	public void dump() {
		if (num == 0) {
			throw new EmptyQueueException();
		}
		for (int i = rear; i < front; i++) {
			System.out.printf("%d : %s", i + 1, que.get(i).toString());
			System.out.println();
		}
	}
}

public class 큐객체_test {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		objectQueue s = new objectQueue(4);
		Random random = new Random();
		int rndx = 0, rndy = 0;
		while (true) {
			System.out.println(" "); // 메뉴 구분을 위한 빈 행 추가
			System.out.printf("현재 데이터 개수: %d / %d\n", s.getSize(), s.getCapacity());
			System.out.print("(1)인큐　(2)디큐　(3)피크　(4)덤프　(0)종료: ");

			int menu = stdIn.nextInt();
			if (menu == 0)
				break;

			int x;
			switch (menu) {
			case 1: // 인큐
				System.out.print("데이터: ");
				rndx = random.nextInt() % 100;
				rndy = random.nextInt() % 100;
				try {
					Point p = s.enQue(new Point(rndx, rndy));
					System.out.println("인큐한 데이터는 " + p.toString() + "입니다.");
				} catch (objectQueue.OverflowQueueException e) {
					System.out.println("stack이 가득찼있습니다.");
				}
				break;

			case 2: // 디큐
				try {
					Point p = s.deQue();
					System.out.println("디큐한 데이터는 " + p.toString() + "입니다.");
				} catch (objectQueue.EmptyQueueException e) {
					System.out.println("큐가 비어 있습니다.");
				}
				break;

			case 3: // 피크
				try {
					Point p = s.peek();
					System.out.println("피크한 데이터는 " + p.toString() + "입니다.");
				} catch (objectQueue.EmptyQueueException e) {
					System.out.println("큐가 비어 있습니다.");
				}
				break;

			case 4: // 덤프
				s.dump();
				break;
			}
		}
	}
}