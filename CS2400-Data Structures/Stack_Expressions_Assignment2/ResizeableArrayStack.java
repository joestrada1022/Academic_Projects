package assignment2;
import java.util.Arrays;
import java.util.EmptyStackException;

public final class ResizeableArrayStack<T> implements StackInterface<T> {
	private T[] stack; // Array of stack entries
	private int topIndex; // Index of top entry
	private static final int DEFAULT_CAPACITY = 50;

	public ResizeableArrayStack() {
		this(DEFAULT_CAPACITY);
	} // end default constructor

	public ResizeableArrayStack(int initialCapacity) {
		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
	} // end constructor

	public void push(T newEntry) {
		ensureCapacity();
		stack[topIndex + 1] = newEntry;
		topIndex++;
	} // end push

	private void ensureCapacity() {
		if (topIndex >= stack.length - 1) { // If array is full, double its size
			int newLength = 2 * stack.length;
			stack = Arrays.copyOf(stack, newLength);
		} // end if
	} // end ensureCapacity

	public T pop() {
		if (isEmpty())
			throw new EmptyStackException();
		else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		} // end if
	} // end pop

	public T peek() {
		if (isEmpty())
			throw new EmptyStackException();
		else
			return stack[topIndex];
	} // end peek

	public boolean isEmpty() {
		return topIndex < 0;
	} // end isEmpty

	public void clear() {
		// Remove references to the objects in the stack,
		// but do not deallocate the array
		while (topIndex > -1) {
			stack[topIndex] = null;
			topIndex--;
		} // end while
			// Assertion: topIndex is -1
	} // end clear

	// Evaluates a given postfix expression and returns a numerical value
	public static int evaluatePostfix(String postfix) {
		ResizeableArrayStack<Integer> valueStack = new ResizeableArrayStack<>(postfix.length());
		int postfixSize = postfix.length();
		int operandOne, operandTwo;
		char nextChar;
		for (int i = 0; i < postfixSize; i++) {
			nextChar = postfix.charAt(i);
			// Checks if next character is a letter
			if (Character.isLetter(nextChar)) {
				// Checks what letter it is, 
				// and converts it to predefined int value given on the Assignment#2 doc
				switch (nextChar) {
				case 'a':
					valueStack.push(2);
					break;
				case 'b':
					valueStack.push(3);
					break;
				case 'c':
					valueStack.push(4);
					break;
				case 'd':
					valueStack.push(5);
					break;
				case 'e':
					valueStack.push(6);
					break;
				default:
					throw new IllegalArgumentException("Undefined Variable. Only a,b,c,d,e are defined");
				}
			} // end if
			// checks if the next character is an operator
			else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/' || nextChar == '^') {
				// Gets both operands
				operandTwo = valueStack.pop();
				operandOne = valueStack.pop();
				// Checks what operation to perform on both operands
				switch (nextChar) {
				case '+':
					valueStack.push(operandOne + operandTwo);
					break;
				case '-':
					valueStack.push(operandOne - operandTwo);
					break;
				case '*':
					valueStack.push(operandOne * operandTwo);
					break;
				case '/':
					valueStack.push(operandOne / operandTwo);
					break;
				case '^':
					valueStack.push(operandOne ^ operandTwo);
					break;
				} // end switch
			} // end else if
		} // end for
		return valueStack.peek();
	} // end evaluatePostfix

} // end ResizeableArrayStack
