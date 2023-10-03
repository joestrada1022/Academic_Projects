package assignment1;

public class LinkedBag<T> implements BagInterface<T> {

	private Node firstNode;
	private int numberOfEntries;

	public LinkedBag() {
		firstNode = null;
		numberOfEntries = 0;
	}

	public int getCurrentSize() {
		return numberOfEntries;
	}

	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	public boolean add(T newEntry) {
		Node newNode = new Node(newEntry);
		newNode.next = firstNode;

		firstNode = newNode;
		numberOfEntries++;

		return true;
	}

	public T remove() {
		T result = null;
		if (firstNode != null) {
			result = firstNode.getData();
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
		}
		return result;
	}

	private Node getReferenceTo(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;

		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData())) {
				found = true;
			} else
				currentNode = currentNode.getNextNode();
		}
		return currentNode;
	}

	public boolean remove(T anEntry) {
		boolean result = false;
		Node nodeN = getReferenceTo(anEntry);

		if (nodeN != null) {
			nodeN.setData(firstNode.getData());
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
			result = true;
		}
		return result;
	}

	public void clear() {
		while (!isEmpty()) {
			remove();
		}
	}

	public int getFrequencyOf(T anEntry) {
		int frequency = 0;
		int counter = 0;

		Node currentNode = firstNode;
		while ((counter < numberOfEntries) && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData())) {
				frequency++;
			}
			counter++;
			currentNode = currentNode.getNextNode();
		}
		return frequency;
	}

	public boolean contains(T anEntry) {
		boolean found = false;
		Node currentNode = firstNode;

		while ((!found) && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData())) {
				found = true;
			} else {
				currentNode = currentNode.getNextNode();
			}
		}
		return found;
	}

	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[numberOfEntries];

		int index = 0;
		Node currentNode = firstNode;
		while ((index < numberOfEntries) && (currentNode != null)) {
			result[index] = currentNode.getData();
			index++;
			currentNode = currentNode.getNextNode();
		}
		return result;
	}

	// Converts both current bag and other bag into an array
	// Iterates over both arrays and adds each entry to the newBag
	public BagInterface<T> union(BagInterface<T> otherBag) {
		BagInterface<T> newBag = new LinkedBag<>();
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
		BagInterface<T> newBag = new LinkedBag<>();
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
		BagInterface<T> newBag = new LinkedBag<T>();
		T[] thisArray = this.toArray();
		T[] otherArray = otherBag.toArray();

		for (int i = 0; i < thisArray.length; i++) {
			newBag.add(thisArray[i]);
		}

		for (int i = 0; i < otherArray.length; i++) {
			if (newBag.contains(otherArray[i]))
				newBag.remove(otherArray[i]);
		}
		return newBag;
	}

	private class Node {
		private T data;
		private Node next;

		private Node(T dataPortion) {
			this(dataPortion, null);
		}

		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		}

		private T getData() {
			return data;
		}

		private void setData(T newData) {
			data = newData;
		}

		private Node getNextNode() {
			return next;
		}

		private void setNextNode(Node nextNode) {
			next = nextNode;
		}
	}

}
