package Chapter05;

import java.util.Stack;

class Point {
	int X;
	int Y;

	Point(int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public String toString() {
		return String.format("(%d, %d)", X, Y);
	}
}

public class EightQueens {

	// 말이 있으면 false, 없으면 true
	public static boolean checkRow(int[][] board, int cx) {
		for (int i = 0; i < board.length; i++) {
			if (board[cx][i] == 1)
				return false;
		}
		return true;
	}

	public static boolean checkCol(int[][] board, int cy) {
		for (int i = 0; i < board.length; i++) {
			if (board[i][cy] == 1)
				return false;
		}
		return true;
	}

	public static boolean checkDiagSW(int[][] board, int cx, int cy) {
		boolean flag = true;
		try {
			int xVal = cx;
			int yVal = cy;
			// 중앙 기준 위에쪽
			if (cx + cy <= 7) {
				// 위로 가기
				while (cx >= 0) {
					if (board[cx--][cy++] == 1) {
						flag = false;
						break;
					}
				}
				cx = xVal;
				cy = yVal;
				// 아래로 가기
				while (cy >= 0) {
					if (board[cx++][cy--] == 1) {
						flag = false;
						break;
					}
				}
				// 중앙 기준 아래쪽
			} else {
				// 위로 가기
				while (cy <= 7) {
					if (board[cx--][cy++] == 1) {
						flag = false;
						break;
					}
				}
				cx = xVal;
				cy = yVal;
				// 아래로 가기
				while (cx <= 7) {
					if (board[cx++][cy--] == 1) {
						flag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		return flag;
	}

	public static boolean checkDiagSE(int[][] board, int cx, int cy) {
		boolean flag = true;
		try {
			int xVal = cx;
			int yVal = cy;
			// 중앙 기준 위쪽
			if (cx <= cy) {
				// 위로 가기
				while (cx >= 0) {
					if (board[cx--][cy--] == 1) {
						flag = false;
						break;
					}
				}
				cx = xVal;
				cy = yVal;
				// 아래로 가기
				while (cy <= 7) {

					if (board[cx++][cy++] == 1) {
						flag = false;
						break;
					}
				}
				// 중앙 기준 아래쪽
			} else {
				// 위로 가기
				while (cy >= 0) {
					if (board[cx--][cy--] == 1) {
						flag = false;
						break;
					}
				}
				cx = xVal;
				cy = yVal;
				// 아래로 가기
				while (cx <= 7) {
					if (board[cx++][cy++] == 1) {
						flag = false;
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		return flag;
	}

	// 이동 가능하면 true, 아니면 false
	public static boolean checkMove(int[][] board, int x, int y) {// (x,y)로 이동 가능한지를 check
		boolean flag = false;
		if (checkRow(board, x) && checkCol(board, y) && checkDiagSW(board, x, y) && checkDiagSE(board, x, y)) {
			flag = true;
		}
		return flag;
	}

	// board 출력
	public static void getBoard(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
	}

	// board 초기화
	public static void clearBoard(int[][] board) {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = 0;
			}
	}

	// 퀸 놓기
	public static void put(int[][] board, int row, int[] check, Stack<Point> stack) {
		for (int j = 0; j < 8; j++) {
			if (checkMove(board, row, j)) {
				board[row][j] = 1;
				stack.push(new Point(row, j));
				check[row] = 1;
			}
		}
		if (check[row] == 0) {
			inspect(board, check, stack);
		}
	}

	// 마지막으로 놓은 퀸 다시 놓기
	public static void inspect(int[][] board, int[] check, Stack<Point> stack) {
		Point p = stack.pop();
		board[p.X][p.Y] = 0;

		if (p.Y != 7) {
			for (int j = p.Y + 1; j < 8; j++) {
				if (checkMove(board, p.X, j)) {
					board[p.X][j] = 1;
					stack.push(new Point(p.X, j));
				}
			}
			if (stack.size() != p.X + 1) {
				check[p.X] = 0;
				inspect(board, check, stack);
			}
		} else {
			check[p.X] = 0;
			inspect(board, check, stack);
		}

		if (p.X != 7) {
			put(board, p.X + 1, check, stack);
		}
	}

	public static void SolveQueen(int[][] board) {

		Stack<Point> stack = new Stack<Point>();
		int[] check = new int[8];
		Point firstQueen = new Point(0, 0);
		stack.push(firstQueen);
		board[0][0] = 1;
		check[0] = 1;
		int row = 1;

		while (stack.size() != 8) {
			put(board, row++, check, stack);
			if (row == 8)
				row = 0;
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().toString());
		}
	}

	public static void main(String[] args) {

		// 체스판 생성
		int[][] board = new int[8][8];
		
		SolveQueen(board);
		getBoard(board);
	}
}