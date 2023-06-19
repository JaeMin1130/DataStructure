package Chapter05;

import java.util.Stack;

enum Directions {
	N, NE, E, SE, S, SW, W, NW
}

class Step {
	int x;
	int y;
	int dir;

	Step(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "Items [x=" + x + ", y=" + y + ", dir=" + dir + "]";
	}
}

class Offsets {
	int x;
	int y;

	Offsets(int a, int b) {
		this.x = a;
		this.y = b;
	}
}

public class MazingProblem {

	static Offsets[] move = new Offsets[8];

	public static void forward(int[][] maze, Offsets[] move, Stack<Step> trace, int first) {
		// 검사했으면 1
		boolean flag = false;

		// 8방향 중 이동가능한 곳으로 이동
		if (!trace.isEmpty()) {
			while (true) {
				Step now = trace.peek();
				flag = false;
				for (int i = first; i < move.length; i++) {
					int moveX = now.x + move[i].x;
					int moveY = now.y + move[i].y;
					if (moveX >= 0 && moveX <= 11 && moveY >= 0 && moveY <= 14) {
						if (maze[moveX][moveY] == 0) {
							maze[moveX][moveY] = 2;
							Step temp = new Step(moveX, moveY, i);
							trace.push(temp);
							flag = true;
							break;
						}
					}
				}
				if (!flag)
					break;
				if (trace.peek().x == 11)
					break;
			}
		}

		if (!flag) {
			backward(maze, move, trace);
		}

	}

	public static void backward(int[][] maze, Offsets[] move, Stack<Step> trace) {
		if (!trace.isEmpty()) {
			Step last = trace.pop();
			maze[last.x][last.y] = 0;
			forward(maze, move, trace, last.dir + 1);
		}
	}

	public static void showPath(int[][] mark, Stack<Step> trace) {
		while (!trace.isEmpty()) {
			Step step = trace.pop();
			mark[step.x][step.y] = 2;
		}
		for (int i = 0; i < mark.length; i++) {
			for (int j = 0; j < mark[i].length; j++) {
				System.out.print(mark[i][j] + " ");
			}
			System.out.println();
		}
	}
		
	public static void main(String[] args) {

		int maze[][] = { // 12 x 15
				{ 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1 },
				{ 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
				{ 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1 },
				{ 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0 },
				{ 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1 },
				{ 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1 },
				{ 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1 },
				{ 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1 },
				{ 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1 },
				{ 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 },
				{ 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }};
		
		for (int ia = 0; ia < 8; ia++) {
			move[ia] = new Offsets(0, 0);
		}
		
		move[0].x = -1;		move[0].y = 0; // N
		move[1].x = -1;		move[1].y = 1; // NE
		move[2].x = 0;		move[2].y = 1; // E
		move[3].x = 1;		move[3].y = 1; // SE
		move[4].x = 1;		move[4].y = 0; // S
		move[5].x = 1;		move[5].y = -1; // SW
		move[6].x = 0;		move[6].y = -1; //W
		move[7].x = -1;		move[7].y = -1; // NW
		
	
		Stack<Step> trace = new Stack<Step>();
		// 출발점
		trace.push(new Step(0,0,0));
		maze[0][0] = 2;
		int first = 0;
		
		forward(maze, move, trace, first);
		int[][] mark = new int[12][15];
		
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("------------------------------------");
		showPath(mark, trace);
	}
}
