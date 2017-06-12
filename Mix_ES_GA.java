/*
445089
Masahide Hasegawa
Mix_ES_GA.java
*/

import java.io.*;

public class Mix_ES_GA{

  public static void main(String[] args) throws Exception {

    int λ = 100;//案の数
    int μ = 20;//淘汰する数
    int p = 0;//世代用カウンタ変数
    int sedai = 100;//世代数
    double bestf[] = new double [μ];
    int zigen = 10; // 10, 100 (, 30, 50)
    int num = 15; // 1, 2, 3, ..., 30
    double min = -100.0;
    double max = 100.0;

/*
    int hako = hakoread();//hakoreadメソッド呼び出し 戻り値箱の大きさ
    int zigen = zigenread();//zigenreadメソッド呼び出し 戻り値荷物の個数
*/
    double x[][] = Random_draft.random(λ,zigen);
    double  f[] = Main_simulator.simulator(num,x,λ);//simulatorメソッド呼び出し 戻り値隙間値
    int data[] = Selection.selection(f,μ,λ,zigen,x,p);//１ループ目の淘汰 戻り値 案の何番目の隙間値が最小値に選ばれたか


    double totalan[][] = new double[μ+λ][zigen];//３ループ目以降μ+λ個の案を格納しておく配列
    double karitotalan[][] = new double[μ+λ][zigen];//３ループ目以降の親の案を格納しておく配列
    int a,j;//for用

//初期アルゴリズム割合(最初のランダムの案で一世代目は使って居るのでうまくes0%ga100%はできない)
    int es_ratio = 1;
    int ga_ratio = λ-1;
    double sinan[][] = new double[λ][zigen];//mix案

    //try{

/*       //実行結果の出力先を作成する
      File file = new File(".//result//ESresult.csv");
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));*/

      for(; p<sedai-1; p++,es_ratio++,ga_ratio--){

        double es_sinan[][] = New_draft.es_make_draft(data,μ,zigen,x,λ,p,es_ratio);//ES新案作成
        double ga_sinan[][] = New_draft.ga_make_draft(data,μ,zigen,x,λ,p,min,max,ga_ratio);//GA新案作成

        for(int mix_loop = 0; mix_loop < λ ;){

          for(; mix_loop < es_ratio;mix_loop++){
            System.out.println("ESで新案作成  " + mix_loop);
            for(int z = 0; z < zigen; z++){
              sinan[mix_loop][z] = es_sinan[mix_loop][z];
            }
          }
          for(int k = 0; mix_loop < λ; mix_loop++,k++){
            System.out.println("GAで新案作成  " + mix_loop);
            for(int z = 0; z < zigen; z++){
              sinan[mix_loop][z] = ga_sinan[k][z];
            }
          }

        }

        double f2[] = Main_simulator.simulator(num,sinan,λ);//simulator2メソッド呼び出し 戻り値隙間値
        double kaerichi[][] = Selection.selection2(f,f2,μ,λ,data,p,bestf);//２ループ目以降の淘汰 戻り値 ①案の何番目の隙間値が最小値に選ばれたか ②淘汰された隙間値


  /////////
        if(p==0){//２ループ目のλ+μ個の案集め


          for(j=0;j<μ;j++){
            a = data[j];
            //System.out.println("a" + a);
            for(int i=0;i<zigen;i++){
              totalan[j][i] = x[a][i];
            }
          }

          for(a=0;j<λ+μ;j++,a++){
            for(int i=0;i<zigen;i++){
              totalan[j][i] = sinan[a][i];
            }
          }


        }else if(p>0){//３ループ目以降のλ+μ個の案集め


          for(j=0;j<μ;j++){
            for(int i=0;i<zigen;i++){
              karitotalan[j][i] = x[j][i];
            }
          }

          for(j=0;j<μ;j++){
            //System.out.println("j" + j);
            for(int i=0;i<zigen;i++){
              totalan[j][i] = karitotalan[j][i];
            }
          }

          for(a=0;j<λ+μ;j++,a++){
            for(int i=0;i<zigen;i++){
              totalan[j][i] = sinan[a][i];
            }
          }


        }


  //この二つのforはkaerichi[][]をdata[]とbestf[]に分けるためのfor文
        for(j=0;j<μ;j++){
          data[j] = (int)kaerichi[0][j];
          //System.out.println("dataの" + data[j]);
        }
        for(j=0;j<μ;j++){
          bestf[j] = kaerichi[1][j];
          //System.out.println("bestの" + bestf[j]);

          if(bestf[j]==0){//隙間値が0になった場合、何ループ何個目のどんなビット列だったかを出力する。
            a=data[j];
            System.out.println("0!" + bestf[j]);
            p=p+1;
            //pw.println(p);//csvファイルに出力
            System.out.print("案は" + p + "ループ" + a + "個目の");
            for(int i=0;i<zigen;i++){
              //pw.print(totalan[a][i]);//csvファイルに出力
              System.out.print(totalan[a][i]);
            }
            //pw.println("\n");
            System.out.print("\n");
            //pw.close();
            System.exit(1);
          }

        }
  //

        for(int b=0;b<μ;b++){//淘汰された案を親の案にする
          a=data[b];
          for(int c=0;c<zigen;c++){
            x[b][c] = totalan[a][c];
          }
        }
  //////////

      }


      if(bestf[μ-1]>0){
        //pw.println("失敗!最小の隙間値は" + bestf[0]);
        //pw.println("\n");
        p++;
        System.out.println("問題" + num + "番" + p + "回ループ終了!最小の評価値は  " + bestf[0]);
        //pw.close();
      }

      System.out.println("出力完了");

    /*}catch (IOException ex) {
      //例外時処理
      ex.printStackTrace();
    }*/

  }

}