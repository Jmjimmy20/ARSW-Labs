package edu.eci.arsw.math;

public class PiThread extends Thread {
	private int start,count;
	private byte[] digits;
	private static int DigitsPerSum = 8;
	
	public PiThread(int a, int b) {
		start = a;
		count = b;
		digits = new byte[b];
	}
	public void run() {
		double sum = 0;

		for (int i = 0; i < count; i++) {
			if (i % DigitsPerSum == 0) {
				sum = 4 * PiDigits.sum(1, start)
						- 2 * PiDigits.sum(4, start)
						- PiDigits.sum(5, start)
						- PiDigits.sum(6, start);

				start += DigitsPerSum;
			}

			sum = 16 * (sum - Math.floor(sum));
			digits[i] = (byte) sum;
		}

	}
	
	public byte[] getDigits() {
		return digits;
	}

	

}
