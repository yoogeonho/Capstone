package yghproject;

import java.util.Random;

public class MathClass {
 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random(12);
		
		for(int i=0; i<100; i++)
			System.out.println(rand.nextInt(1000));
		
	}

}
