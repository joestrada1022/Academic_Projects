package assignment2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class polRoot {
	public static void main(String[] args) throws IOException{
		double epsilon =  Math.pow(2, -23);
		double delta = 0.0001;
		if (args.length < 2 || args.length > 6) {
			System.out.println(
					"Error: Args must be in the format: \npolRoot [-newt, -sec, -hybrid] [-maxIt n] initP [initP2] polyFileName	");
			return;
		}
		// set defaults
		boolean useBis = true;
		boolean useNewt = false;
		boolean useSec = false;
		boolean useHybrid = false;
		int maxIt = 10000;
		String initP = null;
		String initP2 = null;
		// file name will always be the last one
		String fileName = args[args.length - 1];

		for (int i = 0; i < args.length - 1; i++) {
			switch (args[i]) {
			case "-newt":
				useNewt = true;
				useBis = false;
				break;
			case "-sec":
				useSec = true;
				useBis = false;
				break;
			case "-hybrid":
				useHybrid = true;
				useBis = false;
				break;
			case "-maxIt":
			case "-maxIter":
				if (i + 1 < args.length - 1) {
					try {
						maxIt = Integer.parseInt(args[i + 1]);
						i++;
					} catch (NumberFormatException e) {
						// Ignore invalid argument
					}
				}
				break;
			default:
				if (initP == null) {
					initP = args[i];
				} else if (initP2 == null) {
					initP2 = args[i];
				}
				break;
			}
		}

//		System.out.println("bi " + useBis);
//		System.out.println("newt " + useNewt);
//		System.out.println("sec " + useSec);
//		System.out.println("hybrid " + useHybrid);
//		System.out.println("maxIt " + maxIt);
//		System.out.println("initP " + initP);
//		System.out.println("initp2 " + initP2);
//		System.out.println("fileName " + fileName);
		
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		int term = scanner.nextInt();
		double[] coefficients = new double[term + 1];
		for(int i = coefficients.length - 1; i >= 0; i--) {
			coefficients[i] = scanner.nextDouble();
		}
		scanner.close();
		
		Polynomial f = new Polynomial(term, coefficients);
//		System.out.println(f);
//		System.out.println(f.evalDerivative());
		
		if(useBis) {
			if(initP2 == null) {
				System.out.println("The Bisection method requires two initial points");
				return;
			}
			Object[] solution = bisection(f, Double.parseDouble(initP), Double.parseDouble(initP2), maxIt, epsilon);
			createFile(solution, fileName);
		}
		else if(useNewt) {
			Object[] solution = newton(f, f.evalDerivative(), Double.parseDouble(initP), maxIt, epsilon, delta);
			createFile(solution, fileName);
			
		}
		else if(useSec) {
			if(initP2 == null) {
				System.out.println("The Secant method requires two initial points");
				return;
			}
			Object[] solution = secant(f, Double.parseDouble(initP), Double.parseDouble(initP2), maxIt, epsilon);
			createFile(solution, fileName);
		}
		else if(useHybrid) {
			if(initP2 == null) {
				System.out.println("The Hybrid method makes use of bisection, so it needs two initial points");
				return;
			}
			Object[] solution = hybrid(f, f.evalDerivative(), Double.parseDouble(initP), Double.parseDouble(initP2), maxIt, epsilon, delta);
			createFile(solution, fileName);
		}

	}
	
	public static void createFile(Object[] solutionList, String name) throws IOException{
		int dot = name.lastIndexOf(".");
		if(dot > 0) name = name.substring(0, dot) + ".sol";
		PrintWriter writer = new PrintWriter(name);
		for(Object solution : solutionList) writer.print(solution + " ");
		writer.close();
	}
	
	public static Object[] bisection(Polynomial p, double a, double b, int maxIt, double epsilon) {
		double fa = p.evaluate(a);
		double fb = p.evaluate(b);
		double c = 0;
		
		if (fa * fb >= 0) {
			System.out.println("Inadequate values for a and b");
			return new Object[] {a, 0, "failure"};
		}
		
		double error = b - a;
		
		for(int it = 1; it < maxIt; it++) {
			error /= 2;
			c = a + error;
			double fc = p.evaluate(c);
			
			if(Math.abs(error) < epsilon || fc == 0) {
				System.out.println("Algorithm has converged after #" + it + " iterations!");
				return new Object[] {c, it, "success"};
			}
			if(fa * fc < 0) {
				b = c;
				fb = fc;
			}
			else {
				a = c;
				fa = fc;
			}
		}
		System.out.println("Max iterations reached without convergence...");
		return new Object[] {c, maxIt, "failure"};
	}
	
	public static Object[] newton(Polynomial p, Polynomial derivative, double x, int maxIt, double epsilon, double delta) {
		double fx = p.evaluate(x);
		for(int it = 1; it < maxIt; it++) {
			double fd = derivative.evaluate(x);
			
			if(Math.abs(fd) < delta) {
				System.out.println("Small Slope!");
				return new Object[] {x, it, "failure"};
			}
			
			double d = fx/fd;
			x -= d;
			fx = p.evaluate(x);
			
			if(Math.abs(d) < epsilon) {
				System.out.println("Algorithm has converged after #" + it + " iterations!");
				return new Object[] {x, it, "success"};
			}
		}
		System.out.println("Max iterations reached without convergence...");
		return new Object[] {x, maxIt, "failure"};
	}
	private static void swap(double a, double b) {
		double temp = a;
		a = b;
		b = temp;
	}
	public static Object[] secant(Polynomial p, double a, double b, int maxIt, double epsilon) {
		double fa = p.evaluate(a);
		double fb = p.evaluate(b);
		
		if(Math.abs(fa) > Math.abs(fb)) {
			swap(a, b);
			swap(fa, fb);
		}
		
		for(int it = 1; it < maxIt; it++) {
			if(Math.abs(fa) > Math.abs(fb)) {
				swap(a, b);
				swap(fa, fb);
			}
			double d = (b - a) / (fb - fa);
			b = a;
			fb = fa;
			d *= fa;
			
			if(Math.abs(d) < epsilon) {
				System.out.println("Algorithm has converged after #" + it + " iterations!");
				return new Object[] {a, it, "success"};
			}
			
			a -= d;
			fa = p.evaluate(a);
			
		}
		System.out.println("Maximum number of iterations reached!");
		return new Object[] {a, maxIt, "failure"};
	}
	public static Object[] hybrid(Polynomial p, Polynomial derivative, double a, double b, int maxIt, double epsilon, double delta) {
		Object[] results = bisection(p, a, b, 8, epsilon);
		double startingPoint = (double) results[0];
		return newton(p, derivative, startingPoint, maxIt, epsilon, delta);
	}

}
