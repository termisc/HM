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
import java.util.List;
import java.util.Random;

public class ExV4 {

	public static void main(String[] args){

		System.out.println("Init.javaで生成されたAgent,mapを読み込み、Agent間で記事を交換\n　"
				+ "各AgentがもつContextに、共のhashを持たせるのが目的です");

		Random rand = new Random();
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);
		Misc misc = new Misc();		
		int simtime = 1000;

		//logfile initialise
		String contextLogTxTFileName = Preference.ContextCSVFileName;
		try{
			File file = new File(contextLogTxTFileName);
			FileWriter filewriter = new FileWriter(file);
			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}


		//25人のエージェントを読みます
		ArrayList<Agent> agents = new ArrayList<Agent>();
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("agents.bin"));
			agents = (ArrayList<Agent> ) objInStream.readObject();
			objInStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("基本的なcontextをつくります。contextを慣らす");
		//1simtime 1 meet
		int genCount = 0; 
		for (int k = 0 ; k < 1000 ; k++ ) {
			//1simtime 1 meet. 1 random match and 1 context match　が目安
			pair = util.ramdomMatch(Preference.agentNum);
			//ルール request する Agent がもつ　(ひとつの / すべての) Contextにつき 照会を行う
			Agent donner = agents.get(pair[0]);
			Agent recipient = agents.get(pair[1]);
			donner.download_T4(recipient,simtime);
			recipient.download_T4(donner,simtime);
			//200simtimeに一回、genを行う。genするたびgencount +1.mod エージェント数でエージェント輪番で記事を生成する。
			if (simtime % 200 == 0) {
				Agent a = agents.get(genCount % Preference.agentNum);
				for (int fav = 0 ; fav < Preference.favNum; fav++) {
					a.articleGenOwnContext(simtime,fav);
				}
				genCount ++;
			} 
			simtime ++;
		}
		System.out.println("\n慣らしおわり");

		//20200408
		//100回　exchange by context を行い、CSV出力　これを20回。　そのデータ
		for (int k = 0 ; k < 2000 ; k++ ) {
			System.out.print("");
			//1simtime 1 meet. 1 random match and 1 context match
			pair = util.ramdomMatch(Preference.agentNum);
			//ルール ・　request する Agent がもつ　(ひとつの / すべての) Contextにつき 照会を行う
			Agent donner = agents.get(pair[0]);
			Agent recipient = agents.get(pair[1]);
			for(Context c : recipient.getContexts()) {
				for(Article a:donner.serveContext(c,simtime)) {
					c.addCache(a);
					c.addHash(a.getHashID());
				}
			}
			//100simtimeに一回、genを行う。genするたびgencount +1.mod エージェント数でエージェント輪番で記事を生成する。]
			//これsimtimeへらしてもいいな。。。
			if (simtime % 100 == 0) {
				Agent a = agents.get(genCount % Preference.agentNum);
				for (int fav = 0 ; fav < Preference.favNum; fav++) {
					a.articleGenOwnContext(simtime,fav);
				}
				genCount ++;
			}

			if (simtime % 100 == 0) {
				util.makeCSV("",simtime,agents);
			}
			simtime ++;
		}

		System.out.println("\nsimやりました。csvを確認してください。さようなら");
		while(true) {
			try{
				//入力ストリームの生成
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Util.Commandsを参照してコマンド入れてくだ");
				System.out.print("入力してください   ⇒  ");
				//System.exit(1);
				String str = br.readLine();
				util.Commands(str,agents,simtime);
				simtime ++;
			}catch(IOException e){
				System.out.println("Exception :" + e);     
			}
		}
	}
}
