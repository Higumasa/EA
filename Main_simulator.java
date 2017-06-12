/*
445089
Masahide Hasegawa
Main_simulator.java
*/

import java.io.*;

public class Main_simulator{

  public static double[] simulator(int num,double x[][],int λ){//実数最適化を読み込みシミュレートする。

    Simulator simu = new Simulator();

    double f[] = new double[λ];

    for(int i=0;i<λ;i++){
      f[i] = simu.evaluate(num, x[i]);

      System.out.println("f(x) = \t"  + f[i]);
    }

    return f;//返り値関数

  }

}