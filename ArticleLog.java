package hashContextTest;

import java.io.Serializable;

//import java.util.List;

public class ArticleLog implements Serializable{
  String subject;
  String from;
  String to;
  int timestump;
  String coord;
  double score;
  
  ArticleLog( String _from, String _to ,int _timestump, String _coord, double _score) {
	  from = _from;
	  to  = _to;
	  timestump = _timestump;
	  coord = _coord;
	  score = _score;
  }
  
    
  //たぶん　つかわない・
  void setvaliable(String _subject, String _from, String _to ,int _timestump, String _coord) {
	  subject = _subject;
	  from = _from;
	  to  = _to;
	  timestump = _timestump;
	  coord = _coord;
	  score = 0;
  }
  
  String logCSV() {
	  String json ="";
	  json += "{";
	  json += "\"from\":" + "\"" + from + "\",";
	  json += "\"to\":" + "\"" + to + "\",";
	  json += "\"timestump\":" + timestump + "\"";
	  json += "\"score\":" + score + "\"";
	  json += "}";
	  return  json;
  }
  
  void show() {
	  System.out.println("\"subject\" : \""+subject+"\", "+"\"from\" : \""+from+"\", "+"\"to\" : \""+to+"\", "+"\"time\" : \""+timestump+"\", "+"\"pos\" : \""+coord+"\", ");
	  
  }
}
