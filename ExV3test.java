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

public class ExV3test {

	public static void main(String[] args){

		System.out.println("hoge");

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
					new FileInputStream("lib/map.bin"));
			compatibility = (float[][] ) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}


		System.out.println("ExV2で作成された"
				+ "エージェントを任意のコマンドで操作します"
				+ "Usageとは書いてないですそのうち角");

		int agentCount = 0;
		for ( Agent a : agents) {
			System.out.print("["+agentCount+"] "+a.getName()+" ");
			a.showAttr();
			agentCount++;
		}		

		agents.get(0).getPotentialAttributes_X();
	
		Jaccard jacc = new Jaccard();
		for (int k = 0 ; k < 0 ; k++ ) {
			pair = util.ramdomMatch(Preference.agentNum);
			//まず、最初の1000るーぷは他のagentから不作為に記事をDLする
			//attributeに接続する記事を充実させる
			util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]),4649);
			//excEOはそんなにつかわなくてもいいけどなんとなく関数にまとめてしまった機能
			//片方向の交換を二度行う　それだけ
		}
		int simtime = 127;

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
