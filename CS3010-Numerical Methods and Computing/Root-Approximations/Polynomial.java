package assignment2;

public class Polynomial {
    private double[] coefficients;

    public Polynomial(int degree, double[] coefficients) {
        this.coefficients = new double[degree + 1];
        for (int i = 0; i < coefficients.length; i++) {
            this.coefficients[i] = coefficients[i];
        }
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = coefficients.length - 1; i >= 0; i--) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public Polynomial evalDerivative() {
    	double[] dCoefficients = new double[coefficients.length - 1];
    	for(int i = coefficients.length - 1; i >=1; i--) {
    		dCoefficients[i - 1] = i * coefficients[i]; 
    	}
    	return new Polynomial(coefficients.length - 2, dCoefficients);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] == 0) {
                continue;
            }
            if (i == coefficients.length - 1) {
                sb.append(coefficients[i]).append("x^").append(i);
            } else if (i == 0) {
                sb.append(" + ").append(coefficients[i]);
            } else {
                sb.append(" + ").append(coefficients[i]).append("x^").append(i);
            }
        }
        return sb.toString();
    }
}