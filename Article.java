package hashContextTest;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Article implements Serializable{
	private int number;
	private int createdTime;
	private int transportTime;
	private String hashID;
	private String author;
	private String transporter;
	private ArrayList<String> hashList;
	private int potentialAttribute;
	private List<String> ownerList;
	private Boolean isTrapped; 
	
	Article(String _hashID){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		isTrapped = false;
	}
	
	Article(String _hashID,int _attr){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		isTrapped = false;

	}
	
	Article(String _hashID,String _author,int _createdTime,int _attr){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		createdTime = _createdTime;
		author = _author;
		isTrapped = false;

	}
	
	Article(String _hashID,String _author,int _createdTime,int _attr,Boolean _isTrapped){
		hashID = _hashID;
		hashList = new ArrayList<String>();
		potentialAttribute = _attr;
		createdTime = _createdTime;
		author = _author;
		isTrapped = _isTrapped;

	}
	
	void ShowArticleInfo(){
		System.out.println("hashID:"+hashID+" Author:"+author+" Attribute:"+potentialAttribute+" createdtime:"+createdTime);
	}
	
	int getNumber() {
		return number;
	}
	
	String getHashID() {
		return hashID;
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
	
	void addHash(String _hash){
		hashList.add(_hash);
	}	
	
	void hashDump(){
		hashList.forEach(s -> {
            System.out.println(s);
        });
	}
	
	void WriteTransportTime(int _transportTime) {
		transportTime = _transportTime;
	}
	
	void WriteTransporter(String _transporter) {
		transporter = _transporter;
		
	}
	

}
