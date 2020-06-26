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


public class ExV5Hystory {
	
	//Agentsを読み込み、そのAgentsが持っているcontextをもとに、かく記事のコピー経路を追跡します。
	//　1.入力された記事のIDをもとに全ログから登場実績をチェックする
	// 2. そのhashごとの登場履歴を出力する
	//　いくつかのHashについて追跡を行い、Hashごとの伝達効率を評価する
	
	public static void main(String[] args){
		System.out.println("Loading agents");
		
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
		
		
		
	}
	
	
	

}
