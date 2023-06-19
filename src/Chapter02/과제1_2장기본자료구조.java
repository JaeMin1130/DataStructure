package Chapter02;

class Phy implements Comparable<Phy> {
	String name;
	int height;
	double vision;

	Phy() {
	}

	Phy(String name, int height, double vision) {
		this.name = name;
		this.height = height;
		this.vision = vision;
	}

	@Override
	public int compareTo(Phy o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void showData(Phy[] data) {
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i].name + " " + data[i].height + " " + data[i].vision);
		}
	}

	public static void swap(Phy[] data, int i, int j) {
		Phy store = new Phy();
		store = data[i];
		data[i] = data[j];
		data[j] = store;
	}

	public static void sortData(Phy[] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = i; j < data.length; j++) {
				if (data[i].name.compareTo(data[j].name) > 0) {
					swap(data, i, j);
				}
				if(data[i].name.equals(data[j].name) && data[i].height > data[j].height) {
					swap(data, i, j);
				}
				if(data[i].name.equals(data[j].name) && data[i].height == data[j].height &&data[i].vision > data[j].vision) {
					swap(data, i, j);
				}
			}
		}

	}

}

public class 과제1_2장기본자료구조 {
	public static void main(String[] args) {
		Phy[] data = {
						new Phy("홍길", 162, 0.3),
						new Phy("홍길", 152, 0.7),
						new Phy("김홍길동", 182, 0.3),
						new Phy("홍동", 164, 1.3),
						new Phy("길동", 182, 0.6),
						new Phy("길동", 167, 0.2),
						new Phy("길동", 167, 0.5),
		};
		Phy.sortData(data);
		Phy.showData(data);
	}
}