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

		String[] agentNames = {"Alice","Bob","Carol","Dave","Eve","Frank","Gennie","Hanna","Jack","Kim","Liam","Maria","Nate","Olivia","Patric","Quincy","Richard","Sally","Thomas","Ursula","Victor","Wendy","Xiao","Yang","Zora","John"};

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
		//Logging section will be separate.. 2020/06/03
	
		try{
			File file = new File(Preference.ContextCSVFileName);
			FileWriter filewriter = new FileWriter(file);
			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}

				
		for(int i = 0; i < 25 ; i++) {
			Article specialArticle = new Article("XX"+agents.get(i).getName(),agents.get(i).getName(),simtime,0,true);
			specialArticle.setTrap();
			agents.get(i).getContexts().get(0).setAttr(i);
			agents.get(i).getContexts().get(0).addHash(specialArticle.getHashID());
			agents.get(i).addSpecial(specialArticle);
			agents.get(i).makeExchangeListLayers();
		}
		
		for(Agent x :agents ) {x.dumpEx();	System.out.println("");}

		//ランダムでエージェントと遭遇
		//記事を交換
		//いいのがあったらmiddleについか
		//てきとうなタイミングで記事を作成
		//はじめはふたりで

		float limen = 0.2f;

		//ひとりが各エージェントにランダムで訪問
		//1ターンにつき各エージェントが記事一件作成＆交換リスト構成をリロード


		for (int i = 0 ; i < 100 ; i++) {
			simtime ++;

			for (Agent a : agents) {
				a.makeExchangeListLayers();
			}

			//System.out.println("Round" + i);
			//int match = Math.abs(rand.nextInt()) % (Preference.agentNum - 1) + 1; //1~24	
			//util.exChangeBidirectional(agents.get(0), agents.get(match),simtime);
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
		
		System.out.println("☆☆☆☆☆☆");
		for(Agent x :agents ) {x.dumpEx();	System.out.println("");}
		
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
