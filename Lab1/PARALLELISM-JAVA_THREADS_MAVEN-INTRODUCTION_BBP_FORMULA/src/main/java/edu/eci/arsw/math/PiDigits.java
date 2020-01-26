package edu.eci.arsw.math;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.Arrays;


///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;


    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param digitos The quantity of digits
     * @param N The number of threads
     * @return An array containing the hexadecimal digits.
     */
    
    public static byte[] getDigits(int start,int digitos, int N) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (digitos < 0) {
            throw new RuntimeException("Invalid Interval");
        }
        byte[] total = new byte[(int)digitos];
        int sumando = digitos/N;
        int end=sumando;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        PiThread[] arregloThread = new PiThread[N];
        for (int i=0;i<N;i++) {
            if (i==N-1) {
                arregloThread[i] = new PiThread(start, sumando+(digitos%N));
            } else {
                arregloThread[i] = new PiThread(start, sumando);
            }
            arregloThread[i].start();
            start = end;
            if (digitos-end<sumando) {
                end = (int) (digitos-1);
            } else {
                end +=sumando;
            }

        }
        for (int i=0;i<N;i++){
            try {
                arregloThread[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        for (int i=0;i<N;i++){
            try {
                outputStream.write(arregloThread[i].getDigits());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outputStream.toByteArray();
    }

    /// <summary>
    /// Returns the sum of 16^(n - k)/(8 * k + m) from 0 to k.
    /// </summary>
    /// <param name="m"></param>
    /// <param name="n"></param>
    /// <returns></returns>
    public static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    /// <summary>
    /// Return 16^p mod m.
    /// </summary>
    /// <param name="p"></param>
    /// <param name="m"></param>
    /// <returns></returns>
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }

}
