package hashContextTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;


public class Jaccard {


	public static void main(String[] args) {
		LinkedList<String> hashA = new LinkedList<String>();
		LinkedList<String> hashB = new LinkedList<String>();
		LinkedList<String> hashC = new LinkedList<String>();
		String i2 = ("_DgS0EIDvYVah4ensilt4w== Olv1yQILDacqUtOzK802-Q== Ko-X4PHvIou4jbJF90vlug== HDBHTt3JBt1qecDwuMnY-w== 7PbuwsPMDG1H2n5HD54lDA== xcMs8146tWQ8NhKn7-MZhw== EIozZ_ik-nXZEbQLtLz4IA== swX_OHzRCtuOsGkW_Ny3ZA== ajE51Za0cLgQaqFv6bAc9A== OdfVGBl3wfvq6TJyltQAQA== Zns32lILM9wRJm2QfOqFBw== ︎0WA4cueVM0hmwmM4E3JeEw== ︎ySdh8GqGbNfBPon0fDN44A== ︎X7W73xpzC62_6rj9WlI_7Q== ︎ZhSelE1Fw3MkScxg95mV0Q== wz19NHkcEdQF-DcP0HAyCg== ︎shKwF0IHBeuAbv6YCjWbNg== ︎khpanL9AUvbI-ly7QrKvfA== ︎wnAnVB6oBpQfpmvuqoT--g==︎ beGfZUny0LI5V-6VPcHKIg== ︎X6wSh3oI6q8xzKoNWXKmag== ︎lxTRvXBqtezK78rR3fzkeg== ︎6niAzeaI6SjFMbtZhIadpg== ︎SXB3XklwylBs6pRPaIF69A== ︎714an_HaAJ--i0dt337mgQ== ︎fG9bm5DH6QcfUS_mroUsJg==︎ tpfElLXpvTOIIODYqiQDag== akmVswscA2D9LBUfaoO5ww==");
		String i3 = ("G-k8eIG2CR-OKKNKI1OAjw== PgwguHwivieaKw-i7ZmBPw== x_c-g1ApNeb7OFXW2ghWVg== 4iY3MAIWKuEf8Kbrr4lqvQ== YHTUbspbjgNZI56f4c18bg== EKw7jpxawjJudve2sQ_X8w== VWuRYJWn5Y8jy-SlSEgMQQ== nEq4qAHbQE-aeuPAXFRUSw== RKALl_Lwe4OfZ9JWimFkmQ== ajE51Za0cLgQaqFv6bAc9A== OdfVGBl3wfvq6TJyltQAQA== 0WA4cueVM0hmwmM4E3JeEw== ︎ wz19NHkcEdQF-DcP0HAyCg== ︎ X7W73xpzC62_6rj9WlI_7Q== ︎ ySdh8GqGbNfBPon0fDN44A== ︎ Zns32lILM9wRJm2QfOqFBw== ︎ ZhSelE1Fw3MkScxg95mV0Q== ︎ khpanL9AUvbI-ly7QrKvfA== ︎ QtHPILlchEvCACpJMaKDjw== ︎ lxTRvXBqtezK78rR3fzkeg== ︎ SXB3XklwylBs6pRPaIF69A== ︎ 714an_HaAJ--i0dt337mgQ== ︎ beGfZUny0LI5V-6VPcHKIg== ︎ 6niAzeaI6SjFMbtZhIadpg== ︎ X6wSh3oI6q8xzKoNWXKmag== ︎ tpfElLXpvTOIIODYqiQDag== ︎fG9bm5DH6QcfUS_mroUsJg== ︎akmVswscA2D9LBUfaoO5ww== ");
		String[] fruits = i2.split(" ");
		String[] vegetables = i3.split(" ");
		LinkedList i2r = new LinkedList(Arrays.asList(i2.split(" ")));
		LinkedList i3r = new LinkedList(Arrays.asList(i3.split(" ")));
		Jaccard jack = new Jaccard();
		LinkedList<String> i2p = new LinkedList();
		LinkedList<String> i3p = new LinkedList();
		jack.apply(i2r,i3r);
		jack.apply(i2r,i2r);
		System.exit(0);
		hashA.add("ABC"); hashA.add("JKL"); hashA.add("GHI");
		hashB.add("ABC"); hashB.add("DEF"); hashB.add("GHI");
		hashC = hashA;
		hashC.retainAll(hashB);
		for(String s: hashC) {
			System.out.println(s);
		}
	}

	float apply(LinkedList<String> hashA,LinkedList<String> hashB) {
		float similarity = 0f;
		ArrayList<String> hashC = new ArrayList<String>(hashA);
		hashC.retainAll(hashB);
		if(hashA.size() + hashB.size() >= 0 && hashC.size() >= 0 ) {
			similarity = (float)hashC.size() / (float)(hashA.size()+hashB.size()) *2;
		} 
		return similarity;
	}

}
