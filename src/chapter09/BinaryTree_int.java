package chapter09;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;
import java.util.Scanner;

//정수를 저정하는 이진트리 만들기 실습
class TreeNode {
	int data;
	TreeNode leftChild;
	TreeNode rightChild;

	public TreeNode() {
		leftChild = rightChild = null;
	}

	public TreeNode(int data) {
		this.data = data;
		leftChild = rightChild = null;
	}
}

class Tree {
	TreeNode root;
	ArrayList<Integer> nodeList;

	Tree() {
		root = null;
		nodeList = new ArrayList<>();
	}

	void inorder() {
		inorder(root);
	}

	void preorder() {
		preorder(root);
	}

	void postorder() {
		postorder(root);
	}

	void inorder(TreeNode CurrentNode) {
		if (CurrentNode != null) {
			inorder(CurrentNode.leftChild);
			System.out.print(" " + CurrentNode.data);
			inorder(CurrentNode.rightChild);
		}
	}

	void preorder(TreeNode CurrentNode) {
		if (CurrentNode != null) {
			System.out.print(" " + CurrentNode.data);
			preorder(CurrentNode.leftChild);
			preorder(CurrentNode.rightChild);
		}
	}

	void postorder(TreeNode CurrentNode) {
		if (CurrentNode != null) {
			postorder(CurrentNode.leftChild);
			postorder(CurrentNode.rightChild);
			System.out.print(" " + CurrentNode.data);
		}
	}

	boolean isLeafNode(TreeNode current) {
		if (current.leftChild == null && current.rightChild == null)
			return true;
		else
			return false;
	}

	Deque<Integer> queData(TreeNode current) {
		TreeNode temp = current;
		Deque<Integer> qu = new ArrayDeque<Integer>();
		while (true) {
			if (temp.rightChild != null) {
				temp = temp.rightChild;
				qu.add(temp.data);
			} else {
				if (temp.leftChild != null) {
					temp = temp.leftChild;
					qu.add(temp.data);
				} else {
					break;
				}
			}
		}
		return qu;
	}

	TreeNode remodel(TreeNode node) {
		Deque<Integer> qu = queData(node);
		TreeNode prev = null;
		TreeNode cur = node;
		while (true) {
			if (cur.rightChild != null) {
				prev = cur;
				prev.data = qu.poll();
				cur = cur.rightChild;
			} else {
				if (cur.leftChild != null) {
					prev = cur;
					prev.data = qu.poll();
					cur = cur.leftChild;
				} else {
					break;
				}
			}
		}
		if (isLeftChild(prev, cur.data)) {
			cur = null;
			prev.leftChild = cur;
		} else {
			cur = null;
			prev.rightChild = cur;
		}
		return node;
	}

	boolean isLeftChild(TreeNode prev, int data) {
		if (prev.data > data) {
			return true;
		}
		return false;
	}

	boolean add(int x) {// binary search tree를 만드는 입력 => A + B * C을 tree로 만드는 방법: 입력 해결하는 알고리즘 작성 방법을
						// 설계하여 구현
		if (nodeList.contains(x))
			return false;

		TreeNode newNode = new TreeNode(x);

		if (root == null) {
			root = newNode;
			nodeList.add(x);
			return true;
		}
		TreeNode prev = null;
		TreeNode cur = root;

		while (cur != null) {
			if (x > cur.data) {
				prev = cur;
				cur = cur.rightChild;
			} else if (x < cur.data) {
				prev = cur;
				cur = cur.leftChild;
			} else {
				return false;
			}
		}

		if (isLeftChild(prev, x)) {
			prev.leftChild = newNode;
		} else {
			prev.rightChild = newNode;
		}
		nodeList.add(x);
		return true;
	}

	boolean delete(int num) {
		TreeNode prev = null;
		TreeNode cur = root;

		if (!nodeList.contains(num)) {
			return false;
		}
		while (cur != null) {
			if (cur.data == num) {
				if (cur == root) { // root일 때
					remodel(root);
				} else if (isLeafNode(cur)) { // leaf일 때
					if (isLeftChild(prev, num)) {
						cur = null;
						prev.leftChild = cur;
					} else {
						cur = null;
						prev.rightChild = cur;
					}
				} else { // 사이에 있을 때
					if (isLeftChild(prev, num)) {
						prev.leftChild = remodel(cur);
					} else {
						prev.rightChild = remodel(cur);
					}
				}
				nodeList.remove((Object) num);
				return true;
			} else {
				if (isLeftChild(cur, num)) {
					prev = cur;
					cur = cur.leftChild;
				} else {
					prev = cur;
					cur = cur.rightChild;
				}
			}
		}
		return false;
	}

	boolean search(int num) {
		if (nodeList.contains(num)) {
			return true;
		}
		return false;
	}
}

public class BinaryTree_int {
	enum Menu {
		Add("삽입"), Delete("삭제"), Search("검색"), InorderPrint("순차출력"), Exit("종료");

		private final String message; // 표시할 문자열

		static Menu MenuAt(int idx) { // 순서가 idx번째인 열거를 반환
			for (Menu m : Menu.values())
				if (m.ordinal() == idx)
					return m;
			return null;
		}

		Menu(String string) { // 생성자(constructor)
			message = string;
		}

		String getMessage() { // 표시할 문자열을 반환
			return message;
		}
	}

	// --- 메뉴 선택 ---//
	static Menu SelectMenu() {
		Scanner stdIn = new Scanner(System.in);
		int key;
		do {
			for (Menu m : Menu.values())
				System.out.printf("(%d) %s  ", m.ordinal(), m.getMessage());
			System.out.print(" : ");
			key = stdIn.nextInt();
		} while (key < Menu.Add.ordinal() || key > Menu.Exit.ordinal());

		return Menu.MenuAt(key);
	}

	public static void main(String[] args) {

		Random rand = new Random();
		Scanner stdIn = new Scanner(System.in);
		Tree t = new Tree();
		Menu menu; // 메뉴
		int count = 0;
		int num;
		boolean result;
		do {
			switch (menu = SelectMenu()) {
			case Add: // 노드 삽입
				System.out.println("The number of items = ");
				count = stdIn.nextInt();
				int[] input = new int[count];
				for (int ix = 0; ix < count; ix++) {
					input[ix] = rand.nextInt(20);
				}
				for (int i : input) {
					if (t.add(i) == false)
						System.out.printf("Insert Duplicated data : %s \n", i);
				}
//				for (int i : t.nodeList) {
//					System.out.print(i + " ");
//				}
//				System.out.println();
//				if (t.add(count) == false)
//					System.out.printf("Insert Duplicated data : %s \n", count);
//				System.out.printf("root : %s\n", t.root.data);
				break;

			case Delete: // 노드 삭제
				System.out.println("삭제할 데이터: ");
				num = stdIn.nextInt();
				if (t.delete(num) == true) {
					System.out.println("삭제 데이터 = " + num + " 성공");
//					System.out.printf("root : %s\n", t.root.data);
//					for(int i : t.nodeList) {
//						System.out.print(i + " ");
//					}
//					System.out.println();
				} else
					System.out.println("해당 데이터가 존재하지 않습니다.");
				break;

			case Search: // 노드 검색
				System.out.println("검색할 데이터: ");

				num = stdIn.nextInt();
				result = t.search(num);
				if (result == true)
					System.out.printf("해당 데이터(%d)가 존재합니다.", num);
				else
					System.out.println("해당 데이터가 존재하지 않습니다.");
				break;

			case InorderPrint: // 전체 노드를 키값의 오름차순으로 표시
				System.out.print("전위 순회 :");
				t.preorder();
				System.out.println();
				System.out.print("중위 순회 :");
				t.inorder();
				System.out.println();
				System.out.print("후위 순회 :");
				t.postorder();
				System.out.println();
				break;
			}
		} while (menu != Menu.Exit);
	}
}
