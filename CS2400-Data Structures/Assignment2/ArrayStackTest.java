package assignment2;

public class ArrayStackTest {

	public static void main(String[] args) {
		String postfix = "ab * ca - / de * +";
		System.out.println("Result: " + ResizeableArrayStack.evaluatePostfix(postfix));
	}
}
