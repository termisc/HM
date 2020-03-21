package hashContextTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class Agent implements Serializable{

	private String name = "Anonymous";
	//Agentに名前をつけるか？個別の名前はひつようなく、リストの添字だけでよい
	//シナリオのために用意された動作を行うエージェントをログ上で区別するために、特別なタグづけができるとあとあと楽かもしれない
	//デフォルト名は"Anonymous",必要に応じてAgent.setNameで名前をつけてやるとよいでしょう

	private int[] potentialAttributes;
	//このAgentがどのような記事を好んでいるか
	//ほんとはListのほうがいい気もするが、今回取り扱うのはContextであって
	//pttencialは若干嘘なのであんまりリッチな書き方したくない
	//明示的な指示で優先的に流すにしても
	//ログ取りで困ったらやるべきか？

	private List<Integer> potentialAttributes_l;

	private List<Article> articleList;
	//ストックしている記事

	private List<Article> exchangeList;
	//記事を交換するときのリスト、持っているarticlesからよいものを選ぶ。待機中に作成する
	//交換するために待機している記事

	private List<Context> contexts;
	// 似たようなトピックをもったハッシュの集合体だと考えてよい。
	//潜在的なトピックを持っているが、それと無関係に、それ自体(ハッシュ)があらたな特徴をもったトピックとして生まれる
	//ハッシュの塊としてわたされる記事たちの、代表的な特徴のあつまりとして表現される
	//各エージェントにより、さまざまなタイミングで構成される
	//エージェントの趣味で任意につくられる(ここはシミュレートするけどある意味フェイク)
	//交換される記事の傾向から統計的に合成される（ここを実装）
	//統計的に合成されたトピックが実用にたえられるとよい

	private List<Article> exUpper;
	//自作記事

	private List<Article> exMiddle;
	//優先度が高い


	private List<Article> exLower;
	//もらった記事を新着
	//upper:middle:bottom比　1:10:100くらい？

	int POTENCIAL = Preference.favNum; //5です

	Agent(){
		//int POTENCIAL = 5;
		//POTENCIAL = Preference.favnum;
		articleList =  new ArrayList<Article>();
		exchangeList =  new ArrayList<Article>();
		exUpper =  new ArrayList<Article>();
		exMiddle =  new ArrayList<Article>();
		exLower =  new ArrayList<Article>();


		potentialAttributes = new int[POTENCIAL];
		contexts = new ArrayList<Context>();
		Random rand = new Random();
		for(int i = 0; i < POTENCIAL ; i++ ) { contexts.add(new Context(Math.abs(rand.nextInt() % Preference.topicNum ))); }

		for(int i = 0; i < POTENCIAL ;i++) {
			potentialAttributes[i] = Math.abs(rand.nextInt() % Preference.topicNum );
		}

	}

	void message(int commentLevel,String message) {

	}

	void setName(String _name) {
		name = _name;
	}

	String getName() {
		return name;
	}

	List<Article> getArticleList(){
		return articleList;
	}

	List<Article> getExchangeList(){
		return exchangeList;
	}


	int[] getPotentialAttributes_X() {
		return potentialAttributes;
	}

	List<Context> getContexts(){
		return contexts;
	}

	void articleGenSimple(int simtime){
		//ランダムで記事のATTRを作成
		//ランダムでその記事のハッシュを作成
		//交換リストに追加	
		Random rand = new Random();
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Article A = new Article(result,name,simtime,Math.abs(rand.nextInt() % Preference.topicNum ));
		articleList.add(A);		
		exLower.add(A);
	}

	void articleGenFav(int simtime) {
		Random rand = new Random();
		int favnum = Math.abs(rand.nextInt()) % POTENCIAL;
		Context c = contexts.get(favnum);
		int fav = potentialAttributes[favnum]; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		//System.out.println("fav = "+fav);
		//System.out.println("contexts.length = "+contexts.size());
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Article a = new Article(result,name,simtime,fav);
		articleList.add(a);		
		exUpper.add(a);
	}

	void articleGenCommonContext(int simtime) {
		Random rand = new Random();
		int fav = potentialAttributes[Math.abs(rand.nextInt()) % POTENCIAL]; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Article A = new Article(result,name,simtime,fav);
		articleList.add(A);		
		exUpper.add(A);
	}

	void articleGenOwnContext(int simtime) {
		//毎時での生成はこれ使います。
		Random rand = new Random();
		int fav = potentialAttributes[Math.abs(rand.nextInt()) % POTENCIAL]; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Context c = this.contexts.get(fav);
		Article a = new Article(result,name,simtime,c.getAttribute());
		articleList.add(0,a);		
		exUpper.add(0,a);
	}

	void articleGenOwnContext(int simtime,int fav) {
		//毎時での生成はこれ使います。
		//Random rand = new Random();
		//int fav = contextnum; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Context c = this.contexts.get(fav);
		Article a = new Article(result,name,simtime,c.getAttribute());
		//System.out.println("gen article test " + result);
		articleList.add(0,a);		
		exUpper.add(a);

		contexts.get(fav).addHash(a.getHashID());
		//contexts.get(fav).deduplication();//新造なので重複排除は必要なし

	}




	void addSpecial(Article a) {
		articleList.add(a);		
		exUpper.add(a);		
	}

	void articleGenSimtime() {
		//シミュレーションの時間軸に沿った記事の生成
		//せ
	}

	void showArticle() {
		for(Article s : articleList) {
			System.out.println(s.getHashID() );
		}
	}

	void showAttr(){
		//余裕があればjson形式で吐く
		System.out.print("\"attr\" : {");
		for(Context c : contexts) {
			System.out.print( c.getAttribute() + ", ");
		}
		System.out.print("},\n");
	}

	void showHashes(){
		//余裕があればjson形式で吐く いまではない
		System.out.print("\"attr\" : {");
		System.out.println("■□□□■□□□■□□□■□□□■□□□■□□□" );
		for(Context c : contexts) {
			//System.out.print( c.showHashes() + ", ");
			System.out.print( c.getAttribute() + ", ");
			System.out.println("□□□□□□□□" );
			c.showHashes();
		}
		System.out.print("},\n");
	}



	//機能検証用、articleListから最新記事をもってくるだけ
	void makeaExchangeListSimple() {
		if (articleList.size() <= 10 ){
			exchangeList.addAll(articleList);
		}
		if (articleList.size() > 11 ){
			for (int i = 1 ; i < 10 + 1 ; i++) {
				exchangeList.add(articleList.get(articleList.size() - i));
			}
		}	

		if (articleList.size() > 31 ){
			for (int i = 1 ; i < 30 + 1 ; i++) {
				exchangeList.add(articleList.get(articleList.size() - i));
			}
		}
	}		

	void downLoad_simple(List<Article> downLoads){
		articleList.addAll(downLoads);
	}

	void downLoad(List<Article> downLoads){
		for (Article s : downLoads) {
			boolean collision = false;
			for (Article j : articleList) {
				if (j.getHashID().equals(s.getHashID() )) {
					collision = true;
				}
			}
			if (collision == false) {
				articleList.add(s);
			}
		}	
	}

	//外部クラスでリストの書き換えを行うための関数　つかわないかも
	void update(List<Article> refill){
		articleList = refill;
	}

	void makeContextfromCoOccurrenceDomain() {
		//articleから、共起ドメインを計算する
		//新着記事のhashtailを利用。
	}

	void makeContextfromPotentialAttributes() {		
		//potencialAttrよりcontextを生成する
		//Agent 生成時に一度だけ使われ		
		for(Article s : articleList) {
			for (int i = 0 ; i < potentialAttributes.length; i++) {
				if (Math.abs(potentialAttributes[i] - s.getPotentialAttribute() ) < 5){
					contexts.get(i).addHash(s.getHashID());
				}
			}
		}		
	}

	void downloadAndAddContext(List<Article> downLoads){
		for (Article s : downLoads) {
			boolean collision = false;
			for (Article j : articleList) {
				if (j.getHashID().equals(s.getHashID() )) {
					collision = true;
				}
			}
			if (collision == false) {
				articleList.add(s);
				for (int i =0 ; i < potentialAttributes.length; i++) {
					if (Math.abs(potentialAttributes[i] - s.getPotentialAttribute() ) < 5){
						System.out.print("❦");
						contexts.get(i).addHash(s.getHashID());
						contexts.get(i).deduplication();
					}
				}           	
			}
		}	
	}


	void exchange_T4(Agent a,int simtime){
		List<Article> downLoads = a.getExchangeList();
		boolean isExchanged = false;//そのセッションで交換があったかどうか。ログに記入するためのフラグです。
		for (Article s : downLoads) {
			boolean collision = false;
			for (Article j : articleList) {
				if (j.getHashID().equals(s.getHashID() )) {
					collision = true;
				}
			}
			if (collision == false) {
				articleList.add(0,s);
				if (s.isTrapped()) {
					System.out.print("trapped Article : " + s.getHashID() + ", from : " +  a.getName() + ", to : " + name +" " );
				}
				for (Context c : contexts) {
					if (Math.abs( c.getAttribute()- s.getPotentialAttribute() ) < 5){
						switch(Preference.t4LogMode) {
						
							case "name-only" :
								//交換があったとき、一度だけ2者の名前を表示
								if(!isExchanged) {
									System.out.print(simtime + " " + a.getName()+"-"+name);
								}
							break;
							
							default :
								System.out.print(simtime + " " + a.getName()+"-"+c.getAttribute()+"❦"+name+"-"+s.getPotentialAttribute()+" ");
							break;		
						}
						//System.out.print(a.getName()+"-"+c.getAttribute()+"❦"+name+"-"+s.getPotentialAttribute()+" ");
						c.addHash(s.getHashID());
						c.addCache(s);
						c.deduplication();
						exMiddle.add(0,s);
						makeExchangeListLayers();
						isExchanged = true;
					}
				} 
			}
		}
		if (isExchanged) {
			System.out.println("");
		}
	}

	void exchangeBasedContext(Context context,int simtime) {
		//1.相手からContextをもらう
		//2.手持ちのArticleから、もっともJaccard係数が高いものを選ぶ
		//類似度の閾値を決めて、閾値が一定以上のものでもよいかもしれない
		//contextのjaccard係数が高ければ、(jaccard > 0.2) で、ContextのCacheから新着5件 (0.2はPreferencesから読むようにしよう)
		//現在使用しｒてる 202003

		Jaccard jacc = new Jaccard();
		for(Context c : contexts) {
			if ( jacc.apply(c.getHashes(), context.getHashes()) > 0.4 ) {
				System.out.print(simtime + " " +"-"+name);
				System.out.println(" caches send from context");
				//contexのキャッシュ上位5件を私ます。
				int amountOfCache = c.caches.size();
				if (amountOfCache < 5) {
					//すべてのcontext
					//for(Article a : c.caches) {System.out.print(a.getHashID());}		
					context.caches.addAll(c.caches);
					System.out.println("");
				}else {
					//cacheが五件以下ならcacheのすべて。cacheが五件以上なら最新5件を渡します。
					//最新5件
					List<Article> newer = c.caches.subList(c.caches.size()-5,c.caches.size()-1);
					//for(Article a : newer) {System.out.print(a.getHashID());}	
					context.caches.addAll(newer);
					System.out.println("");
				}
			}else{
				//System.out.print("☠");
			}
		}
	}


	void showCaches(Context context) {
		for(Context c : contexts) {
			c.showCaches();
		}
	}

	




	int exListSize() {
		return exchangeList.size();
	}

	int mSize() {
		return exMiddle.size();
	}

	void uniteList(int num, List<Article> articleList) {
		if (articleList.size() <= num ){
			exchangeList.addAll(articleList);
		}
		if (articleList.size() > num + 1 ){
			for (int i = 1 ; i < num + 1 ; i++) {
				exchangeList.add(articleList.get(articleList.size() - i));
			}
		}	
	}

	void dumpExMiddle() {
		for(Article s : exMiddle) {
			s.ShowArticleInfo();
		}
	}

	void dumpEx() {
		for(Article s : exchangeList) {
			s.ShowArticleInfo();
		}
	}


	void makeExchangeListLayers() {
		//層ごとにexchangeListを再構成する
		//ほかのきっかけでexUpper、exMiddle,Exlowerなどに新作ができてるはずである。
		exchangeList = new ArrayList<Article>();
		uniteList (10,exUpper);
		uniteList (10,exMiddle);
		uniteList (10,exLower);
	}

	void makeArticleBasedByContext() {
		//1.simulationの周期に合わせてarticleを生成します。
		//2.手持ちの嗜好より1.attributeに準じたarticleを選択
	}


	void shrinkContextDomain() {
		//類似度の高いコンテクストを結合する
		//先端部に古いほうのコンテクスト、中心部に共起度が高いコンテクスト、終端部に新しい方のコンテクスト
		//自動生成後に行う
	}
}
