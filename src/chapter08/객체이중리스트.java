
package chapter08;

import java.util.Comparator;
import java.util.Scanner;

class SimpleObject2 {
	static final int NO = 1; // 번호를 읽어 들일까요?
	static final int NAME = 2; // 이름을 읽어 들일까요?
	String no; // 회원번호
	String name; // 이름

	public SimpleObject2() {
		this.no = null;
		this.name = null;
	}

	public SimpleObject2(String sno, String sname) {
		this.no = sno;
		this.name = sname;
	}

	// --- 문자열 표현을 반환 ---//
	public String toString() {
		return String.format("[%s, %s]", no, name);
	}

	// --- 데이터를 읽어 들임 ---//
	void scanData(String guide, int sw) {
		Scanner sc = new Scanner(System.in);
		System.out.println(guide + "할 데이터를 입력하세요." + sw);

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
	public static final Comparator<SimpleObject2> NO_ORDER = new NoOrderComparator();

	private static class NoOrderComparator implements Comparator<SimpleObject2> {
		public int compare(SimpleObject2 d1, SimpleObject2 d2) {
			return (d1.no.compareTo(d2.no) > 0) ? 1 : ((d1.no.compareTo(d2.no) < 0)) ? -1 : 0;
		}
	}

	// --- 이름으로 순서를 매기는 comparator ---//
	public static final Comparator<SimpleObject2> NAME_ORDER = new NameOrderComparator();

	private static class NameOrderComparator implements Comparator<SimpleObject2> {
		public int compare(SimpleObject2 d1, SimpleObject2 d2) {
			return (d1.name.compareTo(d2.name) > 0) ? 1 : ((d1.name.compareTo(d2.name) < 0)) ? -1 : 0;
		}
	}
}

class DoubleLinkedList {
	// 노드
	class Node {
		private SimpleObject2 data; // 데이터
		private Node prev; // 앞쪽 포인터(앞쪽 노드에 대한 참조)
		private Node next; // 뒤쪽 포인터(다음 노드에 대한 참조)

		// 생성자
		Node() {
			data = null;
			prev = next = this; //
		}
		
		Node(SimpleObject2 so) {
			this.data = so;
			prev = next = this;
		}

	}

	private Node head; // 머리 포인터(참조하는 곳은 더미 노드)

	// 생성자
	public DoubleLinkedList() {
		head = new Node(); // 더미 노드를 생성
	}

	// 리스트가 비어 있는지(더미 노드만 있는지)
	public boolean isEmpty() {
		return head.next == head;
	}

	public void add(SimpleObject2 obj, Comparator<SimpleObject2> cc) {
		Node newNode = new Node(obj);
		
		if (head.next == head) {
			newNode.next = newNode.prev = head;
			head.next = head.prev = newNode;
			return;
		}

		Node curNode = head.next; // 
		Node preNode = head;

		while (curNode != head && cc.compare(curNode.data, obj) < 0) {
			preNode = curNode;
			curNode = curNode.next;
		}
		// element <= 맨 앞 노드 data
		if (preNode == head) {
			newNode.prev = head;
			newNode.next = curNode;
			head.next = curNode.prev = newNode;
		// element가 list 사이나 끝에 들어갈 때
		} else {
			newNode.next = curNode;
			newNode.prev = preNode;
			preNode.next = curNode.prev = newNode;
		}
	}

	public void delete(SimpleObject2 obj, Comparator<SimpleObject2> cc) {
		if (isEmpty()) {
			System.out.println("List is empty.");
			return;
		}
		Node curNode = head.next;
		Node preNode = head;
		while (curNode != head) {
			if (cc.compare(curNode.data, obj) == 0) {
				// 참조 끊기
				if (preNode == head) { // element가 맨 앞 노드의 data면
					head.next = curNode.next;
					curNode.next.prev = head;
				} else {
					
					preNode.next = curNode.next;
					curNode.next.prev = preNode;
				}
				System.out.printf("삭제된 데이터는 %s입니다.", curNode.data);
				return;
			}
			preNode = curNode;
			curNode = curNode.next;
		}
		System.out.println("삭제하려는 데이터가 존재하지 않습니다.");
	}

	public void show() {
		if (isEmpty()) {
			System.out.println("List is empty.");
			return;
		}
		Node ptr = head.next;

		while (ptr != head) {
			System.out.print(ptr.data + " ");
			ptr = ptr.next;
		}
	}

	// 노드를 검색
	public void search(SimpleObject2 obj, Comparator<SimpleObject2> c) {
		if (isEmpty()) {
			System.out.println("List is empty.");
			return;
		}
		Node ptr = head.next; // 현재 스캔 중인 노드

		while (ptr != head) { 
			if (c.compare(obj, ptr.data) == 0) {
				System.out.println("검색 값 = " + ptr.data + ", 데이터가 존재합니다.");
				return ; // 검색 성공
			}
			ptr = ptr.next; // 다음 노드 선택
		}
		System.out.println("검색 값 = " + obj + ", 데이터가 없습니다.");
		return ; // 검색 실패
	}
}

public class 객체이중리스트 {
	enum Menu {
		Add("삽입"), Delete("삭제"), Show("인쇄"), Search("검색"), Exit("종료");

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
		Scanner sc1 = new Scanner(System.in);
		int key;
		do {
			for (Menu m : Menu.values()) {
				System.out.printf("(%d) %s  ", m.ordinal(), m.getMessage());
				if ((m.ordinal() % 3) == 2 && m.ordinal() != Menu.Exit.ordinal())
					System.out.println();
			}
			System.out.print(" : ");
			key = sc1.nextInt();
		} while (key < Menu.Add.ordinal() || key > Menu.Exit.ordinal());
		return Menu.MenuAt(key);
	}

	public static void main(String[] args) {
		Menu menu; // 메뉴
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Linked List");
		DoubleLinkedList lst1 = new DoubleLinkedList();
		String sno1 = null, sname1 = null;
		SimpleObject2 so;
		boolean result = false;
		do {
			switch (menu = SelectMenu()) {
			case Add:
				so = new SimpleObject2();
				so.scanData("입력", 3);
				lst1.add(so, SimpleObject2.NO_ORDER);
				break;
			case Delete:
				so = new SimpleObject2();
				so.scanData("삭제", SimpleObject2.NO);
				lst1.delete(so, SimpleObject2.NO_ORDER);
				break;
			case Show:
				lst1.show();
				break;
			case Search:
				so = new SimpleObject2();
				so.scanData("탐색", SimpleObject2.NO);
				lst1.search(so, SimpleObject2.NO_ORDER);
				break;
			case Exit: // 꼬리 노드 삭제
				break;
			}
		} while (menu != Menu.Exit);
	}
}
