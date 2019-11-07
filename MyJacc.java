package hashContextTest;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class MyJacc {




	//集合(Set)の使い方
	//
	//２つの語集合間の類似度として Jaccard 係数を用いる例．
	//Jaccard 係数については，例えば http://www11.atwiki.jp/kenichiro/pages/100.html を参照．
	//
	//(注) 本プログラムは，学生へ提示するプログラム例として作成しているため，あえて冗長な記述を含む．


	public static void main(String[] args) {
		//セット（集合）オブジェクトの生成
		Set<String> setA = new HashSet<String>();
		Set<String> setB = new HashSet<String>();

		//各セットに要素を追加
		setA.add("ABC"); setA.add("DEF"); setA.add("GHI");
		setA.add("ABC"); //登録済みの要素と同じものを登録しても２重にカウントしない．
		setB.add("ABC"); setB.add("DEF"); setB.add("JKL");

		//各セットの要素数を出力
		System.out.println("size of setA = " + setA.size());	// 3が出力される
		System.out.println("size of setB = " + setB.size());	// 3が出力される

		//セット内の全要素を得るには Iterator を用いる．
		//例示として setA に含まれる要素を得て表示してみる．
		Iterator<String> iter;
		iter = setA.iterator();
		while(iter.hasNext()){
			String element = iter.next();
			System.out.println(element);
		}

		//２つの集合を比較し，類似度を求める．
		//積集合（共通集合）を作る
		//(注) setA.retainAll(setB)とすると，setA の当初の状態が崩れるため，予め intersection へコピーする．
		Set<String> intersection = new HashSet<String>(setA);	//setA を intersection へコピー
		intersection.retainAll(setB);	//積集合
		System.out.println("size of intersetion = " + intersection.size());	// 2が出力される

		//和集合を作る
		Set<String> union = new HashSet<String>(setA);	//setA を union へコピー
		union.addAll(setB);	//和集合
		System.out.println("size of union = " + union.size());	// 4が出力される

		//類似度(Jaccard係数) = |A cap B| / |A cup B|
		double similarity = (double)intersection.size() / union.size();
		System.out.println("similarity = " + similarity);
	}




}
