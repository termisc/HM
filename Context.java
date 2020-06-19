package hashContextTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
	
	LinkedList<String> hashes;
	//キリがないのでQueue,10こまで. たまったらSynonimsをもとに再構成する
	
	ArrayList<ArrayList> synonims;  //ArrayList<String> を保持してね。
	
	
	LinkedList<Article> caches; // Article recommended by context.
	
	
	Context(){
	  hashes = new  LinkedList<String>();
	  synonims = new ArrayList<ArrayList>();
	  caches = new LinkedList<Article>();
	}
	
	Context(int _attribute){
		  hashes = new  LinkedList<String>();
		  synonims = new ArrayList<ArrayList>();
		  caches = new LinkedList<Article>();
		  attribute = _attribute;
		  
	}
	
	Integer getAttribute() {
		return attribute;
	}
	
	LinkedList<String> getHashes() {
		return hashes;
	}
	
	//重複を回避しよう
	void addHash(String hash){
		hashes.add(hash);
	}
	
	void setHashes(LinkedList<String> _hashes){
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
		caches.forEach(c -> {
            System.out.println(c.getHashID());
        });
	}
	
	void showCacheCSV(){
		hashes.forEach(c -> {
            System.out.print(c);
            System.out.print(",");
        });
	}
	
	
	String getHashesForLog(){
		String log = "";
		for (String s : hashes) {
			log += hashes + "\n";
		}
		log += hashes + "星";
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
	
	void cacheDeduplication() {
		for(int i = 0; i < caches.size(); i++) {
			for(int j = 0; j < caches.size(); j++) {
				if ( i != j && caches.get(i).getHashID().equals(caches.get(j).getHashID()) ){
					caches.remove(j);
				}
			}
		}
	}
	
	LinkedList<Article> cacheSizeEqualize() {
		if (caches.size() > Preference.cacheSize ) {
			List<Article> sub = caches.subList(caches.size()-Preference.cacheSize,caches.size()-1);
			//ArrayList<Article> cache = sub.newArrayList(sub);
			caches = new LinkedList<Article>(sub);
			System.out.println("EQORIZED");
			//System.exit(0);
		
		}
		
		
		return caches;
	}

	 void addCache(Article s) {
		 caches.add(s);
		// TODO Auto-generated method stub
		
	}
	

}
