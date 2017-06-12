/*
445089
Masahide Hasegawa
Random_draft.java
*/

import java.io.*;

public class Random_draft{

  public static double[][] random(int λ,int zigen){//案作成

    double MIN = -100.0;
    double MAX = 100.0;

    //int zigen = 10; // 10, 100 (, 30, 50)

    double[][] x = new double[λ][zigen];

    for(int a=0; a<λ; a++){
      for (int i=0; i<zigen; i++) {
        x[a][i] = MIN + (MAX - MIN) * Math.random();
        System.out.println(a + "番目の案 " + i + "個目" + "\t" + x[a][i]);
      }
    }

    return x;//n次元実数ベクトル

  }

}