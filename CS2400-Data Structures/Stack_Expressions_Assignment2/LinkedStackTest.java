package assignment2;

public class LinkedStackTest {

	public static void main(String[] args) {
		String infix = "a * b /(c-a)+d*e";
		System.out.println("Resulting Postfix Expression: " + LinkedStack.convertToPostfix(infix));
	}

}
