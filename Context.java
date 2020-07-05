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
	LinkedList<String> hashes; //相手に渡すハッシュ集合
	LinkedList<Article> caches; // Article recommended by context.
	
	//キリがないのでQueue,10こまで. たまったらSynonimsをもとに再構成する
	//ArrayList<ArrayList> synonims; 
	//Cacheに実態のあるArticleのHashからcontextのhashesを再構築する。//なくてもいいかも
	
	public class hashData{
		String hash;
		int score;
	}
	
	LinkedList<hashData> domains;//ハッシュ集合。相手に渡さなず、ハッシュに関するデータを保持している。ハッシュごとのスコアなど。このデータをもとにhashes やcachesを構成（したい）
	
	
	
	Context(){
	  hashes = new  LinkedList<String>();
	  caches = new LinkedList<Article>();
	  domains = new LinkedList<hashData>();	  
	}
	
	Context(int _attribute){
		  hashes = new  LinkedList<String>();
		  caches = new LinkedList<Article>();
		  domains = new LinkedList<hashData>();
		  attribute = _attribute;
	}
	
	Integer getAttribute() {
		return attribute;
	}
	
	LinkedList<String> getHashes() {
		return hashes;
	}
	
	void addHash(String _hash){
		for(String s: hashes) {
			if(_hash.equals(s)) {
				return;
			}
		}
		hashes.add(_hash);
	}
	
	void addHashData(String _hash){
		for(hashData d: domains) {
			if(_hash.equals(d)) {
				return;
			}
		}
		hashData d = new hashData();
		d.hash = _hash;
		d.score = 0;
		domains.add(d);
	}
	
	void addCache(Article _cache) {
		for(Article c: caches) {
			if(_cache.getHashID().equals(c.getHashID())) {
				return;
			}
		}
		caches.add(_cache);
	}
	
	void recieveCache(List<Article> arts) {
		for(Article a : arts) {
			addCache(a);
		}
		caches.addAll(arts);
	}
	
	void recieveContext(List<Article> arts) {
		//recievecache と recievecontextは統合してもいいかも(いまのところ運用がかわらん)
		for(Article a : arts) {
			  addHash(a.getHashID());
			  //addHashに重複回避あります
		}
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
	
	String getHashesForLog(){
		String log = "";
		for (String s : hashes) {
			log += s + ",";
		}		
		return log;
	}
		
	
	void reconstruct() {
		//hashDataを参考にしてcache,hashes再構成するよ
		//1.重複回数が多いものを優先(基底)
		//2.新しいものを優先
		//3.(どうする？作成頻度にもとづいてバラけるように配置したい)
	}
		
	
	void deduplication() {
		//シミュレーションにエラーなければ使わなくていい(はず。)
		for(int i = 0; i < hashes.size(); i++) {
			for(int j = 0; j < hashes.size(); j++) {
				if ( i != j && hashes.get(i).equals(hashes.get(j)) ){
					hashes.remove(j);
				}
			}
		}
	}
	
	void cacheDeduplication() {
		//シミュレーションにエラーなければ使わなくていい(はず。)
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
			//サイズ超過分、先頭から削除したリストを作成し上書き。
			List<Article> sub = caches.subList(caches.size()-Preference.cacheSize, caches.size()-1);
			//ArrayList<Article> cache = sub.newArrayList(sub);
			caches = new LinkedList<Article>(sub);
			System.out.println("E");
			//System.exit(0);		
		}
		return caches;
	}
	
	LinkedList<Article> hashSizeEqualize() {
		if (hashes.size() > Preference.contextSize ) {
			List<String> sub = hashes.subList(hashes.size()-Preference.contextSize, hashes.size()-1);
			//ArrayList<Article> cache = sub.newArrayList(sub);
			hashes = new LinkedList<String>(sub);
			System.out.println("e");
			//System.exit(0);		
		}
		return caches;
	}

	
	

}
