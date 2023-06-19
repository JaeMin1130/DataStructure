package chapter08;

import java.util.Comparator;
import java.util.Scanner;

class SimpleObject {
	static final int NO = 1; // 번호를 읽어 들일까요?
	static final int NAME = 2; // 이름을 읽어 들일까요?

	private String no; // 회원번호
	private String name; // 이름

	public SimpleObject() {
		no = null;
		name = null;
	}

	// --- 문자열 표현을 반환 ---//
	public String toString() {
		return String.format("[%s, %s]", no, name);
	}

	// --- 데이터를 읽어 들임 ---//
	void scanData(String guide, int sw, Scanner sc) {
		System.out.println(guide + "할 데이터를 입력하세요.");

		if ((sw & NO) == NO) { // & 는 bit 연산자임
			System.out.print("번호: ");
			no = sc.next();
		}
		if ((sw & NAME) == NAME) {
			System.out.print("이름: ");
			name = sc.next();
		}
	}

	// --- 회원번호로 순서를 매기는 comparator ---//
	public static final Comparator<SimpleObject> NO_ORDER = new NoOrderComparator();

	private static class NoOrderComparator implements Comparator<SimpleObject> {
		public int compare(SimpleObject d1, SimpleObject d2) {
			return (d1.no.compareTo(d2.no) > 0) ? 1 : (d1.no.compareTo(d2.no) < 0) ? -1 : 0;
		}
	}

	// --- 이름으로 순서를 매기는 comparator ---//
	public static final Comparator<SimpleObject> NAME_ORDER = new NameOrderComparator();

	private static class NameOrderComparator implements Comparator<SimpleObject> {
		public int compare(SimpleObject d1, SimpleObject d2) {
			return d1.name.compareTo(d2.name);
		}
	}
}

class Node2 {
	SimpleObject data;
	Node2 link;

	public Node2(SimpleObject element) {
		data = element;
		link = null;
	}
}

class LinkedList2 {
	Node2 first;

	public LinkedList2() {
		first = null;
	}

	public void Add(SimpleObject element, Comparator<SimpleObject> cc) {
		Node2 newNode = new Node2(element);

		if (first == null) {
			first = newNode;
			return;
		}

		Node2 cur = first; // cur는 first의 주솟값을 가리킴
		Node2 pre = null;

		while (cur != null && cc.compare(cur.data, element) < 0) {
			pre = cur;
			cur = cur.link;
		}
		// element <= 맨 앞 노드 data
		if (pre == null) {
			newNode.link = cur;
			first = newNode;
			// element가 list 사이나 끝에 들어갈 때
		} else {
			newNode.link = cur;
			pre.link = newNode;
		}
	}

	public void Delete(SimpleObject element, Comparator<SimpleObject> cc) {
		if (first == null) {
			System.out.println("List is empty.");
			return;
		}

		Node2 cur = first;
		Node2 pre = null;

		while (cur != null) {
			if (cc.compare(cur.data, element) == 0) {
				if (pre == null) { // element가 맨 앞 노드의 data면
					first = cur.link;
				} else {
					pre.link = cur.link;
				}
				System.out.printf("삭제된 데이터는 %s입니다.", cur.data);
				return;
			}
			pre = cur;
			cur = cur.link;
		}

		System.out.println("삭제하려는 데이터가 존재하지 않습니다.");
	}

	public void Show() {
		if (first == null) {
			System.out.println("List is empty.");
			return;
		}

		Node2 cur = first;
		while (cur != null) {
			System.out.print(cur.data + " ");
			cur = cur.link;
		}

		System.out.println();
	}

	public void Search(SimpleObject element, Comparator<SimpleObject> cc) {
		if (first == null) {
			System.out.println("List is empty.");
			return;
		}
		Node2 cur = first;
		while (cur != null) {
			if (cc.compare(cur.data, element) == 0) {
				System.out.println("검색 값 = " + element + ", 데이터가 존재합니다.");
				return;
			}
			cur = cur.link;
		}
		System.out.println("검색 값 = " + element + ", 데이터가 존재하지 않습니다.");
		return;
	}
}

public class 객체연결리스트 {

	enum Menu {
		Add("입력"),
		Delete("삭제"),
		Show("출력"),
		Search("검색"),
		Exit("종료");

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
		Scanner sc = new Scanner(System.in);
		int key;
		do {
			for (Menu m : Menu.values()) {
				System.out.printf("(%d) %s  ", m.ordinal(), m.getMessage());
				if ((m.ordinal() % 3) == 2 &&
						m.ordinal() != Menu.Exit.ordinal())
					System.out.println();
			}
			System.out.print(" : ");
			key = sc.nextInt();
		} while (key < Menu.Add.ordinal() ||
				key > Menu.Exit.ordinal());
		return Menu.MenuAt(key);
	}

	public static void main(String[] args) {
		Menu menu;
		LinkedList2 l = new LinkedList2();
		Scanner sc = new Scanner(System.in);
		SimpleObject data;
		do {
			switch (menu = SelectMenu()) {
				case Add:
					data = new SimpleObject();
					data.scanData("입력", 3, sc);
					l.Add(data, SimpleObject.NO_ORDER);
					break;
				case Delete:
					data = new SimpleObject();
					data.scanData("삭제", SimpleObject.NO, sc);
					l.Delete(data, SimpleObject.NO_ORDER);
					break;
				case Show:
					l.Show();
					break;
				case Search:
					data = new SimpleObject();
					data.scanData("검색", SimpleObject.NO, sc);
					l.Search(data, SimpleObject.NO_ORDER);
					break;
				case Exit:
					break;
			}
		} while (menu != Menu.Exit);
		sc.close();
	}
}
