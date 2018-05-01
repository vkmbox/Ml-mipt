package linreg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.function.BiFunction;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.LUDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.util.FastMath;

public class Main {
  
  public static final int ROWS = 200;
  
  public static void main( String ... args ) throws IOException {
    
    BiFunction<RealVector, RealVector, Double> mserror = (RealVector yy, RealVector pred) -> {
      RealVector subtract = yy.subtract(pred); //.getNorm();
      return subtract.dotProduct(subtract)/ROWS;
    };
    
    //Linear model: YY = FF*BB + ERR
    RealMatrix FF = new Array2DRowRealMatrix(ROWS, 4);
    RealMatrix YY = new Array2DRowRealMatrix(ROWS, 1);
    try ( BufferedReader bf = new BufferedReader ( new InputStreamReader 
        ( Main.class.getResourceAsStream("advertising.csv") )) ) {
      String line = bf.readLine(); int row = 0;
      while ( (line = bf.readLine()) != null ) {
        String[] split = line.split(",");
        double[] xx = {Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), 1.0};
        FF.setRow(row, xx);
        double[] yy = {Double.parseDouble(split[4])};
        YY.setRow(row++, yy);
      }
    }
    
    for ( int ii = 0; ii < 3; ii++ ) {
      double[] values = FF.getColumn(ii);
      double mean = StatUtils.mean(values);
      double var = FastMath.sqrt(StatUtils.populationVariance(values, mean));
      RealVector columnVector = FF.getColumnVector(ii).mapToSelf( ( double val ) -> { return (val - mean)/var; } );
      FF.setColumnVector(ii, columnVector);
    }
    
    double percentile = StatUtils.percentile(YY.getColumn(0), 50);
    double[] test1 = new double[ROWS];
    Arrays.fill(test1, percentile);
    System.out.println("1.txt:");
    System.out.println(String.format("%.3f", mserror.apply(YY.getColumnVector(0), new ArrayRealVector(test1))));
    
    //B = (FtF)-1FtY
    RealMatrix transpose = FF.transpose();
    RealMatrix multiply = transpose.multiply(FF);
    RealMatrix pInverse = new LUDecomposition(multiply).getSolver().getInverse();
    RealMatrix BB = pInverse.multiply(transpose).multiply(YY);
    System.out.println("2.txt:");
    System.out.println(String.format("%.3f", BB.getEntry(3, 0))); //.round(percentile));
    
    RealMatrix predict = FF.multiply(BB);
    System.out.println("3.txt:");
    System.out.println(String.format("%.3f", mserror.apply(YY.getColumnVector(0), predict.getColumnVector(0))));

    for ( int row = 0; row < 4; row++ )
      System.out.println(Arrays.toString( BB.getRow(row)) );
    
    //for ( int row = 0; row < ROWS; row++ )
      //System.out.println(Arrays.toString( predict.getRow(row)) + " - " + Arrays.toString(YY.getRow(row)));
    
    
    /*System.out.println("FF");
    for ( int row = 0; row < ROWS; row++ )
    System.out.println(Arrays.toString( FF.getRow(row)));*/
    /*System.out.println("YY");
    for ( int row = 0; row < ROWS; row++ )
    System.out.println(Arrays.toString( YY.getRow(row)));*/

  }
}