package hashContextTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ExV4 {

	public static void main(String[] args){

		System.out.println("Init.javaで生成されたAgent,mapを読み込み、Agent間で記事を交換\n　"
				+ "各AgentがもつContextに、共のhashを持たせるのが目的です");


		Random rand = new Random();
		int randomNum = rand.nextInt();
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);
		Misc misc = new Misc();



		//25人のエージェントを読みます
		ArrayList<Agent> agents = new ArrayList<Agent>();

		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("agents.bin"));
			agents = (ArrayList<Agent> ) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		//エージェント間の遭遇確率をよみこみます
		float[][] compatibility = new float[Preference.agentNum][Preference.agentNum];
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("map.bin"));
			compatibility = (float[][] ) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//とりあえず、一人のAgentに注目して動かすよ



		agents.get(0).showAttr();
		//agents.get(1).showAttr();

		int simulateTime = 0;
		float limen = 0.1f;	//これ、うごきをみて流動すべきものです

		Jaccard jacc = new Jaccard();

		//agents.get(0).dumpExMiddle();


		System.out.println("randome match はじまるよ");
		System.out.println("おわり");

		//System.exit(0);





		for (int k = 0 ; k < 1000 ; k++ ) {
			//記事を生成する
			//連番で記事をつくります

			for (int fav = 0 ; fav < Preference.favNum; fav++) {
				agents.get(k % Preference.agentNum).articleGenOwnContext(simulateTime,fav);
			}

			pair = util.ramdomMatch(Preference.agentNum);
			//まず、最初の1000るーぷは他のagentから不作為に記事をDLする
			//attributeに接続する記事を充実させる
			util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]));

			simulateTime ++;
		}

		//1000回のお見合いで488件の（同じコンテクストについて、agentどうしで共通するHashが追加されました）
		// 数字でみると488/50000
		//この記事では新しく生成されないので記事は500件ていど



		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(
							new FileOutputStream("a.bin"));
			objOutStream.writeObject(agents);
			objOutStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		System.out.println("エージェント保存しましま");











	}

}
