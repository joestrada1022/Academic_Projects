package assignment1;
import java.util.Arrays;

public class LinkedBagTest {

	public static void main(String[] args) {

		// Creates bags for testing
		BagInterface<String> bag1 = new LinkedBag<>();
		BagInterface<String> bag2 = new LinkedBag<>();

		// Adds values to bag1
		bag1.add("a");
		bag1.add("b");
		bag1.add("c");

		// Adds values to bag2
		bag2.add("b");
		bag2.add("b");
		bag2.add("d");
		bag2.add("e");

		// Prints what each bag contains
		System.out.println("Bag1 Contains: " + Arrays.toString(bag1.toArray()));
		System.out.println("Bag2 Contains: " + Arrays.toString(bag2.toArray()) + "\n");

		// Prints what a union between bags would look like
		BagInterface<String> unionedBag = bag1.union(bag2);
		System.out.println("Union: " + Arrays.toString(unionedBag.toArray()));

		// Prints what an intersection between bags would look like
		BagInterface<String> intersectedBag = bag1.intersection(bag2);
		System.out.println("Intersection: " + Arrays.toString(intersectedBag.toArray()));

		// Prints what Bag1 - Bag2 would look like
		BagInterface<String> differencedBag = bag1.difference(bag2);
		System.out.println("Bag1 - Bag2: " + Arrays.toString(differencedBag.toArray()));

		// Prints what Bag2 - Bag1 would look like
		BagInterface<String> differencedBag2 = bag2.difference(bag1);
		System.out.println("Bag2 - Bag1: " + Arrays.toString(differencedBag2.toArray()));
	}

}
