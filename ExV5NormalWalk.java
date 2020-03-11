package hashContextTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ExV5NormalWalk {
	
	
	
	
	public static void main(String[] args){
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);

		
		ArrayList<Agent> agents = new ArrayList<Agent>();
		
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("agents_context_commonized.bin"));
			agents = (ArrayList<Agent> ) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("よみました");
		
		
		//test1.適当な周期で交換しながら記事を作成する　5回交換するごとに（それぞれ）一個生成するくらいの頻度にしたい
		//Onesimでは125体のノードがいて30秒に1回弱の頻度で発生する
		//これではもうしこし交換が多いことにする　25対いて30秒に一回おこる
		//(30秒に一回おこる程度の確率を毎秒ではなく、30秒に一回、強制的に発生させる)
		//今回はmap(agent間の遭遇確率)を考慮しない（するのも有り）
		
		//test2.流通を繰り返して、well-trainedなcontextができます
		//contextをわたせばだいたい同じattributeのグループといいjaccard値がでるはず
		//Q. いまのでも段階でもwell-trainedでは・・？
		//A.もうしこし上げたい。ただ、普通に記事が生成される環境でまわせるループをつくる(このモデルでまわせる普通のループ)
		//る必要あるのでその練習みたいなもんです
		
		//test3.あるタイミングでcontextを優先させて運ぼう
		
		//それではtest1
		for (int k = 0 ; k < 360000 ; k++ ) {
			
		  //遭遇です　30秒に一回どこかで起こる
		  if(k % 30 == 0) {
			  pair = util.ramdomMatch(Preference.agentNum);				
				util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]),k);
		  }
		  
		  //メッセージ生成です　すべてのノードが　30分に1回くらいつくる
		  if(k %  1800 == 0) {
			  for ( Agent a : agents) {
				  a.articleGenSimple(k);
				  a.makeExchangeListLayers();
			  }
			  System.out.print("x");
		  }
		  
		  
			
			
		}
		System.out.print("おわりです");
		
		//きょうはもう寝るがループ内の交換リストの挙動を確認
		util.saveAgents(agents, "walk360k.dat");
		
		agents.get(0).dumpEx();
		
		
	}
	
	
	
	

}
