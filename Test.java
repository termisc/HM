package hashContextTest;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		
		String hashID = "hoge"; 
		String author ="Bob";
		int attribute = 233;
		int createdtime = 1;
		String description = "I found nice one";
		
		//List Atriclelog
		ArrayList<ArticleLog> articleLogs = new ArrayList<ArticleLog>();
		ArticleLog a = new ArticleLog("alice","bob",1,"-");
		ArticleLog b = new ArticleLog("las","ref",2,"-");
		articleLogs.add(a);
		articleLogs.add(b);
		
		
		
		String json = "{"
		+ "\"hashID\":" + "\"" +hashID + "\","
		+ "\"Author\":" + "\"" +author + "\","
		+ "\"Attribute\":" + "\"" +attribute + "\","
		+ "\"Createdtime\":" + "\"" +createdtime + "\","
		+ "\"Description\":" + "\"" +description + "\","
		+ "\"Hystory\":";
		
		json +="{";
		for( ArticleLog articleLog : articleLogs) {
			json += articleLog.logCSV() +",";
		}
		json += "}"+ "}";
		System.out.println(json);

	}
	
	
	public static String hystoryCSV(ArrayList<ArticleLog> articleLogs) {
		String json ="{";
		for( ArticleLog a : articleLogs) {
			json += a.logCSV() +",";
		}
		json += "}";
		return json;
	}

}
