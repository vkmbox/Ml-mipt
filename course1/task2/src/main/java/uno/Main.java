package uno;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.DecompositionSolver;
import org.apache.commons.math4.linear.LUDecomposition;

public class Main{
  
  public static double[] getRow( double aa) {
    return new double[]{ Math.pow(aa, 0), Math.pow(aa, 1), Math.pow(aa, 2), Math.pow(aa, 3)};
    /*return new ArrayRealVector( 
      new double[]{ Math.pow(aa, 0), Math.pow(aa, 1), Math.pow(aa, 2), Math.pow(aa, 3)}, false 
    );*/
  }
  
  public static void main(String ... args) throws IOException {
    UnivariateFunction fnc = (double x) -> Math.sin(x / 5.0) * Math.exp(x / 10.0) + 5.0 * Math.exp(-x / 2.0);
    
    //double[][] data = { {1,1,1,1}, {2d,5d}, {1d, 7d}};
    RealMatrix coefficients = new Array2DRowRealMatrix( 4, 4 );
    coefficients.setRow(0, getRow(1));
    coefficients.setRow(1, getRow(4));
    coefficients.setRow(2, getRow(10));
    coefficients.setRow(3, getRow(15));
    
    RealVector constants = new ArrayRealVector
      (new double[] { fnc.value(1), fnc.value(4), fnc.value(10), fnc.value(15) }, false);
    
    /*for ( int row = 0; row < 4; row++ )
      System.out.println(Arrays.toString( coefficients.getRow(row)));
    
    System.out.println(Arrays.toString( constants.toArray()));*/
    
    DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
    RealVector solution = solver.solve(constants);
    System.out.println(String.format("w_0=%.2f, w_1=%.2f, w_2=%.2f, w_3=%.2f"
      , solution.getEntry(0), solution.getEntry(1), solution.getEntry(2), solution.getEntry(3)));
    
  }
}