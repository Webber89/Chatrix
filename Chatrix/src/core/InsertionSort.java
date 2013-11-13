package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class InsertionSort
{

    public static void main(String[] args) throws JsonParseException,
	    JsonMappingException, IOException
    {
	test();
    }

    public static void test()
    {
	ArrayList<String> liste = new ArrayList<String>();
	liste.add("Poul");
	liste.add("Brian");
	liste.add("Hans");
	liste.add("Emil");
	liste = sortWithComp(liste, new Comparator<String>()
	{

	    @Override
	    public int compare(String o1, String o2)
	    {
		return o1.compareTo(o2);
	    }
	});
	for (String s : liste)
	{
	    System.out.println(s);
	}
    }

    public static <T> ArrayList<T> sortWithComp(ArrayList<T> unsortedList,
	    Comparator<T> comparator)
    {

	// SortedList - output
	ArrayList<T> sortedList = new ArrayList<T>(unsortedList);

	// As long as sorted elements is less than total elements
	for (int sortedElements = 1; sortedElements < sortedList.size(); sortedElements++)
	{

	    // Points to the next item to insert
	    T itemToInsert = sortedList.get(sortedElements);
	    int pointer;
	    // points to the last element being sorted - if pointer is >=0 and
	    // the element pointed to is bigger than the item to insert
	    for (pointer = sortedElements - 1; (pointer >= 0)
		    && (comparator.compare(sortedList.get(pointer),
			    itemToInsert) > 0); pointer--)
	    {

		sortedList.set(pointer + 1, sortedList.get(pointer));
	    }

	    sortedList.set(pointer + 1, itemToInsert);
	}
	return sortedList;
    }

    public static <T extends Comparable<T>> List<T> sortMoreUsers(List<T> users)
    {
	List<T> sortedList = new ArrayList<T>(users);

	for (int sortedElements = 1; sortedElements < sortedList.size(); sortedElements++)
	{

	    T itemToInsert = sortedList.get(sortedElements);
	    int pointer;

	    for (pointer = sortedElements - 1; (pointer >= 0)
		    && (sortedList.get(pointer).compareTo(itemToInsert) > 0); pointer--)
	    {
		sortedList.set(pointer + 1, sortedList.get(pointer));
	    }

	    sortedList.set(pointer + 1, itemToInsert);
	}
	return sortedList;
    }

    public static ArrayList<Integer> sortUsers(ArrayList<Integer> unsortedList)
    {

	ArrayList<Integer> sortedList = new ArrayList<Integer>(unsortedList);

	for (Integer i : sortedList)
	{
	    System.out.print(i + " - ");
	}
	System.out.println("\n-------");
	int sortedElements;

	for (sortedElements = 1; sortedElements < sortedList.size(); sortedElements++)
	{

	    int itemToInsert = sortedList.get(sortedElements);
	    int pointer;

	    for (pointer = sortedElements - 1; (pointer >= 0)
		    && (sortedList.get(pointer) < itemToInsert); pointer--)
	    {
		sortedList.set(pointer + 1, sortedList.get(pointer));

	    }
	    sortedList.set(pointer + 1, itemToInsert);
	}
	for (Integer i : sortedList)
	{
	    System.out.print(i + " - ");
	}
	System.out.println("\n-------");
	return sortedList;
    }
}
