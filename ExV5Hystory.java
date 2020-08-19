package hashContextTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
	
	//読むファイル変えたくなったらpreferencesでファイル名かえてね
	//ContextCSVFileNameを読む
	//ContextCSVのうち、trackListに符号するものを出力。articleTraceLogに保存。
	//メモ：ログにに出てるのはHashでcacheではない。
	
	private static ArrayList<String> SearchingArticleList;

	public static void main(String[] args){
		
		String contextLogFileName = Preference.ContextCSVFileName;
		String trackListFileName = Preference.trackListFileName;
		String outputLogFileName = Preference.articleTraceLogFileName;
		
		contextLogFileName = Preference.ContextCSVFileName;
		trackListFileName = Preference.trackListFileName;
		
		//引数にログ・ファイルなければデフォルト値を使用
		System.out.println("***args***");
		if(args.length == 2) {
		  contextLogFileName = args[0];
		  trackListFileName = args[1];
		}else if(args.length == 1)   {
			contextLogFileName = args[0];
		}	
		System.out.println("log : "+contextLogFileName);
		System.out.println("tracklist : "+trackListFileName);
		//String mainLog;
		StringBuilder mainLogBuf = new StringBuilder();
	
		//記事を追跡したLogファイルを設定する.ファイルをつくります (articleTraceLog.csv)
		try{
			File file = new File(outputLogFileName);
			FileWriter filewriter = new FileWriter(file);
			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}
		
		
		//検索リストのファイルを開く、ファイルを読む、一行ずつArrayListに入れる
		System.out.println("Articles to be tracked\n---");
		SearchingArticleList =  new ArrayList<String>();
		try{
			File file = new File(Preference.trackListFileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			while((str = br.readLine()) != null){
				SearchingArticleList.add(str);
				System.out.println(str);
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		System.out.println("---");

		
		//ログを一行ずつ読む。目当(検索リスト)ての文字列があったらログ出力
		try{
			File file = new File(Preference.ContextCSVFileName);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str;
			while((str = br.readLine()) != null){
				String[] factors = str.split(",");
				for(String f : factors) {
					for (String h : SearchingArticleList) {
						if (f.equals(h)) {
							System.out.println(factors[0]+","+factors[1]+","+h);
							mainLogBuf.append(factors[0] + "," + factors[1] + "," + h +"\n"); 
						}
					}
				}			    
			}
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		System.out.println("---");
	}
}
