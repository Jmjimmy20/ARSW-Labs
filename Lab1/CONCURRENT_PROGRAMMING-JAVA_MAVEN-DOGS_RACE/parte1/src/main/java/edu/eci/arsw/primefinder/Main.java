package edu.eci.arsw.primefinder;

public class Main {


	public static void main(String[] args) {
		
		PrimeFinderThread pft=new PrimeFinderThread(0, 9999999);
		PrimeFinderThread pft2=new PrimeFinderThread(10000000, 19999999);
		PrimeFinderThread pft3=new PrimeFinderThread(20000000, 30000000);
		ExecutionThread threadControl = new ExecutionThread(pft,pft2,pft3);
		threadControl.start();
		pft.start();
		pft2.start();
		pft3.start();

		
		
	}

	
}
