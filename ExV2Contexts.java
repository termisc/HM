package hashContextTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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


		int simulateTime = 0;

		String[] agentNames = {"alice","bob","carol","dave","eve","frank","gennie","hanna","jack","kim","liam","olivia","quincy","pat","richard","sally","thomas","ursula","victor","wendy","xiao","yang","zora","maria","john"};

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
		
		

		//Context、空要素けします

		//25人のエージェントをつくります
		ArrayList<Agent> agents = new ArrayList<Agent>();
		for (int i = 0 ; i < Preference.agentnum ; i++) {
			simulateTime++;
			agents.add(new Agent());
			agents.get(i).setName(agentNames[i]);
			for (int j = 0; j < 30 ; j++) {
				agents.get(i).articleGenSimple(simulateTime);
				//これでランダムで雑な記事がたくさんできる
			}
			agents.get(i).makeaExchangeListSimple();
			agents.get(i).makeContextfromPotentialAttributes();	
			agents.get(i).showAttr();
		}

		simulateTime++;




		//エージェント間の遭遇確率をよみこみます
		float[][] compatibility = new float[Preference.agentnum][Preference.agentnum];
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

		System.out.println("Agentのさいしょの記事をいじります！　主にこの記事の動向をみます　attr=0, articlename = GOTCHA");
		//Article(String _hashID,String _author,int _createdTime,int _attr,Boolean _isTrapped){
		Article specialArticle = new Article("You_have_Got_Me",agents.get(0).getName(),simulateTime,0,true);
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
			simulateTime ++;

			for (Agent a : agents) {
				a.articleGenFav(simulateTime);			
				a.makeExchangeListLayers();
			}

			//System.out.println("Round" + i);
			int match = Math.abs(rand.nextInt()) % (Preference.agentnum - 1) + 1; //1~24	
			util.exchengeEachOther(agents.get(0), agents.get(match));
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
		agents.get(0).dumpEx();

	}

}
