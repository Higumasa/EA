/*
445089
Masahide Hasegawa
New_draft.java
*/

import java.io.*;

public class New_draft{

  public static double[][] es_make_draft(int data[],int μ,int zigen,double x[][],int λ,int p,int es_ratio){//ループ二回目以降の新案作成

    int a=0;
    int i;
    double an[][] = new double[μ][zigen];//順番がバラバラだった淘汰された案を整理するためのもの
    double sinan[][] = new double[es_ratio][zigen];//新案
    int choicea = 0;//a 何番目の案をランダムで選び取ってくるかを表す
    int choicei = 0;//i 何番目の値をランダムで選び取ってくるかを表す
    int henkou = 3;//何個値を変更させるか

    for(int b=0;b<μ;b++){//淘汰された案のみのan[][]を作成
      //d = myu-μ;
      if(p==0){
        a = data[b];
      }else if(p>0){
        a = b;
      }
      //System.out.println("ここまでおけ" + b);
      for(i=0;i<zigen;i++){
        //System.out.println("best「r」 " + a + " " + d + " " + r[a][i]);
        an[b][i] = x[a][i];
        //System.out.println("best「an」 " + i + " " + d + " " + an[d][i]);
      }

    }

    for(int loop=0;loop<es_ratio;loop++){//μ個の案の中からランダムに一個選びsinan[][]にコピー、その案のビット数を何個かランダムで反転させる

      choicea = new java.util.Random().nextInt(μ);//0~μの乱数を代入
      //System.out.println("choicea" + " " + choicea);
      for(i=0;i<zigen;i++){//コピー
        //System.out.println("best「r」 " + a + " " + d + " " + r[a][i]);
        sinan[loop][i] = an[choicea][i];
        //System.out.println("best「an」 " + " " + d + " " + an[d][i]);
      }

      for(int loop2=0;loop2<henkou;loop2++){//実数を何個かランダムで変更させる
        choicei = new java.util.Random().nextInt(zigen);//0~zigenの乱数を代入
        double rand = Math.random()*2.0-1.0;//-1.0~1.0の乱数

        sinan[loop][choicei] += rand;
        if(sinan[loop][choicei] > 100.0){
          sinan[loop][choicei] -= rand*2;
        }
        if(sinan[loop][choicei] > -100.0){
          sinan[loop][choicei] += rand*2;
        }

      }
      /*for(i=0;i<zigen;i++){//結果表示
        System.out.println("sinan " + i + " " + loop + " " + sinan[loop][i]);
      }*/
    }

    return sinan;

  }




  public static double[][] ga_make_draft(int data[],int μ,int zigen,double x[][],int λ,int p,double min,double max,int ga_ratio){//ループ二回目以降の新案作成

    int a = 0;
    int i;
    double an[][] = new double[μ][zigen];//順番がバラバラだった淘汰された案を整理するためのもの
    double sinan[][] = new double[ga_ratio][zigen];//新案
    double sinan2[][] = new double[ga_ratio][zigen];//新案1
    double sinan3[][] = new double[ga_ratio][zigen];//新案2
    int choicea = 0;//a 何番目の案をランダムで選び取ってくるかを表す(1個目)
    int choicea2 = 0;//a 何番目の案をランダムで選び取ってくるかを表す(2個目)
    double l;//|xa-xb|
    double α = 0.5;//xcを区間[xb1-αL,xa1+αL]の範囲でランダムに決める時に使う
    double section_min = 0;
    double section_max = 0;//区間 [xb-αL,xa+αL]

    for(int b=0;b<μ;b++){//淘汰された案のみのan[][]を作成
      //d = myu-μ;
      if(p==0){
        a = data[b];
      }else if(p>0){
        a = b;
      }
      //System.out.println("ここまでおけ" + b);
      for(i=0;i<zigen;i++){
        //System.out.println("best「r」 " + a + " " + d + " " + r[a][i]);
        an[b][i] = x[a][i];
        //System.out.println("best「an」 " + i + " " + d + " " + an[d][i]);
      }

    }

    for(int loop=0;loop<ga_ratio;loop++){//μ個の案の中からランダムに二個選びsinan2[][]とsinan3[][]にコピー、その案でブレンド交叉を行う

      choicea = new java.util.Random().nextInt(μ);//0~μの乱数を代入
      choicea2 = new java.util.Random().nextInt(μ);//0~μの乱数を代入
      if(choicea == choicea2){//choiceaとchoicea2のダブりを防ぐ
        choicea2 = choicea2 + 1;
        if(choicea2 > μ-1){
          choicea2 = choicea2 - 2;
        }
      }

      System.out.println("choicea" + " " + choicea);
      System.out.println("choicea2" + " " + choicea2);
      for(i=0;i<zigen;i++){//コピー
        //System.out.println("best「r」 " + a + " " + d + " " + r[a][i]);
        sinan2[loop][i] = an[choicea][i];
        sinan3[loop][i] = an[choicea2][i];
        System.out.println("sinan2 " + sinan2[loop][i]);
        System.out.println("sinan3 " + sinan3[loop][i]);

      }

      for(i=0;i<zigen;i++){//ブレンド交叉

        l = Math.abs(sinan2[loop][i] - sinan3[loop][i]);

        if(sinan2[loop][i] < sinan3[loop][i]){
          section_min = sinan2[loop][i] - α * l;
          section_max = sinan3[loop][i] + α * l;
        }else if(sinan2[loop][i] >= sinan3[loop][i]){
          section_min = sinan3[loop][i] - α * l;
          section_max = sinan2[loop][i] + α * l;
        }

        System.out.println("l " + l);
        System.out.println("section_min " + section_min);
        System.out.println("section_max " + section_max);

        sinan[loop][i] = (Math.random()*(l+1))+section_min;

        if(sinan[loop][i] < min){
          sinan[loop][i] = min;
        }else if(sinan[loop][i] > max){
          sinan[loop][i] = max;
        }

      }

//突然変異1/zigen%
      for(i=0;i<zigen;i++){
        int totsuzenheni = new java.util.Random().nextInt(zigen);
        if(totsuzenheni==0){
          System.out.println(sinan[loop][i] + "が");

          sinan[loop][i] = min + (max - min) * Math.random();

          System.out.println(sinan[loop][i] + "に");
          System.out.println(loop + "個目の案の " + i + "ビット目が  入れ替わってる");

        }
        System.out.println("新案  " + sinan[loop][i]);
      }

    }

    return sinan;

  }

}