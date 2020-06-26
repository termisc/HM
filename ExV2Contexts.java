package hashContextTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class ExV2Contexts {

	public static void main(String[] args){

		System.out.println("hoge");

		Random rand = new Random();
		int randomNum = rand.nextInt();
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);
		Misc misc = new Misc();


		int simtime = 0;

		String[] agentNames = {"Alice","Bob","Carol","Dave","Eve","Frank","Gennie","Hanna","Jack","Kim","Liam","Olivia","Quincy","Pat","Richard","Sally","Thomas","Ursula","Victor","Wendy","Xiao","Yang","Zora","Maria","John"};


		if (false) {
			//Contextをよみこみます
			ArrayList<Context> contexts = new ArrayList<Context>();
			try {
				ObjectInputStream objInStream 
				= new ObjectInputStream(
						new FileInputStream("fuga.bin"));
				contexts = (ArrayList<Context> ) objInStream.readObject();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}


		//Context、空要素けします
		//25人のエージェントをつくります
		ArrayList<Agent> agents = new ArrayList<Agent>();
		for (int i = 0 ; i < Preference.agentNum ; i++) {
			simtime++;
			agents.add(new Agent());
			agents.get(i).setName(agentNames[i]);
			for (int j = 0; j < 30 ; j++) {
				agents.get(i).articleGenSimple(simtime);
				//これでランダムで雑な記事がたくさんできる
			}
			agents.get(i).makeaExchangeListSimple();
			agents.get(i).makeContextfromPotentialAttributes();	
			agents.get(i).showAttr();
		}
		simtime++;

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
		
		//contextのlogを初期化します。
		
			//よくつかうんので
		
		/*
		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(new FileOutputStream(hashLogFileName));
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
		*/
		
		//Logging section will be separate.. 2020/06/03
		String contextLogTxTFileName = Preference.ContextCSVFileName;
		try{
			File file = new File("contHyst.csv");
			FileWriter filewriter = new FileWriter(file);
			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}

		
		
		

		//とりあえず、一人のAgentに注目して動かすよ

		System.out.println("Agentのさいしょの記事をいじります！　主にこの記事の動向をみます　attr=0, articlename = GOTCHA");
		//Article(String _hashID,String _author,int _createdTime,int _attr,Boolean _isTrapped){
		Article specialArticle = new Article("XXXXXX",agents.get(0).getName(),simtime,0,true);
		agents.get(0).getContexts().get(0).setAttr(0);
		agents.get(0).getContexts().get(0).addHash(specialArticle.getHashID());
		agents.get(0).addSpecial(specialArticle);
		agents.get(0).makeExchangeListLayers();

		//ランダムでエージェントと遭遇
		//記事を交換
		//いいのがあったらmiddleについか
		//てきとうなタイミングで記事を作成
		//はじめはふたりで

		agents.get(0).showAttr();
		agents.get(1).showAttr();
		float limen = 0.2f;

		//ひとりが各エージェントにランダムで訪問
		//1ターンにつき各エージェントが記事一件作成＆交換リスト構成をリロード

		agents.get(0).dumpEx();

		for (int i = 0 ; i < 100 ; i++) {
			simtime ++;

			for (Agent a : agents) {
				a.articleGenFav(simtime);			
				a.makeExchangeListLayers();
			}

			//System.out.println("Round" + i);
			int match = Math.abs(rand.nextInt()) % (Preference.agentNum - 1) + 1; //1~24	
			util.exchengeEachOther(agents.get(0), agents.get(match),simtime);
		}

		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(
							new FileOutputStream("agents.bin"));
			objOutStream.writeObject(agents);
			objOutStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("エージェント保存したよ\n");
		System.out.println("simttime:"+ simtime);
		agents.get(0).dumpEx();
		
		while(true) {
			try{
				//入力ストリームの生成
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("入力してください   ⇒  ");
				String str = br.readLine();
				simtime = util.Commands(str,agents,simtime);
				simtime ++;
				
			}catch(IOException e){
				System.out.println("Exception :" + e);     
			}
		}

	}

}
