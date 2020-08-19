package hashContextTest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
//Utilみたいなクラスがふえてきたらパッケージわけます
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

//ログ取りに必要になるクラスなど
public class Util {



	int Commands(String s, ArrayList<Agent> agents,int simtime) {

		String[] args = s.split(" ");

		//System.out.println("cmd：" + args[0]);
		switch (args[0]) {
		case "dump" :
			System.out.println("dump");
			break;

		case "guide" :
			System.out.println("guide");
			guide(s,agents);
			break;

		case "save" :
			System.out.println("save");
			//saveAgents(agents,"hoge.bin");
			save(agents,s);
			break;

		case "meet" :
			session(s,agents,simtime);
			break;

		case "exlist" :
			//交換リストを表示します
			showExList(s,agents);
			break;

		case "context" :
			System.out.println("✨");
			showContexts(s,agents);
			//数字でエージェントごとの
			break;

		case "article" :
			System.out.println("alist✨");
			//数字でエージェントごとの
			break;

		case "tr" :
			System.out.println("記事の手動交換.エージェントに設定された[嗜好]の値を使用。");
			simtime = train(s,simtime,agents);
			break;

		case "tr2" :
			System.out.println("記事のコンテクスト(類似性を表現するIDの集合)をもとにした自動交換");
			simtime = tr2(s,simtime,agents);
			break;

		case "tr21" :
			System.out.println("記事のコンテクスト(類似性を表現するIDの集合)をもとにした自動交換");
			System.out.println("一定割合で記事の生成(gen)を行います。");
			System.out.println("Usage: tr <交換ごとの生成回数>");
			simtime = tr21(s,simtime,agents);
			break;
			
		case "genall" :
			System.out.println("gen article from context");
			 genArticleAllAgentsFromAllContext(s,simtime,agents);
			break;

		case "gen" :
			System.out.println("gen article from context");
			genArticleFromAllContext(s,simtime,agents);
			break;

		case "jacc" :
			System.out.println("比較 context");
			jaccardContext(s,simtime,agents);
			break;

		case "msc" :
			System.out.println("exchange by Context");
			sessionContext(s,agents,simtime);
			break;

		case "csv" :
			System.out.println("make CSV");
			makeCSV(s,simtime,agents);
			break;

		case "cache" :
			showCaches(s,agents);
			break;

		case "exit" :
			System.out.println("Have a nice day.");
			System.exit(0); //ほんとはここに書かず、引数で判断して呼び出し側で閉じるものだよ
			break;

		}

		return simtime;
	}

	void showCaches(String s, ArrayList<Agent> agents) {

		String[] args = s.split(" "); 
		args = Arrays.copyOfRange(args, 1, args.length);
		int agentnum = -1;
		agentnum = Integer.parseInt(args[0]);
		List<Context> contexts = agents.get(agentnum).getContexts();			
		for(Context c : contexts) {
			System.out.println(c.caches.size());
			c.showCaches();
		}		
	}

	void showArticleList(String s, ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		String[] args = s.split(" "); 
		args = Arrays.copyOfRange(args, 1, args.length);
		int agentnum = -1;
		agentnum = Integer.parseInt(args[0]);
		agents.get(agentnum).showArticle();
	}

	void guide(String s, ArrayList<Agent> agents) {	
		int agentCount = 0;
		for ( Agent a : agents) {
			System.out.print("["+agentCount+"] "+a.getName()+" ");
			a.showAttr();
			agentCount++;
		}		
	}

	void showExList(String s, ArrayList<Agent> agents) {
		String[] args = s.split(" "); 
		args = Arrays.copyOfRange(args, 1, args.length);
		System.out.println(args.length);

		int agentnum = -1;
		try{
			agentnum = Integer.parseInt(args[0]);
		}catch(IndexOutOfBoundsException exception) {
			System.out.println("IndexOutOfBoundsException");
		}	

		try{
			agents.get(agentnum).dumpEx();
		}catch(IndexOutOfBoundsException exception) {
			System.out.println("IndexOutOfBoundsException");
		}		
	}

	//これはAgentの中のクラスにしたほうがいい
	List<Article> deduplication(List<Article> articleList) {
		for(int i = 0;i < articleList.size();i++) {
			for(int j = 0;j < articleList.size();j++) {
				if (i != j && articleList.get(i).getHashID().equals(articleList.get(j).getHashID() )) {
					articleList.remove(j);
				}
			}
		}
		return articleList;
	}

	//これはAgentの中のクラスにしたほうがいい
	void deduplicationContext(Context _context) {
		LinkedList<String> hashes = _context.getHashes();
		for(int i = 0; i < hashes.size(); i++) {
			for(int j = 0; j < hashes.size(); j++) {
				if ( i != j && hashes.get(i).equals(hashes.get(j)) ){
					hashes.remove(j);
				}
			}
			_context.setHashes(hashes);
		}
	}

	int[] ramdomMatch(int maxnum) {
		int[] couple = new int[2]; 
		Random rand = new Random();
		couple[0] = Math.abs(rand.nextInt() % maxnum);
		couple[1] = Math.abs(rand.nextInt() % maxnum);
		while (couple[0] == couple[1] ) {
			couple[1] = Math.abs(rand.nextInt()% maxnum);
		}
		return couple;
	}

	static void saveAgents(ArrayList<Agent> agents, String filename) {
		//よくつかうんので
		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(new FileOutputStream(filename));
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
	}
	
	static void saveArticles(ArrayList<Article> articles, String filename) {
		//よくつかうんので
		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(new FileOutputStream(filename));
			objOutStream.writeObject(articles);
			objOutStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("エージェント保存しましま");
	}

	void saveCommonContexts(ArrayList<Context> contexts, String filename) {
		//よくつかうんので
		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(
							new FileOutputStream(filename));
			objOutStream.writeObject(contexts);
			objOutStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Contexts保存しましま");
	}

	void save(ArrayList<Agent> agents, String s) {
		//よくつかうんので
		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		Pattern pattern_opt = Pattern.compile("-.*");
		for (String arg :args) {
			Matcher matcher_opt = pattern_opt.matcher(arg);
			if (matcher_opt.matches() ) {
				switch (arg) {
				case "-a" :
					System.out.println("えーじぇんと");
					System.out.println("save agents");
					LocalDateTime d = LocalDateTime.now();
					DateTimeFormatter df1 = DateTimeFormatter.ofPattern("MMddHHmss");
					String ds = df1.format(d); 
					ds = ds + ".bin";
					System.out.println(ds);
					saveAgents(agents,ds);
					saveAgents(agents,"hoge.bin");			
					break;
				case "-c" :
					ArrayList<Context> contexts = new ArrayList<Context>();
					for(int i=0 ; i< 256 ;i++) {
						contexts.add(new Context(i));
					}
					for(Agent a : agents ) {
						for(Context co : a.getContexts()) {
							int attr = co.getAttribute();
							LinkedList<String> h1 = contexts.get(co.getAttribute()).getHashes();
							LinkedList<String> h2 = co.getHashes();
							h1.addAll(h2);
							contexts.get(attr).setHashes(h1);					
						}
					}
					int i = 0;
					for(Context c : contexts) {
						System.out.println(i);;
						i++;
						c.showHashes();
					}
					saveCommonContexts(contexts,"fuga.bin");	
					System.out.println("こんてくすと");
					System.out.println("save contexts");
					//attrの数だけcontect(save)を作成
					//agentのcontestを読む
					//add context from agents to context(save) what has same attr.
					break;
				}
				System.out.println("OK");
			}else {
				System.out.println("error");
			}

		}




	}

	void showContexts(String s, ArrayList<Agent> agents) {
		String[] args = s.split(" "); 
		args = Arrays.copyOfRange(args, 1, args.length);
		int agent = -1;
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		Pattern pattern_opt = Pattern.compile("-.*");

		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				agent = Integer.parseInt(arg);
				try{
					agents.get(agent).showHashes();		
				}catch(IndexOutOfBoundsException exception) {
					//handleTheExceptionSomehow(exception);
					System.out.println("Error: Index Out Of Bounds. Skip");
				}				
				System.out.println("OK");
			}else {
				System.out.println("error");
			}	
			Matcher matcher_opt = pattern_opt.matcher(arg);
			if (matcher_opt.matches() ) {
				switch (arg) {
				case "-a" :
					System.out.println("show all contexts");
					for ( Agent a : agents) {
						System.out.println("★★★★★"+a.getName());
						a.showHashes();
					}
					break;
				}
				System.out.println("OK");
			}else {
				System.out.println("error");
			}
		}
	}


	void makeCSV(String s,int simtime,ArrayList<Agent> agents) {		
		String csv = "";		
		for ( Agent a : agents) {

			List<Context> contexts = a.getContexts();
			for(Context c : contexts) {
				csv += simtime+","+a.getName()+","+c.getAttribute().toString()+",";
				csv += c.getHashesForLog();
				csv+="\n";
			}

		}
		csv+="\n";
		//System.out.println(csv);
		File file = new File(Preference.ContextCSVFileName);
		try {
			FileWriter filewriter = new FileWriter(file, true);
			filewriter.write(csv);
			filewriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	int train (String s,int simtime,ArrayList<Agent> agents) {
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				int goal = Integer.parseInt(arg);
				for(int i = 0 ; i < goal ; i++) {
					int[] couple = ramdomMatch(25);
					agents.get(couple[0]).download_T4(agents.get(couple[1]),simtime);
					agents.get(couple[1]).download_T4(agents.get(couple[0]),simtime);
					simtime ++;
				}
			}
		}
		return simtime;
	}

	int tr2 (String s,int simtime,ArrayList<Agent> agents) {
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				int goal = Integer.parseInt(arg);
				for(int i = 0 ; i < goal ; i++) {
					int[] couple = ramdomMatch(25);
					agents.get(couple[0]).download_T4(agents.get(couple[1]),simtime);
					agents.get(couple[1]).download_T4(agents.get(couple[0]),simtime);
					simtime ++;
				}
				int[] couple = ramdomMatch(25);
				agents.get(couple[0]).download_T4(agents.get(couple[1]),simtime);
				agents.get(couple[1]).download_T4(agents.get(couple[0]),simtime);
				System.out.println("OK");
				simtime ++;
			}else {
				System.out.println("Error");
			}
		}
		return simtime;
	}

	int tr21 (String s,int simtime,ArrayList<Agent> agents) {
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		//引数1 試行回数
		//引数2 生成回数
		int trainTimes;
		int genFrequency;
		//厳密なvalidationしません　いそがしいから
		if(args.length != 3) {
			System.out.println("invalid argments");
			return simtime;
		}
		try {
			trainTimes = Integer.parseInt(args[1]);
			genFrequency = Integer.parseInt(args[2]);
		} catch(Exception ex) {
			System.out.println("invalid argments");   
			return simtime;
		}
		System.out.println("trainTimes : " + trainTimes);
		System.out.println("genFrequency : " + genFrequency);
		int[] couple;
		for(int i = 0 ; i < trainTimes; i++) {
			System.out.print(".");
			couple = ramdomMatch(25);
			for (int contextNum = 0;contextNum < Preference.favNum;contextNum++) {
				try {
					Agent recipient = agents.get(couple[0]);
					Agent donner = agents.get(couple[1]);
					Context c = donner.getContexts().get(contextNum);
					recipient.giveArticlefromContext(c,simtime);
				}
				catch(IndexOutOfBoundsException exception) {
					System.out.println("indexerror");
				}
			}
			simtime++;
		}
		return simtime;
	}



	int sessionContext(String s,ArrayList<Agent> agents ,int simtime) {
		//(1)要求エの番号
		//(2)要求エが渡すコンテクストの番号
		//(3)供給エの番号
		//あたえられた要求エのコンテクストに対して、もっとも近い供給エのコンテクストに属する記事を渡す
		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		//validationしません　いそがしいから
		int agent1 = -1;
		int agent2 = -1;
		int contextNum = -1;
		System.out.println(args.length);
		if ( args.length != 3) {
			System.out.println("parse error, usage : smc int(Agent request) int(Agent reply) int(context) ");
			return 1;
		}
		agent1 = Integer.parseInt(args[0]);
		agent2 = Integer.parseInt(args[2]);
		contextNum = Integer.parseInt(args[1]);
		try {
			Agent recipient = agents.get(agent1);
			Agent donner = agents.get(agent2);
			Context c = recipient.getContexts().get(contextNum);
			donner.giveArticlefromContext(c,simtime);
		}
		catch(IndexOutOfBoundsException exception) {
			//handleTheExceptionSomehow(exception);
			System.out.println("indexerror");
		}
		return 0;		
	}

	void session (String s, ArrayList<Agent> agents,int simtime) {
		// どうにかして、対象1、対象2、オプション、を分けます
		//sex [options] object object . optionがあとにくることはないということにする
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		args = Arrays.copyOfRange(args, 1, args.length); //sexだけ消しました
		int agent1 = -1;
		int agent2 = -1;
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		Pattern pattern_opt = Pattern.compile("-.*");
		Boolean obj1_flag = false; //これが入ったあとでオプションがきたらエラー
		Boolean obj2_flag = false; //これが入ったあとは何がきてもエラー
		Boolean opt_flag = false; //これが入ったあとで
		Boolean error_flag = false; 
		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				if (obj2_flag == true ) {
					error_flag = true;
					System.out.println("error");
				}else if ( obj1_flag == true ){
					agent2 = Integer.parseInt(arg);
					obj2_flag = true;
				}else {
					agent1 = Integer.parseInt(arg);
					obj1_flag = true;
				}
			}
			Matcher matcher_opt = pattern_opt.matcher(arg);
			if (matcher_opt.matches() ) {
				if(obj1_flag == true) {
					error_flag = true; 
					continue;
				}			
				switch (arg) {
				case "-r" :
					System.out.println("らんだむ");
					int[] couple = ramdomMatch(25);//25は、どっかに定数あったのでそこから参照する
					agent1 = couple[0];
					agent2 = couple[1];
					obj1_flag = true;
					obj2_flag = true;
					break;
				}
			}
		}
		if(error_flag == false && obj2_flag == true) {
			agents.get(agent1).download_T4(agents.get(agent2),simtime);
			agents.get(agent2).download_T4(agents.get(agent1),simtime);
		}		
	}

	int genArticleFromAllContext(String s,int simtime,ArrayList<Agent> agents) {
		//そのAgentのContextに関する記事をひとつずつ生成する
		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		if ( args.length != 1) {
			System.out.println("parse error, usage : genac int(Agent)");
			return 1;
		}
		if ( Integer.parseInt(args[0]) > Preference.agentNum || Integer.parseInt(args[0]) < 0 ) {
			System.out.println("IndexOutOfBounds!");
			return 1;
		}
		int agentNum = -1;
		agentNum = Integer.parseInt(args[0]);
		Agent a = agents.get(agentNum);
		//pereferensys.favnumじゃなくてAgentのContextの配列長さを使え
		for (int fav = 0 ; fav < Preference.favNum; fav++) {
			a.articleGenOwnContext(simtime,fav);
		}
		a.makeExchangeListLayers();
		return 0;
	}
	
	int genArticleAllAgentsFromAllContext(String s,int simtime,ArrayList<Agent> agents) {
		for (Agent a : agents) {
			for (int fav = 0 ; fav < Preference.favNum; fav++) {
				a.articleGenOwnContext(simtime,fav);
			}
			a.makeExchangeListLayers();	
		}
		return 0;
	}
	
	void initUniqueArticleList() {
		
	}
	
	void addUniqueArticleList() {
		
	}
	
	//writedownを一行ごとやるか、まとめてやるかの違いあるかと思う。
	//今回は一行ごとにやります。
	
	void writeDownArticleList() {
		
	}
	

	void jaccardContext(String s,int simtime,ArrayList<Agent> agents) {

		//int agentnum1 int contextnum1 int agentnum2 int contextnum2
		// contextの類似度を図る
		//急いでいろのバリデーションなし
		int agentNum1 = -1;
		int contextNum1 = -1;
		int agentNum2 = -1;
		int contextNum2 = -1;

		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		if ( args.length != 4 ) {
			System.out.println("parse error, usage : jacc int(Agent1) int(Agent1.Context) int(Agent2) int(Agent2.Context)");
			return;
		}

		agentNum1 = Integer.parseInt(args[0]);
		contextNum1 = Integer.parseInt(args[1]);
		agentNum2 = Integer.parseInt(args[2]);
		contextNum2 = Integer.parseInt(args[3]);

		try {
			Agent agent1 = agents.get(agentNum1);
			Agent agent2 = agents.get(agentNum2);
			Context context1 = agent1.getContexts().get(contextNum1);
			Context context2 = agent2.getContexts().get(contextNum2);
			System.out.println("✨");
			context1.showHashes();
			System.out.println("✨");
			context2.showHashes();
			Jaccard jacc = new Jaccard();
			double result = jacc.apply(context1.getHashes(), context2.getHashes());
			System.out.println(result);
		}
		catch(IndexOutOfBoundsException exception) {
			//handleTheExceptionSomehow(exception);
			System.out.println("error");
		}

	}



}
