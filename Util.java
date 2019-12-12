package hashContextTest;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
//Utilみたいなクラスがふえてきたらパッケージわけます
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		case "save" :
			System.out.println("save");
			//saveAgents(agents,"hoge.bin");
			save(agents,s);
			break;	
		case "meet" :
			session(s,agents);
			break;
		case "context" :
			System.out.println("✨");
			showContexts(s,agents);
			//数字でエージェントごとの
			break;
			
		case "alist" :
			System.out.println("alist✨");
			alist(s,agents);
			//showContexts(s,agents);
			//数字でエージェントごとの
			break;
			
		case "train" :
			System.out.println("💪");
			train(s,simtime,agents);
			break;
		
		case "gen" :
			System.out.println("gen article from context");
			genAllContext(s,simtime,agents);
			break;
			
		case "msc" :
			System.out.println("💪");
			sessionContext(s,agents);
			break;
			
		case "exit" :
			System.out.println("Have a nice day.");
			System.exit(0); //ほんとはここに書かず、引数で判断して呼び出し側で閉じるものだよ
			break;

		}
		
		return simtime;
	}

	void alist(String s, ArrayList<Agent> agents) {
		// TODO Auto-generated method stub
		String[] args = s.split(" "); 
		args = Arrays.copyOfRange(args, 1, args.length);
		int agentnum = -1;
		agentnum = Integer.parseInt(args[0]);
		agents.get(agentnum).showArticle();
	}

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

	void deduplicationContext(Context _context) {
		ArrayList<String> hashes = _context.getHashes();
		for(int i = 0; i < hashes.size(); i++) {
			for(int j = 0; j < hashes.size(); j++) {
				if ( i != j && hashes.get(i).equals(hashes.get(j)) ){
					hashes.remove(j);
				}
			}
			_context.setHashes(hashes);
		}

	}

	void exchengeEachOther(Agent a,Agent b) {
		a.exchange_T4(b);
		b.exchange_T4(a);
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

	void saveAgents(ArrayList<Agent> agents, String filename) {
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
							ArrayList<String> h1 = contexts.get(co.getAttribute()).getHashes();
							ArrayList<String> h2 = co.getHashes();
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
					
					//contexts[2].showHashes();
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
				agents.get(agent).showHashes();
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
		
	
	void train (String s,int simtime,ArrayList<Agent> agents) {
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				int goal = Integer.parseInt(arg);
				for(int i = 0 ; i < goal ; i++) {
					int[] couple = ramdomMatch(25);
					exchengeEachOther(agents.get(couple[0]),agents.get(couple[1]));
				}
				int[] couple = ramdomMatch(25);
				exchengeEachOther(agents.get(couple[0]),agents.get(couple[1]));
				System.out.println("OK");
			}else {
				System.out.println("Error");
			}
		}
		
	}
	
	int sessionContext(String s,ArrayList<Agent> agents ) {
		//(1)要求エージェントの番号　(2)コンテクストの番号 (3)供給エージェントの番号
		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		//validationしません　いそがしいから
		int agent1 = -1;
		int agent2 = -1;
		int context = -1;
		System.out.println(args.length);
		if ( args.length != 3) {
			System.out.println("parse error, usage : sc int(Agent request) int(Agent reply) int(context) ");
			return 1;
		}
		agent1 = Integer.parseInt(args[0]);
		context = Integer.parseInt(args[1]);
		agent2 = Integer.parseInt(args[2]);
		try {
			Agent donner = agents.get(agent1);
			Agent recipient = agents.get(agent2);
			Context c = recipient.getContexts().get(context);
			donner.exchangeBasedContext(recipient.getContexts().get(context));
		}
		catch(IndexOutOfBoundsException exception) {
		    //handleTheExceptionSomehow(exception);
			System.out.println("error");
		}
		return 0;		
	}
	
	void session (String s, ArrayList<Agent> agents) {
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
		  System.out.println("!!!");
		  exchengeEachOther(agents.get(agent1),agents.get(agent2));
		}		
	}

	int genAllContext(String s,int simtime,ArrayList<Agent> agents) {
		//そのAgentのContextに関する記事をひとつずつ生成する
		String[] args = s.split(" ");
		args = Arrays.copyOfRange(args, 1, args.length);
		if ( args.length != 1) {
			System.out.println("parse error, usage : genac int(Agent)");
			return 1;
		}
		int agentnum = -1;
		agentnum = Integer.parseInt(args[0]);
		Agent a = agents.get(agentnum);
		for (int fav = 0 ; fav < Preference.favnum; fav++) {
			a.articleGenOwnContext(simtime,fav);
			a.makeExchangeListLayers();
		}
		return 0;
	}

    

}
