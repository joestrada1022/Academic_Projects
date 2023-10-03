package assignment1;
import java.util.Arrays;

public class ResizeableArrayBag<T> implements BagInterface<T> {

	private T[] bag;
	private static final int DEFAULT_CAPACITY = 25;
	private int numberOfEntries;

	public ResizeableArrayBag() {
		this(DEFAULT_CAPACITY);
	}

	public ResizeableArrayBag(int capacity) {
		@SuppressWarnings("unchecked")
		T[] tempBag = (T[]) new Object[capacity];
		bag = tempBag;
		numberOfEntries = 0;
	}

	public int getCurrentSize() {
		return numberOfEntries;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	private boolean isArrayFull() {
		return numberOfEntries == bag.length;
	}

	private void doubleCapacity() {
		int newLength = 2 * bag.length;
		bag = Arrays.copyOf(bag, newLength);
	}

	public boolean add(T newEntry) {
		if (isArrayFull()) {
			doubleCapacity();
		}

		bag[numberOfEntries] = newEntry;
		numberOfEntries++;

		return true;
	}

	public T remove() {
		T result = removeEntry(numberOfEntries - 1);
		return result;
	}

	public boolean remove(T anEntry) {
		int index = getIndexOf(anEntry);
		T result = removeEntry(index);
		return anEntry.equals(result);
	}

	public void clear() {
		// brute force method
		while (!isEmpty()) {
			remove();
		}
		// can also set numEntries to 0
	}

	public int getFrequencyOf(T anEntry) {
		int count = 0;

		for (int index = 0; index < numberOfEntries; index++) {
			if (anEntry.equals(bag[index])) {
				count++;
			}
		}
		return count;
	}

	private int getIndexOf(T anEntry) {
		int where = -1;
		boolean found = false;
		int index = 0;

		while (!found && (index < numberOfEntries)) {
			if (anEntry.equals(bag[index])) {
				found = true;
				where = index;
			}
			index++;
		}
		return where;
	}

	private T removeEntry(int givenIndex) {
		T result = null;

		if (!isEmpty() && (givenIndex >= 0)) {
			result = bag[givenIndex];
			bag[givenIndex] = bag[numberOfEntries - 1];
			bag[numberOfEntries - 1] = null;
			numberOfEntries--;
		}
		return result;
	}

	public boolean contains(T anEntry) {
		return getIndexOf(anEntry) > -1;
	}

	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];
		for (int index = 0; index < numberOfEntries; index++) {
			result[index] = bag[index];
		}
		return result;
	}

	// Converts both current bag and other bag into an array
	// Iterates over both arrays and adds each entry to the newBag
	public BagInterface<T> union(BagInterface<T> otherBag) {

		BagInterface<T> newBag = new ResizeableArrayBag<>();
		T[] thisBagElements = this.toArray();
		T[] otherBagElements = otherBag.toArray();

		for (int i = 0; i < thisBagElements.length; i++) {
			newBag.add(thisBagElements[i]);
		}

		for (int i = 0; i < otherBagElements.length; i++) {
			newBag.add(otherBagElements[i]);
		}

		return newBag;
	}

	// Converts the current bag into an array
	// Iterates through that array and compares the value at each index
	// For each index value, it checks if it was already added to the newBag
	// If it wasn't, then it checks for the lower frequency of that value in both
	// bags
	// Whichever has the lower frequency, it adds the value that many times to the
	// newBag
	// *Accounts for frequency being zero, which means the bag doesn't contain the
	// value
	public BagInterface<T> intersection(BagInterface<T> otherBag) {
		BagInterface<T> newBag = new ResizeableArrayBag<>();
		T[] thisArray = this.toArray();
		for (int i = 0; i < thisArray.length; i++) {
			if (newBag.contains(thisArray[i]))
				continue;
			int FrequencyOf;
			if (this.getFrequencyOf(thisArray[i]) < otherBag.getFrequencyOf(thisArray[i])) {
				FrequencyOf = this.getFrequencyOf(thisArray[i]);
			} else {
				FrequencyOf = otherBag.getFrequencyOf(thisArray[i]);
			}

			for (int j = 0; j < FrequencyOf; j++)
				newBag.add(thisArray[i]);
		}

		return newBag;
	}

	// Converts both bags to an array
	// Adds the contents of thisBag to the newBag
	// Iterates through the otherBag to see if the newBag contains the value at each
	// index
	// If it does, then it removes it from the newBag
	public BagInterface<T> difference(BagInterface<T> otherBag) {
		BagInterface<T> newBag = new ResizeableArrayBag<>();
		T[] thisArray = this.toArray();
		T[] otherArray = otherBag.toArray();

		for (int i = 0; i < thisArray.length; i++)
			newBag.add(thisArray[i]);

		for (int i = 0; i < otherArray.length; i++)
			if (newBag.contains(otherArray[i]))
				newBag.remove(otherArray[i]);

		return newBag;
	}

}
