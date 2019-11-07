package hashContextTest;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

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

	void hashTest() {

		HashMap<String,ArrayList<ArticleLog>> kingmap = new HashMap<String,ArrayList<ArticleLog>>();
		kingmap.put("りんご",new ArrayList<ArticleLog>());
		kingmap.get("りんご").add(new ArticleLog("copy","alicce","bob",1,"NA"));
		kingmap.get("りんご").add(new ArticleLog("copy","alicce","bob",2,"NA"));
		kingmap.get("りんご").add(new ArticleLog("copy","alicce","bob",3,"NA"));
		kingmap.get("りんご").add(new ArticleLog("copy","alicce","bob",4,"NA"));
		kingmap.get("りんご").add(new ArticleLog("copy","alicce","bob",5,"NA"));
		//kingmap.get("りんご").get(0).show();

		System.out.println(kingmap.get("りんご").size());
		kingmap.put("みかん",new ArrayList<ArticleLog>());
		kingmap.get("みかん").add(new ArticleLog("copy","alicce","bob",6,"NA"));
		kingmap.get("みかん").add(new ArticleLog("copy","alicce","bob",7,"NA"));
		kingmap.get("みかん").add(new ArticleLog("copy","alicce","bob",8,"NA"));
		kingmap.get("みかん").add(new ArticleLog("copy","alicce","bob",9,"NA"));
		System.out.println(kingmap.get("みかん").size());

		//すべての記事に関して log dump
		kingmap.forEach((k,v)->{
			System.out.println("\"Article_ID\" : \"" + k +"\" ,{");		
			for(ArticleLog i : v){
				//System.out.println(item);
				i.show();
			}
			System.out.println("},");
		});

		//keyはArticleのID,ValueはJSON. 連続なExchange History を　IDごとにアクセスしたい
		//すべてJsonだときびしい
		//必要に応じてJSONからMassage Packに乗り換えられるとよいだろう　エスケープが煩雑。
	}

	void saveAgents(ArrayList<Agent> agents, String filename) {
		//よくつかうんので
		try {
			ObjectOutputStream objOutStream = 
					new ObjectOutputStream(
							new FileOutputStream(filename));
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

	void Commands(String s, ArrayList<Agent> agents) {

		String[] args = s.split(" ");

		System.out.println("cmd：" + args[0]);
		switch (args[0]) {
		case "dump" :
			System.out.println("dump");
			break;
		case "save" :
			System.out.println("save");
			saveAgents(agents,"hoge.bin");
			break;
		
		case "moon" :
			System.out.println("😄");
			break;
		case "merculy" :
			System.out.println("💪😄🎤 mama~~");
			break;
		case "sex" :
			System.out.println("💓");
			session(s,agents);
			break;
		case "show" :
			System.out.println("✨");
			showHashes(s,agents);
			break;
		case "exit" :
			System.out.println("Have a nice day.");
			System.exit(0); //ほんとはここに書かず、引数で判断して呼び出し側で閉じるものだよ
			break;

		}
	}

	void showHashes(String s, ArrayList<Agent> agents) {
		//最初の引数(int)のみを読みます。
		//hash object
		String[] args = s.split(" "); //冗長だがこれでいいのだ
		args = Arrays.copyOfRange(args, 1, args.length);
		int agent = -1;
		Pattern pattern_obj = Pattern.compile("[0-9]+");
		for (String arg :args) {
			Matcher matcher_obj = pattern_obj.matcher(arg);
			if (matcher_obj.matches() ) {
				agent = Integer.parseInt(arg);
				agents.get(agent).showArticle();
				
				System.out.println("OK");
			}else
				System.out.println("error");
			}
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
				  //System.out.println("errorあとのどうさはスキップ");
				  error_flag = true; 
				  continue;
				}			
				switch (arg) {
				case "-r" :
					System.out.println("らんだむ");
					int[] couple = ramdomMatch(25);
					agent1 = couple[0];
					agent2 = couple[1];
					obj1_flag = true;
					obj2_flag = true;
					break;
				}
			}
		}
		if(error_flag == false && obj2_flag == true) {
		  exchengeEachOther(agents.get(agent1),agents.get(agent2));
		}		
	}



}
