/*
445089
Masahide Hasegawa
Selection.java
*/

import java.io.*;

public class Selection{

  public static int[] selection(double f[],int μ,int λ,int zigen,double x[][],int p){

    double min = 999999999.0;//最小値
    int count = 0;//何番目の隙間値が最小値に選ばれたか
    int data[] = new int [μ];//countの値をμ個分保存
    double bestf[] = new double [μ];
    double karif[] = new double [λ];//f[]が99999999に上書きされないように値を格納しておき淘汰に用いる
    //int ave,sum=0;//評価値の平均値

//    try{
/*
    //実行結果の出力先を作成する
      File file = new File("ESresult/ESresult_B.csv");
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));

*/
      /*
      File file2 = new File(".//result//averageES4result.csv");
      PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
      */

    for(int a=0;a<λ;a++){//f[]をkarif[]に淘汰のため格納
      karif[a] = f[a];
    }

    for(int b=0;b<μ;b++){//μ個分ランク付けをする
      for(int a = 0;a<λ;a++){
        if(min>karif[a]){//隙間値データの最小値を出す
          min = karif[a];
          count = a;
        }
        //System.out.println("f" + a + " " + f[a]);
      }
      //d = myu-μ;
      data[b] = count;
      //System.out.println("data" + d + " " + data[d]);
      bestf[b] = karif[count];//ランク付けのための配列に代入

      if(bestf[b]==0){
        int a=data[b];
        System.out.println("0!" + bestf[b]);
        p = p+1;//p=0なので0ループ目とならないように調整
        System.out.print("案は" + p + "ループ" + a + "個目の");
        for(int i=0;i<zigen;i++){
          System.out.print(x[a][i]);
        }
        System.exit(1);
      }

      karif[count] = 999999999;//選ばれた最小値を99999999にしてループ対象外にする。
      min = 999999999;
      System.out.println("bestf      " + count + " " + b + " " + bestf[b]);
    }

/*

    for(int b=0;b<μ;b++){
      sum+=bestf[b];
    }
    ave = sum/μ;

    pw.println(bestf[0] + "," + ave);


    //pw2.println(ave);

    pw.close();
    //pw2.close();

*/

/*
    }catch (IOException ex) {
      //例外時処理
      ex.printStackTrace();
    }
*/

    return data;

  }

    public static double[][] selection2(double f[],double f2[],int μ,int λ,int data[],int p,double bestf[]){

    double min = 999999999.0;//最小値
    int count = 0;//何番目の隙間値が最小値に選ばれたか
    int data2[] = new int [μ];//countの値をμ個分保存
    double totalanf[] = new double[μ+λ];//λ個の新案とその作成に使ったμ個の案（子と親）を入れた配列
    int a,j;
    double karitotalf[] = new double[μ+λ];//totalf[]が99999999に上書きされないように値を格納しておき淘汰に用いる

    double kaerichi[][] = new double [2][μ];

    //int ave,sum = 0;

/*
    //実行結果の出力先を作成する
      File file = new File("ESresult/ESresult_B.csv");
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
*/
      /*
      File file2 = new File(".//result//averageES4result.csv");
      PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(file2,true)));
      */

    if(p==0){//２ループ目の場合

      for(j=0,a=0;j<μ;j++){//totalanf[]にμ個の親案を格納
        a = data[j];
        totalanf[j] = f[a];
        //System.out.println("μ" + totalanf[j]);
      }

      for(a=0;j<λ+μ;j++,a++){//totalanf[]にλ個の子案を格納
        totalanf[j] = f2[a];
        //System.out.println("λ" + totalanf[j]);
      }

    }else if(p>0){//３ループ目以降の場合

      for(j=0;j<μ;j++){//totalanf[]にμ個の親案を格納
        totalanf[j] = bestf[j];
        //System.out.println("μ" + totalanf[j]);
      }

      for(a=0;j<λ+μ;j++,a++){//totalanf[]にλ個の子案を格納
        totalanf[j] = f2[a];
        //System.out.println("λ" + totalanf[j]);
      }

    }

    for(a=0;a<μ+λ;a++){//totalf[]をkaritotalf[]に淘汰のため格納
      karitotalf[a] = totalanf[a];
    }

    for(int b=0;b<μ;b++){//μ個分ランク付けをする
      for(a = 0;a<λ+μ;a++){
        if(min>karitotalf[a]){//隙間値データの最小値を出す
          min = karitotalf[a];
          count = a;
        }
      }
      data2[b] = count;
      //System.out.println("data" + d + " " + data[d]);
      bestf[b] = karitotalf[count];//ランク付けのための配列に代入
      karitotalf[count] = 999999999;//選ばれた最小値を99999999にしてループ対象外にする。
      min = 999999999;
      System.out.println("bestf      " + count + " " + b + " " + bestf[b]);
    }

    /*

    for(int b=0;b<μ;b++){
      sum+=bestf[b];
    }
    ave = sum/μ;

    pw.println(bestf[0] + "," + ave);


    //System.out.println("ave      " + ave);
    //pw2.println(ave);

    pw.close();
    //pw2.close();

    */

    for(j=0;j<μ;j++){
      kaerichi[0][j] = data2[j];
      kaerichi[1][j] = bestf[j];
    }

    return kaerichi;

  }

}