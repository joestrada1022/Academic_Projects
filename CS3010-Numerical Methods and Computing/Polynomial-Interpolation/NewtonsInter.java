package assignment3;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NewtonsInter {

	public static void main(String[] args) throws IOException{
		if(args.length != 1) {
			System.out.println("This program requires ONE file argument.");
			System.exit(0);
		}
		File file = new File(args[0]);
		Scanner scanner = new Scanner(file);
		
		String xsLine = scanner.nextLine();
		String[] xs = xsLine.split("\\s+");
		
		String ysLine = scanner.nextLine();
		String[] ys = ysLine.split("\\s+");
		scanner.close();
		
		if(xs.length != ys.length) {
			System.out.println("There must be the same amount of Xs and Ys.");
			System.exit(0);
		}
		
        double[] xsDoubles = convertToDoubles(xs);
        double[] ysDoubles = convertToDoubles(ys);
		

		double[] cs = new double[xsDoubles.length];
		coeff(xsDoubles, ysDoubles, cs);
		
		scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("Enter a value to evaluate the polynomial at (or press 'q' to quit): ");
			String input = scanner.nextLine();
			System.out.println();
			if(input.equals("q")) {
				scanner.close();
				break;
			}
			try {
				double z = Double.parseDouble(input);				
				System.out.println("Result at z = " + z + ": " + evalNewton(xsDoubles, cs, z));
				System.out.println();
			} catch (NumberFormatException e) {
				System.out.println("Must enter a numerical value or 'q'\n");
			}
		}
		System.out.println("\nExiting Program...");
	}
	
    private static double[] convertToDoubles(String[] strings) {
        double[] doubles = new double[strings.length];
        for (int i = 0; i < strings.length; i++) {
            doubles[i] = Double.parseDouble(strings[i]);
        }
        return doubles;
    }	
	
	
	
	public static void coeff (double[] xs, double[] ys, double[] cs) {
		int n = xs.length - 1; // this works because the size of the vectors should be n + 1
		for(int i = 0; i <= n; i++) {
			cs[i] = ys[i];
		}
		
		for(int j = 1; j <= n; j++) {
			for(int i = n; i >= j; i--) {
				cs[i] = (cs[i] - cs[i - 1]) / (xs[i] - xs[i - j]);
			}
		}
	}
	public static double evalNewton(double[] xs, double[] cs, double z) {
		int n = cs.length - 1;
		double result = cs[n];
		
		for(int i = n-1; i >= 0; i--) {
			result = result * (z - xs[i]) + cs[i];
		}
		return result;
	}

}
