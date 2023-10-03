package assignment2;
import java.util.EmptyStackException;

public class LinkedStack<T> implements StackInterface<T> {

	private Node topNode; // references to the first node in the chain

	public LinkedStack() {
		topNode = null;
	} // end default constructor

	public void push(T newEntry) {
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
	} // end push

	public T pop() {
		T top = peek(); // Might throw EmptyStackException
		// Assertion: topNode != null
		topNode = topNode.getNextNode();

		return top;
	} // end pop

	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		} else {
			return topNode.getData();
		}
	} // end peek

	public boolean isEmpty() {
		return topNode == null;
	} // end isEmpty

	public void clear() {
		topNode = null;
	} // end clear

	// Method that checks precedences of the four basic operators
	private static int checkPrecedence(char operator) {
		switch (operator) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return -1;
		}
	}
	
	// Converts infix expression to postfix
	public static String convertToPostfix(String infix) {
		StackInterface<Character> operatorStack = new LinkedStack<>();
		String postfix = "";
		int infixSize = infix.length();
		char nextChar;
		char topOperator;

		for (int i = 0; i < infixSize; i++) {
			nextChar = infix.charAt(i);
			if (Character.isLetter(nextChar)) {
				postfix += nextChar;
			} else if (nextChar == '^') {
				operatorStack.push(nextChar);
			} else if ((nextChar == '+') || (nextChar == '-') || (nextChar == '*') || (nextChar == '/')) {
				while (!operatorStack.isEmpty()
						&& (checkPrecedence(nextChar) <= checkPrecedence(operatorStack.peek()))) {
					postfix += operatorStack.peek();
					operatorStack.pop();
				}
				operatorStack.push(nextChar);
			} else if (nextChar == '(') {
				operatorStack.push(nextChar);
			} else if (nextChar == ')') {
				topOperator = operatorStack.pop();
				while (topOperator != '(') {
					postfix += topOperator;
					topOperator = operatorStack.pop();
				}
			}
		}
		while (!operatorStack.isEmpty()) {
			topOperator = operatorStack.pop();
			postfix += topOperator;
		}
		return postfix;
	}

	private class Node {
		private T data; // entry in stack
		private Node next; // link to next node

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
