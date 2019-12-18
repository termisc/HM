package hashContextTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class LoadedSim {

	//これテストなんで、なにもかも適当でいいです
	public static void main(String[] args){


		//util.deduplicationContext(c);
		Random rand = new Random();
		int randomNum = rand.nextInt();
		Misc misc = new Misc();
		Util util = new Util();		
		int[] pair = util.ramdomMatch(2);

		ArrayList<Agent> agents = new ArrayList<Agent>();
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("agents.bin"));
			agents = (ArrayList<Agent>) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//25*25の遭遇率の配列をつくります
		//25はPreference.agentnumから参照します
		//確率はdouble,floatあるがfloatでよい	
		Random rnd = new Random();

		float[][] compatibility = new float[Preference.agentNum][Preference.agentNum];
		try {
			ObjectInputStream objInStream 
			= new ObjectInputStream(
					new FileInputStream("map.bin"));
			compatibility = (float[][] ) objInStream.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		int simulateTime = 0;
		float limen;

		//main loop

		int heartCount = 0;
		


		String hashLog = "";

		for (Agent a : agents) {
			List<Context> contexts =  a.getContexts();
			System.out.println("★★★★");
			/*
			for(int i = 0 ; i < 5 ; i++ ) {
				System.out.println("\n♡♡♡♡"+a.getPotentialAttributes()[i]);
				contexts.get(i).showHashes();
			}
			*/
		}
		

		//Context sample = agents.get(5).getContexts().get(4);



		System.out.print("おわりです");
		System.exit(0);

		try{
			File file = new File("hashLog.txt");
			FileWriter filewriter = new FileWriter(file);
			filewriter.write(hashLog);
			filewriter.close();
		}catch(IOException e){
			System.out.println(e);
		}

		//util.hashTest();
		System.out.print("おわりです");
	}
}




