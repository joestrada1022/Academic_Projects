package assignment1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class gaussian {

	public static void main(String[] args) throws IOException{
		if(args.length == 0)
		{
			System.out.println("This program requires the '--spp' flag (optional) and a file name");			
		}
		else
		{
			Boolean spp = false;
			if(args[0].equals("--spp")) spp = true;
			File file = new File(args[args.length - 1]);
			Scanner scanner = new Scanner(file);
			int n = scanner.nextInt();
			double[][] coefficients = new double[n][n];
			double[] constants = new double[n];
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < n; j++)
				{
					coefficients[i][j] = scanner.nextDouble();
				}
			}
			for(int i = 0; i < n; i++)
			{
				constants[i] = scanner.nextDouble();
			}
			scanner.close();
			if(spp)
			{
				double[] solution = sppGaussian(coefficients, constants);
				createFile(solution, file.getName());
			}
			else 
			{
				double[] solution = naiveGaussian(coefficients, constants);
				createFile(solution, file.getName());
			}
		}
	}

	public static void createFile(double[] solution, String name) throws IOException
	{
		int dot = name.lastIndexOf(".");
		if(dot > 0) name = name.substring(0, dot) + ".sol";
		PrintWriter writer = new PrintWriter(name);
		for(int i = 0; i < solution.length; i++) 
		{
			writer.print(solution[i] + " ");
		}
		writer.close();
	}
	
	
	private static void sppFwdElim(double[][] coefficients, double[] constants, int[] index)
	{
		int n = constants.length;
		double[] scaling = new double[n];
		
		for(int i = 0; i < n; i++)
		{
			double sMax = 0;
			for(int j = 0; j < n; j++) 
			{
				sMax = Math.max(sMax, Math.abs(coefficients[i][j]));
			}
			scaling[i] = sMax;
		}
		
		for(int k = 0; k < n - 1; k++)
		{
			double rMax = 0;
			int maxInd = k;
			
			for(int i = k; i < n; i++)
			{
				double r = Math.abs(coefficients[index[i]][k] / scaling[index[i]]);
				if(r > rMax)
				{
					rMax = r;
					maxInd = i;
				}
			}
			swap(index, maxInd, k);
			
			for(int i = k + 1; i < n; i++)
			{
				double mult = coefficients[index[i]][k] / coefficients[index[k]][k];
				
				for(int j = k; j < n; j++)
				{
					coefficients[index[i]][j] -= mult * coefficients[index[k]][j];
				}
				
				constants[index[i]] -= mult * constants[index[k]];
			}
		}
		
	}
	
	private static void sppBackSub(double[][] coefficients, double[] constants, double[] solution, int[] index)
	{
		int n = constants.length;
		solution[n - 1] = constants[index[n - 1]] / coefficients[index[n - 1]][n - 1];
		for(int i = n - 2; i >= 0; i--)
		{
			double sum = constants[index[i]];
			for(int j = i + 1; j < n; j++) 
			{
				sum -= coefficients[index[i]][j] * solution[j];
			}
			solution[i] = sum / coefficients[index[i]][i];
		}
	}
	
	private static double[] sppGaussian(double[][] coefficients, double[] constants)
	{
		double[] solution = new double[constants.length];
		int[] index = new int[constants.length];
		
		for(int i = 0; i < constants.length; i++)
		{
			index[i] = i;
		}
		
		sppFwdElim(coefficients, constants, index);
		sppBackSub(coefficients, constants, solution, index);
		
		return solution;
	}
	
	private static void swap(int[] index, int maxInd, int k)
	{
		int temp = index[maxInd];
		index[maxInd] = index[k];
		index[k] = temp;
	}

	public static double[] naiveGaussian(double[][] coefficients, double[] constants) 
	{
		double[] solution = new double[constants.length];
		forwardElimination(coefficients, constants);
		backSubstitution(coefficients, constants, solution);
		return solution;
	}

	private static void forwardElimination(double[][] coefficients, double[] constants) 
	{
		int n = constants.length;
		for (int k = 0; k < n - 1; k++) 
		{
			for (int i = k + 1; i < n; i++) 
			{
				double mult = coefficients[i][k] / coefficients[k][k];
				for (int j = k; j < n; j++) 
				{
					coefficients[i][j] -= mult * coefficients[k][j];
				}
				constants[i] -= mult * constants[k];
			}
		}
	}

	private static void backSubstitution(double[][] coefficients, double[] constants, double[] solution) {
		int n = constants.length;
		solution[n - 1] = constants[n - 1] / coefficients[n - 1][n - 1];
		for (int i = n - 2; i >= 0; i--) {
			double sum = constants[i];
			for (int j = i + 1; j < n; j++) {
				sum -= coefficients[i][j] * solution[j];
			}
			solution[i] = sum / coefficients[i][i];
		}
	}
	
}