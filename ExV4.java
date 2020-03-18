package hashContextTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ExV4 {

	public static void main(String[] args){

		System.out.println("Init.javaで生成されたAgent,mapを読み込み、Agent間で記事を交換\n　"
				+ "各AgentがもつContextに、共のhashを持たせるのが目的です");


		Random rand = new Random();
		//int randomNum = rand.nextInt();
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);
		Misc misc = new Misc();
		
		int simtime = 10000;



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

		//int simulateTime = 0;
		float limen = 0.1f;	//これ、うごきをみて流動すべきものです

		Jaccard jacc = new Jaccard();

		//agents.get(0).dumpExMiddle();


		System.out.println("randome match はじまるよ");
		System.out.println("おわり");

		//System.exit(0);





		for (int k = 0 ; k < 0 ; k++ ) {
			//記事を生成する
			//輪番で記事をつくります

			for (int fav = 0 ; fav < Preference.favNum; fav++) {
				agents.get(k % Preference.agentNum).articleGenOwnContext(simtime,fav);
			}

			pair = util.ramdomMatch(Preference.agentNum);
			//まず、最初の1000るーぷは他のagentから不作為に記事をDLする
			//attributeに接続する記事を充実させる
			util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]),simtime);

			simtime ++;
		}

		//1000回のお見合いで488件の（同じコンテクストについて、agentどうしで共通するHashが追加されました）
		// 数字でみると488/50000
		//この記事では新しく生成されないので記事は500件ていど
		//20 simtimeにいちど　、Agentは輪番で記事を生成する　25x20、500simtimeにすべてのagentがひとつづつ記事を生成する 

		/*
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
		*/


		System.out.println("エージェント保存しましません");
		
		
		//1simtime 1 meet
		 int genCount = 0; 
		 for (int k = 0 ; k < 1000 ; k++ ) {
			 
			 System.out.print("");
			//1simtime 1 meet. 1 random match and 1 context match
			 pair = util.ramdomMatch(Preference.agentNum);
			 util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]),simtime);
			 //ルール ・　request する Agent がもつ　(ひとつの / すべての) Contextにつき 照会を行う
			 Agent donner = agents.get(pair[0]);
			 Agent recipient = agents.get(pair[1]);
			 
			 for(Context c : donner.getContexts()) {
				 	donner.exchangeBasedContext(c,simtime);
					//System.out.print( c.showHashes() + ", ");
					//System.out.print( c.getAttribute() + ", ");
					//System.out.println("□□□□□□□□" );
					//c.showHashes();
			}
			 			
			 //20simtimeに一回、genを行う。genするたびgencount +1.mod エージェント数でエージェント輪番で記事を生成する。
			 if (simtime % 20 == 0) {
				 Agent a = agents.get(genCount % Preference.agentNum);
				 System.out.println(simtime+ " Agent_"+ genCount % Preference.agentNum + " " + a.getName() + " Gen Article" );
				 for (int fav = 0 ; fav < Preference.favNum; fav++) {
					 a.articleGenOwnContext(simtime,fav);
				}
				 genCount ++;
			 } 
			 simtime ++;
		}
		
		
		
		
		while(true) {
			try{
				//入力ストリームの生成
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("入力してください   ⇒  ");
				String str = br.readLine();
				util.Commands(str,agents,simtime);
				simtime ++;
			}catch(IOException e){
				System.out.println("Exception :" + e);     
			}
		}











	}

}
