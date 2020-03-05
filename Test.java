package hashContextTest;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		System.out.println("x");
		ArrayList<Integer> list = new ArrayList<Integer>();
		 
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		System.out.println("list.size() = "+list.size());
		List <Integer> subList = list.subList(0, list.size());
		System.out.println("sublist.size() = "+subList.size());
		System.out.println("show subList...");
		for(Integer i : subList ) {
			System.out.println(i);
		}
		
		System.out.println(list.get(list.size()-1));

	}

}
