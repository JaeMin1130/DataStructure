package Chapter05;

public class Hanoi {
	static void move(int no, int x, int y) {
		if(no > 1) {
			move(no - 1, x, 6 - x - y);
		}
		
		System.out.printf("원반[%d]을(를) %d번 기둥으로 옮김\n", no, x, y);
		
		if(no > 1) {
			move(no - 1, 6 - x - y, y);
		}
	}
}
