package edu.eci.arsw.math;

public class PiThread extends Thread {
	private int a,b;
	private byte[] temporal;
	
	public PiThread(int a, int b) {
		this.a = a;
		this.b = b;
		temporal = new byte[b];
	}
	public void run() {
		temporal = PiDigits.getDigits(a, b);
	}
	
	public byte[] getTemporal() {
		return temporal;
	}

}
