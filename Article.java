package hashContextTest;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Article implements Serializable ,Cloneable{
	private int number;
	private int createdTime;
	private int lastTransportTime;//最終交換時刻。今はつかわない。
	private int transportCount;
	//交換回数。交換回数が多い記事は交換候補から避けたほうよい・
	//反面、交換回数が多い記事をhash listにおいておけばcontextの共通性が高まるだろう。いわば殿堂いり。
	private String hashID;
	private String description;
	private String author;
	private String transporter;
	private ArrayList<String> hashList;
	private int potentialAttribute;
	private List<String> ownerList;
	private Boolean isTrapped; 
	private ArrayList<ArticleLog> articleLogs;
	
	
	
	Article(String _hashID){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		isTrapped = false;
		articleLogs = new ArrayList<ArticleLog>();
	}
	
	Article(String _hashID,int _attr){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		articleLogs = new ArrayList<ArticleLog>();
		isTrapped = false;

	}
	
	Article(String _hashID,String _author,int _createdTime,int _attr){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		createdTime = _createdTime;
		author = _author;
		isTrapped = false;
		articleLogs = new ArrayList<ArticleLog>();
		description = "";

	}
	
	Article(String _hashID,String _author,int _createdTime,int _attr,Boolean _isTrapped){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		createdTime = _createdTime;
		author = _author;
		isTrapped = _isTrapped;
		articleLogs = new ArrayList<ArticleLog>();
		description = "";

	}
	
	void WriteDescription(String _desc) {
		description = _desc;
		
	}
	
	void ShowArticleInfo(){
		System.out.println("hashID:"+hashID+" Author:"+author+" Attribute:"+potentialAttribute+" createdtime:"+createdTime+" Description:"+description);
	}
	
	void ShowArticleInfoCSV(){
		String json = "{"
				+ "\"hashID\":" + "\"" +hashID + "\","
				+ "\"Author\":" + "\"" +author + "\","
				+ "\"Attribute\":" + "\"" +potentialAttribute + "\","
				+ "\"Createdtime\":" + "\"" +createdTime + "\","
				+ "\"Description\":" + "\"" +description + "\","
				+ "\"Hystory\":";
		
		
				json +="\n";
				json +="{";
				for( ArticleLog articleLog : articleLogs) {
					json += articleLog.logCSV() +",";
				}
				json += "}"+ "}";
				System.out.println(json);
		
		
		
	}
	
	int getNumber() {
		return number;
	}
	
	String getHashID() {
		return hashID;
	}
	
	double getScore(){
		double scoreSum = 0;
		for (ArticleLog l : articleLogs ) {
			scoreSum += l.score;
		}
		return scoreSum;
	}
	
	ArrayList<String> getHashList(){
		return hashList;
	}
	
	int getPotentialAttribute() {
		return potentialAttribute;
	}
	
	List<String> getOwnerList(){
		return ownerList;
	}
	
	Boolean isTrapped() {
		return isTrapped;
	}
	
	void setTrap() {
		isTrapped = true;
	}
	
	void unSetTrap() {
		isTrapped = false;
	}
	
	void addHash(String _hash){
		hashList.add(_hash);
	}	
	
	void hashDump(){
		hashList.forEach(s -> {
            System.out.println(s);
        });
	}	
	void WriteTransportTime(int _simtime) {
		lastTransportTime = _simtime;
	}
	
	void WriteTransporter(String _transporter) {
		transporter = _transporter;
		
	}
	
	void addLog(ArticleLog _articleLog) {
		articleLogs.add(_articleLog);
		
	}
	
	String getTransporter() {
		return transporter;
		
	}
	

}
