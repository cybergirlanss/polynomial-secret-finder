import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PolynomialSecretFinder {
    
    public static void main(String[] args) {
        try {
            // Test Case 1 - Manual input (since JSON parsing is complex without library)
            System.out.println("=== TEST CASE 1 ===");
            BigInteger secret1 = solveTestcase1();
            System.out.println("Secret c for Test Case 1: " + secret1);
            
            // Test Case 2 - Manual input
            System.out.println("\n=== TEST CASE 2 ===");
            BigInteger secret2 = solveTestcase2();
            System.out.println("Secret c for Test Case 2: " + secret2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static BigInteger solveTestcase1() {
        List<BigInteger> xValues = new ArrayList<>();
        List<BigInteger> yValues = new ArrayList<>();
        
        // Add points from Test Case 1 manually
        xValues.add(BigInteger.ONE);                      // x=1
        yValues.add(new BigInteger("4"));                 // y=4 (base 10)
        
        xValues.add(BigInteger.valueOf(2));               // x=2
        yValues.add(new BigInteger("111", 2));            // y=7 (base 2: 111 = 7)
        
        xValues.add(BigInteger.valueOf(3));               // x=3
        yValues.add(new BigInteger("12"));                // y=12 (base 10)
        
        xValues.add(BigInteger.valueOf(6));               // x=6
        yValues.add(new BigInteger("213", 4));            // y=39 (base 4: 213 = 39)
        
        // Print the points
        System.out.println("Points for Test Case 1:");
        for (int i = 0; i < xValues.size(); i++) {
            System.out.println("(" + xValues.get(i) + ", " + yValues.get(i) + ")");
        }
        
        return lagrangeInterpolation(xValues, yValues, 3); // k=3
    }
    
    private static BigInteger solveTestcase2() {
        List<BigInteger> xValues = new ArrayList<>();
        List<BigInteger> yValues = new ArrayList<>();
        
        // Add points from Test Case 2 manually (first 7 points for k=7)
        xValues.add(BigInteger.ONE);
        yValues.add(new BigInteger("13444211440455345511", 6));
        
        xValues.add(BigInteger.valueOf(2));
        yValues.add(new BigInteger("aed7015a346d63", 15));
        
        xValues.add(BigInteger.valueOf(3));
        yValues.add(new BigInteger("6aeeb69631c227c", 15));
        
        xValues.add(BigInteger.valueOf(4));
        yValues.add(new BigInteger("e1b5e05623d881f", 16));
        
        xValues.add(BigInteger.valueOf(5));
        yValues.add(new BigInteger("316034514573652620673", 8));
        
        xValues.add(BigInteger.valueOf(6));
        yValues.add(new BigInteger("2122212201122002221120200210011020220200", 3));
        
        xValues.add(BigInteger.valueOf(7));
        yValues.add(new BigInteger("20120221122211000100210021102001201112121", 3));
        
        System.out.println("Using first 7 points from Test Case 2 for interpolation");
        return lagrangeInterpolation(xValues, yValues, 7); // k=7
    }
    
    private static BigInteger lagrangeInterpolation(List<BigInteger> xValues, 
                                                   List<BigInteger> yValues, 
                                                   int k) {
        BigInteger result = BigInteger.ZERO;
        
        for (int i = 0; i < k; i++) {
            BigInteger numerator = yValues.get(i);
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    numerator = numerator.multiply(xValues.get(j).negate());
                    BigInteger diff = xValues.get(i).subtract(xValues.get(j));
                    denominator = denominator.multiply(diff);
                }
            }
            
            BigInteger term = numerator.divide(denominator);
            result = result.add(term);
        }
        
        return result;
    }
}