package hashContextTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class Misc {
	
	void showAttributes() {
		
	}
	
	
	
	
	
	String  showAgentLog(Agent _agent) {
		String log = "";
		log += "{\n";
		log += "\"name\" : " + "\"" + _agent.getName() + "\",\n";
		log += "\"attr\" : {";
		/*
		for(int i = 0; i < _agent.getPotentialAttributes().length ;i++) {
			log +=  _agent.getPotentialAttributes()[i] + ", ";
		}
		*/
		
		log += "},\n";
		log += "\"contextLength\" : {";
		for (Context c : _agent.getContexts()) { log += c.hashes.size() + "," ;	}
		log += "}\n}\n";
		return log;
	}
	
	
	String  showAgentContext(Agent _agent) {
		String log = "";
		log += "{\n";
		log += "\"name\" : " + "\"" + _agent.getName() + "\",\n";
		log += "\"attr\" : {";
		/*
		for(int i = 0; i < _agent.getPotentialAttributes().length ;i++) {
			log +=  _agent.getPotentialAttributes()[i] + "■■■  : ";
			log += _agent.getContexts().get(i).showHashesForLog();
		}
		*/
		log += "},\n";
		log += "\"contextLength\" : {";		
		for (Context c : _agent.getContexts()) { log += c.hashes.size() + "," ;	}
		
		log += "}\n";
		//log += "\"hashes\" : {";
		//List<Context> contexts = _agent.getContexts();
		/*
		for (Context c : _agent.getContexts() ) {
			log+=c.showHashesForLog();
		}
		*/
		//for(Cont_agent.)
		log += "}\n";
		log +=	"}\n";
		return log;
	}
	
	void  saveAgentState(Agent _agents) {
		
		try {
			ObjectOutputStream objOutStream = 
		             new ObjectOutputStream(
		             new FileOutputStream("agents.bin"));
			objOutStream.writeObject(_agents);
            objOutStream.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void  saveMap(float[][] _compatibility){
		
		try {
			ObjectOutputStream objOutStream = 
		             new ObjectOutputStream(
		             new FileOutputStream("map.bin"));
			objOutStream.writeObject(_compatibility);
            objOutStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
