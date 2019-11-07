package hashContextTest;

//import java.util.List;

public class ArticleLog {
  String subject;
  String from;
  String to;
  int timestump;
  String coord;
  
  ArticleLog(String _subject, String _from, String _to ,int _timestump, String _coord) {
	  subject = _subject;
	  from = _from;
	  to  = _to;
	  timestump = _timestump;
	  coord = _coord;
  }
  
    
  //たぶん　つかわない・
  void setvaliable(String _subject, String _from, String _to ,int _timestump, String _coord) {
	  subject = _subject;
	  from = _from;
	  to  = _to;
	  timestump = _timestump;
	  coord = _coord;
  }
  
  void show() {
	  System.out.println("\"subject\" : \""+subject+"\", "+"\"from\" : \""+from+"\", "+"\"to\" : \""+to+"\", "+"\"time\" : \""+timestump+"\", "+"\"pos\" : \""+coord+"\", ");
	  
  }
}
