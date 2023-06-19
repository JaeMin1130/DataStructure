package Chapter04;

import java.util.Scanner;
//int형 고정 길이 스택
class IntStack {
	private final int capacity; // 스택의 크기
	private int top; // 스택 포인터
	private int[] data; // 스택용 배열

	// 사용자 정의 예외
	// 실행 시 예외 : 스택이 가득 참
	public class OverflowIntStackException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public OverflowIntStackException() {
		}
	}

	// 실행 시 예외 : 스택이 비어 있음
	public class EmptyIntStackException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public EmptyIntStackException() {
		}
	}

	// 생성자
	IntStack(int capacity) {
		this.capacity = capacity;
		top = 0;
		try {
			data = new int[capacity];
		} catch (OutOfMemoryError e) {
			capacity = 0;
		}
	}
	// 메서드
	public int size() {
		return top;
	}
	public int getCapacity() {
		return capacity;
	}
	public boolean isEmpty() {
		return top == 0;
	}
	
	// push 데이터 넣기
	public int push(int num) throws OverflowIntStackException {
		if (top >= capacity)
			throw new OverflowIntStackException();
		data[top] = num;
		return data[top++];
	}

	// pop 맨 위에 데이터 빼기
	public int pop() throws EmptyIntStackException {
		if (top == 0)
			throw new EmptyIntStackException();
		return data[--top];
	}

	// peek 맨 위에 값을 확인
	public int peek() throws EmptyIntStackException {
		if (top == 0)
			throw new EmptyIntStackException();
		return data[top - 1];
	}

	// dump 스택 안의 모든 데이터를 바닥 -> 꼭대기 순서로 출력
	public void dump() {
		if (top <= 0) {
			System.out.println("스택이 비어 있습니다.");
		} else {
			for (int i = 0; i < top; i++) {
				System.out.print(data[i] + " ");
			}
			System.out.println();
		}
	}

}

public class 스택정수_test {
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		IntStack s = new IntStack(4); // 최대 64 개를 푸시할 수 있는 스택
		while (true) {
			System.out.println(); // 메뉴 구분을 위한 빈 행 추가
			System.out.printf("현재 데이터 개수: %d / %d\n", s.size(), s.getCapacity());
			System.out.print("(1)push　(2)pop　(3)peek　(4)dump　(0)종료: ");

			int menu = stdIn.nextInt();
			int x;
			switch (menu) {
			case 1: // 푸시
				System.out.print("데이터: ");
				x = stdIn.nextInt();
				try {
					s.push(x);
				} catch (IntStack.OverflowIntStackException e) {
					System.out.println("스택이 가득 찼습니다.");
				}
				break;
			case 2: // 팝
				try {
					x = s.pop();
					System.out.println("팝한 데이터는 " + x + "입니다.");
				} catch (IntStack.EmptyIntStackException e) {
					System.out.println("스택이 비어있습니다.");
				}
				break;
			case 3: // 피크
				try {
					x = s.peek();
					System.out.println("피크한 데이터는 " + x + "입니다.");
				} catch (IntStack.EmptyIntStackException e) {
					System.out.println("스택이 비어있습니다.");
				}
				break;
			case 4: // 덤프
				s.dump();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
}