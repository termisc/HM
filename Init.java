package hashContextTest;


import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.ArrayList;

public class Init {
	//これテストなんで、なにもかも適当でいいです
	//シミュレーションに必要な要素をセーブして、agents.bin　と　map.binに保存
	//シミュレータのメインなループのテストはLoadedSimに以降します
	public static void main(String[] args){
		
		Random rand = new Random();
		int randomNum = rand.nextInt();
		Util util = new Util();		
	    int[] pair = util.ramdomMatch(2);
		Misc misc = new Misc();
		int simtime = 0;

		//25人のエージェントをつくります
		ArrayList<Agent> agents = new ArrayList<Agent>();
		for (int i = 0 ; i < Preference.agentNum ; i++) {
			agents.add(new Agent());
			agents.get(i).setName("no_"+String.valueOf(i));
			for (int j = 0; j < 300 ; j++) {
				agents.get(i).articleGenSimple(0);
			}
			agents.get(i).makeaExchangeListSimple();
			agents.get(i).makeContextfromPotentialAttributes();	
			agents.get(i).showAttr();
			simtime++;
		}
		
		//25*25の遭遇率の配列をつくります
		//25はPreference.agentnumから参照します
		//確率はdouble,floatあるがfloatでよい	
		//この配列は、各エージェント間の確率的な距離を表すMapとみてよい。
			
		float[][] compatibility = new float[Preference.agentNum][Preference.agentNum];
		for (int i = 0 ; i < Preference.agentNum ; i++) {
			for (int j = 0 ; j < Preference.agentNum ; j++) {
				compatibility[i][j] = rand.nextFloat();
			}
		}
		
		float limen;
		int heartCount = 0;
		
		Random rnd = new Random();
		limen = 0.2f;
		
		for (int k = 0 ; k < 100000 ; k++ ) {
			pair = util.ramdomMatch(Preference.agentNum);
			limen = rnd.nextFloat();
			if (compatibility[pair[0]][ pair[1]] > limen) {
				//まず、最初の1000るーぷは他のagentから不作為に記事をDLする
				//attributeに接続する記事を充実させる
				util.exchengeEachOther(agents.get(pair[1]), agents.get(pair[0]),simtime);
				heartCount ++;
			}
		}
		System.out.println(heartCount);
		
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
		
		try {
			ObjectOutputStream objOutStream = 
		             new ObjectOutputStream(
		             new FileOutputStream("map.bin"));
			objOutStream.writeObject(compatibility);
            objOutStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.print("おわりです");
	}
}

