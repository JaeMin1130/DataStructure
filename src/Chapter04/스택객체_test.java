package Chapter04;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class ObjectStack {
	private int capacity; // 스택의 크기
	private int top; // 스택 포인터
	private ArrayList<Point> data; // 스택용 배열

	public class OverflowObjectStackException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public OverflowObjectStackException() {
		}
	}

	public class EmptyObjectStackException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public EmptyObjectStackException() {
		}
	}

	ObjectStack(int capacity) {
		this.capacity = capacity;
		top = 0;
		try {
			data = new ArrayList<Point>();
		} catch (Exception e) {
			capacity = 0;
		}
	}

	public int size() {
		return top;
	}

	public int getCapacity() {
		return capacity;
	}

	public ArrayList<Point> getData() {
		return data;
	}

	public boolean isEmpty() {
		return top == 0;
	}

	public Point push(Point p) throws OverflowObjectStackException {
		if (top >= capacity)
			throw new OverflowObjectStackException();
		data.add(p);
		return data.get(top++);
	}

	public Point pop() throws EmptyObjectStackException {
		if (top == 0)
			throw new EmptyObjectStackException();
		return data.get(--top);
	}

	public Point peek() throws EmptyObjectStackException {
		if (top == 0)
			throw new EmptyObjectStackException();
		return data.get(top - 1);
	}

	public void dump() {
		if (top <= 0) {
			System.out.println("스택이 비어 있습니다.");
		} else {
			for (int i = 0; i < top; i++) {
				System.out.printf("%d : %s", i + 1, data.get(i).toString());
				System.out.println();
			}
		}
	}
}

class Point {
	private int x;
	private int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("Point [x=%3d, y=%3d]", x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}

public class 스택객체_test {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		ObjectStack s = new ObjectStack(8); // 최대 8 개를 push할 수 있는 stack
		Random random = new Random();
		int rndx = 0, rndy = 0;

		while (true) {
			System.out.println(); // 메뉴 구분을 위한 빈 행 추가
			System.out.printf("현재 데이터 개수: %d / %d\n", s.size(), s.getCapacity());
			System.out.print("(1)push　(2)pop　(3)peek　(4)dump　(0)종료: ");

			int menu = stdIn.nextInt();

			if (menu == 0) {
				System.out.println("\n프로그램을 종료합니다.");
				break;
			}
			switch (menu) {
			case 1: // 푸시
				rndx = random.nextInt() % 10;
				rndy = random.nextInt() % 100;
				try {
					s.push(new Point(rndx, rndy));
				} catch (ObjectStack.OverflowObjectStackException e) {
					System.out.println("stack이 가득찼있습니다.");
				}
				break;

			case 2: // 팝
				try {
					Point p = s.pop();
					System.out.printf("pop한 데이터는 %s 입니다.", p.toString());
				} catch (ObjectStack.EmptyObjectStackException e) {
					System.out.println("stack이 비어있습니다.");
				}
				break;

			case 3: // 피크
				try {
					Point p = s.peek();
					System.out.printf("peek한 데이터는 %s 입니다.", p.toString());
				} catch (ObjectStack.EmptyObjectStackException e) {
					System.out.println("stack이 비어있습니다.");
				}
				break;

			case 4: // 덤프
				s.dump();
				break;
			}
		}
	}
}