package hashContextTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExV6Tracer {


	public static void main(String[] args){

		//6記事を出力する

		//25人のエージェントを読みます
		ArrayList<Agent> agents = new ArrayList<Agent>();
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("agents.bin"));
			agents = (ArrayList<Agent> ) objInStream.readObject();
			objInStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		
		agents.get(0).getArticleList().get(0).WriteDescription("HOGE");
		//for(Agent x :agents ) {x.dumpAllArticle();	System.out.println("");}
		agents.get(0).dumpAllArticle();
		
		// Read Articles (Binary)
		
		List<Article> articles = new ArrayList<Article>();
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("articles.bin"));
			articles = (List<Article> ) objInStream.readObject();
			objInStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//for(Article a :articles ) {a.ShowArticleInfoCSV();}
		
		
		




	}
}
