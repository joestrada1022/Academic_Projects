package assignment3;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class GetPoints {
	 public static void main(String[] args) throws IOException{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a positive integer n: ");
		int n = scanner.nextInt();
		System.out.println();
		if(n < 0) {
			System.out.println("Must be a positive integer.");
			System.exit(0);
		}
		File file = new File("points.txt");
		PrintWriter writer = new PrintWriter(file);
		double min = -100.0;
		double max = 200.0;
		for(int i = 0; i < n; i++) {
			writer.print((min + (max - min) * Math.random()));
			if(i != n - 1) {
				writer.print(" ");
			}
		}
		writer.println();
		for(int i = 0; i < n; i++) {
			writer.print((min + (max - min) * Math.random()));
			if(i != n - 1) {
				writer.print(" ");
			}
		}
		System.out.println("Successfully saved points of size " + n + " to points.txt");
		System.out.println("\n\nExiting Program...");
		scanner.close();
		writer.close();
	}
}
