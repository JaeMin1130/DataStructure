import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Scanner;

class SimpleObject2 {
	static final int NO = 1; // 번호를 읽어 들일까요?
	static final int NAME = 2; // 이름을 읽어 들일까요?

	public Integer no; // 회원번호
	public String name; // 이름

	// --- 문자열 표현을 반환 ---//
	public String toString() {
		return "(" + no + ") " + name;
	}

	public SimpleObject2() {
		no = null;
		name = null;
	}

	public SimpleObject2(Integer no, String name) {
		this.no = no;
		this.name = name;
	}

	// --- 데이터를 읽어 들임 ---//
	void scanData(String guide, int sw, SimpleObject2 so) {
		Scanner sc = new Scanner(System.in);
		System.out.println(guide + "할 데이터를 입력하세요." + sw);

		if ((sw & NO) == NO) { // & 는 bit 연산자임
			System.out.print("번호: ");
			so.no = sc.nextInt();
		}
		if ((sw & NAME) == NAME) {
			System.out.print("이름: ");
			so.name = sc.next();
		}
	}

	// --- 회원번호로 순서를 매기는 comparator ---//
	public static final Comparator<SimpleObject2> NO_ORDER = new NoOrderComparator();

	private static class NoOrderComparator implements Comparator<SimpleObject2> {
		public int compare(SimpleObject2 d1, SimpleObject2 d2) {
			return (d1.no.compareTo(d2.no) > 0) ? 1 : (d1.no.compareTo(d2.no) < 0) ? -1 : 0;
		}
	}

	// --- 이름으로 순서를 매기는 comparator ---//
	public static final Comparator<SimpleObject2> NAME_ORDER = new NameOrderComparator();

	private static class NameOrderComparator implements Comparator<SimpleObject2> {
		public int compare(SimpleObject2 d1, SimpleObject2 d2) {
			return d1.name.compareTo(d2.name);
		}
	}
}

//정수를 저정하는 이진트리 만들기 실습
class TreeNode {
	TreeNode leftChild;
	SimpleObject2 data;
	TreeNode rightChild;

	public TreeNode() {
		leftChild = rightChild = null;
	}

	TreeNode(SimpleObject2 data) {
		this.data = data;
		leftChild = rightChild = null;
	}
}

class Tree4 {
	TreeNode root;
	ArrayList<Integer> nodeList;

	Tree4() {
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
	
	Deque<SimpleObject2> queData(TreeNode current) {
		TreeNode temp = current;
		Deque<SimpleObject2> qu = new ArrayDeque<SimpleObject2>();
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
		Deque<SimpleObject2> qu = queData(node);
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
		if (isLeftChild(prev, cur.data, SimpleObject2.NO_ORDER)) {
			cur = null;
			prev.leftChild = cur;
		} else {
			cur = null;
			prev.rightChild = cur;
		}
		return node;
	}

	boolean isLeftChild(TreeNode prev, SimpleObject2 data, Comparator<? super SimpleObject2> c) {
		if (c.compare(prev.data, data) > 0) {
			return true;
		}
		return false;
	}

	boolean add(SimpleObject2 so, Comparator<? super SimpleObject2> c) {
		if (nodeList.contains(so.no)){
			return false;
		}

		TreeNode newNode = new TreeNode(so);

		if (root == null) {
			root = newNode;
			nodeList.add(so.no);
			return true;
		}
		TreeNode prev = null;
		TreeNode cur = root;

		while (cur != null) {
			if (c.compare(so, cur.data) > 0) {
				prev = cur;
				cur = cur.rightChild;
			} else if (c.compare(so, cur.data) < 0) {
				prev = cur;
				cur = cur.leftChild;
			} else {
				return false;
			}
		}

		if (isLeftChild(prev, so,  SimpleObject2.NO_ORDER)) {
			prev.leftChild = newNode;
		} else {
			prev.rightChild = newNode;
		}
		nodeList.add(so.no);
		return true;
	}

	boolean delete(SimpleObject2 so, Comparator<? super SimpleObject2> c) {
		TreeNode prev = null;
		TreeNode cur = root;

		if (!nodeList.contains(so.no)) {
			return false;
		}
		while (cur != null) {
			if (c.compare(cur.data, so) == 0) {
				if (cur == root) { // root일 때
					remodel(root);
				} else if (isLeafNode(cur)) { // leaf일 때
					if (isLeftChild(prev, so, SimpleObject2.NO_ORDER)) {
						cur = null;
						prev.leftChild = cur;
					} else {
						cur = null;
						prev.rightChild = cur;
					}
				} else { // 사이에 있을 때
					if (isLeftChild(prev, so, SimpleObject2.NO_ORDER)) {
						prev.leftChild = remodel(cur);
					} else {
						prev.rightChild = remodel(cur);
					}
				}
				nodeList.remove(so.no);
				return true;
			} else {
				if (isLeftChild(cur, so, SimpleObject2.NO_ORDER)) {
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

	boolean search(SimpleObject2 so) {
		if (nodeList.contains(so.no)) {
			return true;
		}
		return false;
	}
}

public class BinaryTree_SimpleObject {

	enum Menu {
		Add("삽입"), Delete("삭제"), Search("검색"), InorderPrint("정렬인쇄"), Exit("종료");

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
		Scanner sc2 = new Scanner(System.in);
		Tree4 t = new Tree4();
		Menu menu; // 메뉴
		SimpleObject2 so;
		boolean result;
		do {
			switch (menu = SelectMenu()) {
			case Add: 
				SimpleObject2[] sox = { new SimpleObject2(33, "ee"), new SimpleObject2(55, "tt"),
						new SimpleObject2(22, "ww"), new SimpleObject2(66, "yy"), new SimpleObject2(21, "wq") };
				for (SimpleObject2 soz : sox){
					if(!t.add(soz, SimpleObject2.NO_ORDER)){
						System.out.printf("입력한 회원 번호(%d)에 해당하는 데이터가 이미 존재합니다.\n", soz.no);
					}
				}
				break;

			case Delete: 
				so = new SimpleObject2();
				so.scanData("삭제", SimpleObject2.NO, so);
				if (t.delete(so, SimpleObject2.NO_ORDER) == true) {
					System.out.println("삭제된 데이터 = 회원번호: " + so.no);
				} else
					System.out.printf("회원번호: %d인 데이터가 존재하지 않습니다.", so.no);
				break;

			case Search: 
				so = new SimpleObject2();
				so.scanData("검색", SimpleObject2.NO, so);
				result = t.search(so);
				if (result == false)
					System.out.println("검색 값 = 회원번호: " + so.no + "인 데이터가 존재하지 않습니다.");
				else
					System.out.println("검색 값 = 회원번호: " + so.no + "인 데이터가 존재합니다.");
				break;

			case InorderPrint:
				t.preorder();
				System.out.println();
				t.inorder();
				System.out.println();
				t.postorder();
				System.out.println();
				break;

			case Exit:
				break;
			}
		} while (menu != Menu.Exit);
		sc2.close();
	}
}
