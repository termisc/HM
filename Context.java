package hashContextTest;

import java.io.Serializable;
import java.util.ArrayList;


public class Context  implements Serializable{
	
	// 似たようなトピックをもったハッシュの集合体だと考えてよい。
	//潜在的なトピックを持っているが、それと無関係に、それ自体(ハッシュ)があらたな特徴をもったトピックとして生まれる
	//ハッシュの塊としてわたされる記事たちの、代表的な特徴のあつまりとして表現される
	//各エージェントにより、さまざまなタイミングで構成される
	//エージェントの趣味で任意につくられる(ここはシミュレートするけどある意味フェイク)
	//交換される記事の傾向から統計的に合成される（ここを実装）　統計的に合成されたトピックが実用にたえられるとよい
	
	//Contextsは渡す記事に貼り付けられている
	//記事とは別に自由に使えるかたちで存在する。
	int attribute;
	
	ArrayList<String> hashes;
	//キリがないのでQueue,10こまで. たまったらSynonimsをもとに再構成する
	
	ArrayList<ArrayList> synonims;  //ArrayList<String> を保持してね。
	
	
	ArrayList<Article> caches; // Article recommended by context.
	
	
	Context(){
	  hashes = new  ArrayList<String>();
	  synonims = new ArrayList<ArrayList>();
	  caches = new ArrayList<Article>();
	}
	
	Context(int _attribute){
		  hashes = new  ArrayList<String>();
		  synonims = new ArrayList<ArrayList>();
		  caches = new ArrayList<Article>();
		  attribute = _attribute;
		  
	}
	
	Integer getAttribute() {
		return attribute;
	}
	
	ArrayList<String> getHashes() {
		return hashes;
	}
	
	//重複を回避しよう
	void addHash(String hash){
		hashes.add(hash);
	}
	
	void setHashes(ArrayList<String> _hashes){
		hashes = _hashes;
	}
	
	void setAttr(Integer _attribute) {
		attribute = _attribute;
	}
	
	void showHashes(){
		hashes.forEach(s -> {
            System.out.println(s);
        });
	}
	
	void showCaches(){
		hashes.forEach(c -> {
            System.out.print(c);
        });
	}
	
	String showHashesForLog(){
		String log = "";
		for (String s : hashes) {
			log += hashes + ",'";
		}
		return log;
	}
	
	void addSynonims(ArrayList<String> _hashes) {
		synonims.add(_hashes);
	}
	
	void reconstruct() {
		//Synonimを参考にして再構成するよ
		//1.重複回数が多いものを優先(基底)
		//2.新しいものを優先
		//3.(どうする？作成頻度にもとづいてバラけるように配置したい)
	}
	
	void deduplication() {
		for(int i = 0; i < hashes.size(); i++) {
			for(int j = 0; j < hashes.size(); j++) {
				if ( i != j && hashes.get(i).equals(hashes.get(j)) ){
					hashes.remove(j);
				}
			}
		}
	}

	 void addCache(Article s) {
		 caches.add(s);
		// TODO Auto-generated method stub
		
	}
	

}
