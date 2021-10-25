package tasks;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

public class GeneralCrypto {
    public final static String ALPHABET = " АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

    public static String xorOperation(String a, String b, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) == b.charAt(i))
                sb.append("0");
            else
                sb.append("1");
        }
        return sb.toString();
    }

    public static String stringToBinarySeq(String s) throws UnsupportedEncodingException {
        byte[] bytes = s.getBytes("windows-1251");
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    // разбиение последовательности по n бит
    public static String stringConversion(String binaryStr, int separator) {
        char[] arr = binaryStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i += separator) {
            sb.append(binaryStr, i, i + separator);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static int functionEuler(int p, int q) {
        return (p - 1) * (q - 1);
    }

    // нахождение наибольшего общего делителя
    public  static int gcd(int p, int q) {
        int t;

        while (q != 0) {
            t = p;
            p = q;
            q = t % q;
        }

        return p;
    }

    public static int getD(int phi) {
        Random rand = new Random();
        int d = rand.nextInt(phi);

        while (d == 1 || gcd(d, phi) != 1) {
            d = rand.nextInt(phi);
        }

        return d;
    }

    public static int findE(int d, int phi) {
        int e = 0;

        while ((d * e - 1) % phi != 0) {
            e++;
        }

        return e;
    }

    // возвращает массив чисел, соответствующий номерам букв в алфавите
    public static int[] getNumbers(String msg) {
        int[] numbers = new int[msg.length()];
        char[] arr = msg.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            numbers[i] = GeneralCrypto.ALPHABET.indexOf(arr[i]);
        }

        return numbers;
    }

    public static int hashEncoder(int[] arr, int n, int h0) {
        BigInteger eBig = BigInteger.valueOf(2);
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger sum;
        BigInteger m;

        BigInteger h1 = BigInteger.valueOf(h0 + arr[0]);
        BigInteger h = h1.modPow(eBig, nBig);

        for (int i = 1; i < arr.length; i++) {
            m = BigInteger.valueOf(arr[i]);
            sum = h.add(m);
            h = sum.modPow(eBig, nBig);
        }

        return h.intValue();
    }
}