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
	
    int POTENCIAL = Preference.favnum; //5です

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
		for(int i = 0; i < POTENCIAL ; i++ ) { contexts.add(new Context(Math.abs(rand.nextInt() % Preference.topicnum ))); }
		
		for(int i = 0; i < POTENCIAL ;i++) {
			potentialAttributes[i] = Math.abs(rand.nextInt() % Preference.topicnum );
		}
		
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
		Article A = new Article(result,name,simtime,Math.abs(rand.nextInt() % Preference.topicnum ));
		articleList.add(A);		
		exLower.add(A);
	}
	
	void articleGenFav(int simtime) {
		Random rand = new Random();
		int fav = potentialAttributes[Math.abs(rand.nextInt()) % POTENCIAL]; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Article A = new Article(result,name,simtime,fav);
		articleList.add(A);		
		exUpper.add(A);
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
		Random rand = new Random();
		int fav = potentialAttributes[Math.abs(rand.nextInt()) % POTENCIAL]; //pottencialからひとつ選ぶ
		String p = RandomStringUtils.randomAlphabetic(16);
		byte[] hashbyte = DigestUtils.md5(p);
		String result = Base64.getUrlEncoder().encodeToString(hashbyte);
		Context c = this.contexts.get(fav);
		Article a = new Article(result,name,simtime,fav);
		articleList.add(a);		
		exUpper.add(a);
	}
	
	void exchangeByContext(int simtime) {
		
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
						contexts.get(i).addHash(s.getHashID()+"♡");
						contexts.get(i).deduplication();
					}
				}           	
			}
		}	
	}
	
	
	
   
	
	void exchange_T4(Agent a){
		List<Article> downLoads = a.getExchangeList();
		for (Article s : downLoads) {
			boolean collision = false;
			for (Article j : articleList) {
				if (j.getHashID().equals(s.getHashID() )) {
					collision = true;
				}
			}
			if (collision == false) {
				articleList.add(s);
				if (s.isTrapped()) {
					System.out.println("trapped Article : " + s.getHashID() + ", from : " +  a.getName() + ", to : " + name );
				}
				
				for (Context c : contexts) {
					if (Math.abs( c.getAttribute()- s.getPotentialAttribute() ) == 5){
						System.out.println("from : " +  a.getName() + " to : " + name+" ❦ "+ s.getHashID());
						c.addHash(s.getHashID());
						c.addCash(s);
						c.deduplication();
						exMiddle.add(s);
						
					}
				}           	
			}
		}	
	}
	
	
	

	

	
	
	void exchangeBasedContext(Context context) {
		//1.相手からContextをもらう
		//2.手持ちのArticleから、もっともJaccard係数が高いものを選ぶ
		// てもちのarticleの中から、もっとも適合度の高いものを選ぶ
		//類似度の閾値を決めて、閾値が一定以上のものでもよいかもしれない
		int[] points = new int[articleList.size()]; 
		double max = 0.0;
		
		Jaccard jacc = new Jaccard();
		for (Article a : articleList) {
			//points[a.]
			double c = jacc.apply(a.getHashList(), context.getHashes());
			if (c > max) {
				max = c;
			}
		}
	}
	
	Article exchangeBasedContextA(Context context) {
		//1.相手からContextをもらう
		//2.手持ちのArticleから、もっともJaccard係数が高いものを選ぶ
		// てもちのarticleの中から、もっとも適合度の高いものを選ぶ
		//類似度の閾値を決めて、閾値が一定以上のものでもよいかもしれない
		int[] points = new int[articleList.size()]; 
		Jaccard jacc = new Jaccard();
		double max = 0.0;
		int ret = 0;
		int i = 0;
		
		for (Article a : articleList) {
			//points[a.]
			double c = jacc.apply(a.getHashList(), context.getHashes());
			if (c > max) {
			  ret = i;	
			}
			i++;
		}
		return articleList.get(ret);
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
