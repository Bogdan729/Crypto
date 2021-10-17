package tasks.task3;

import tasks.GeneralCrypto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class RSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите сообщение:");
        String msg = sc.next().toUpperCase();

        System.out.println("Введите 2 простых числа p и q: ");

        int p = sc.nextInt();
        int q = sc.nextInt();

        int n = p * q;

        System.out.println("\n" + "n: " + n);

        int phi = GeneralCrypto.functionEuler(p, q);
        System.out.println("phi: " + phi);

        int d = GeneralCrypto.getD(phi);
        System.out.println("d: " + d);

        int e = GeneralCrypto.findE(d, phi);
        System.out.println("e: " + e + "\n");

        System.out.println("Открытый ключ: (" + e + ", " + n + ")");
        System.out.println("Закрытый ключ: (" + d + ", " + n + ")" + "\n");

        BigInteger[] arr = getBigIntegers(msg);

        System.out.println(Arrays.toString(arr));

        System.out.println(Arrays.toString(encoder(arr, e, n)));
    }

    static BigInteger[] getBigIntegers(String msg) {
        BigInteger[] numbers = new BigInteger[msg.length()];
        char[] arr = msg.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            numbers[i] = BigInteger.valueOf(GeneralCrypto.ALPHABET.indexOf(arr[i]));
        }

        return numbers;
    }

    static BigInteger[] encoder(BigInteger[] arr, int e, int n) {
        BigInteger[] cryptogram = new BigInteger[arr.length];
        BigInteger eBig = BigInteger.valueOf(e);
        BigInteger nBig = BigInteger.valueOf(n);

        for (int i = 0; i < arr.length; i++) {
            BigInteger r = arr[i].modPow(eBig, nBig);
            cryptogram[i] = r;
        }

        return cryptogram;
    }
}
