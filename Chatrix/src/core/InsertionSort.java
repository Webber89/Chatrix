package core;
import java.util.ArrayList;
import java.util.Collection;

public class InsertionSort {

	public static void main(String[] args) {

		ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(4);
		a.add(452);
		a.add(3);
		a.add(664);
		a.add(5);
		a.add(1);
		sortUsers(a);
	}

	public InsertionSort(Collection<?> unsortedList) {

	}

	public static ArrayList<Integer> sortUsers(ArrayList<Integer> unsortedList) {

		ArrayList<Integer> sortedList = new ArrayList<Integer>(unsortedList);

		for (Integer i : sortedList) {
			System.out.print(i + " - ");
		}
		System.out.println("\n-------");
		int sortedElements;

		for (sortedElements = 1; sortedElements < sortedList.size(); sortedElements++) {

			int itemToInsert = sortedList.get(sortedElements);
			int pointer;

			for (pointer = sortedElements - 1; (pointer >= 0)
					&& (sortedList.get(pointer) < itemToInsert); pointer--) {
				sortedList.set(pointer + 1, sortedList.get(pointer));

			}
			sortedList.set(pointer + 1, itemToInsert);
		}
		for (Integer i : sortedList) {
			System.out.print(i + " - ");
		}
		System.out.println("\n-------");
		return sortedList;
	}
}
