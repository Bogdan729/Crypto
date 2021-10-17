package tasks.task4;

import tasks.GeneralCrypto;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class HashFunction {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите сообщение: ");
        String msg = sc.next().toUpperCase();

        System.out.println("Введите 2 простых числа p и q: ");

        int p = sc.nextInt();
        int q = sc.nextInt();

        System.out.println("Введите начальное значение хеша h0: ");

        int h0 = sc.nextInt();

        int n = p * q;

        int[] arr = getNumbers(msg);

        int result  = GeneralCrypto.hashEncoder(arr, n, h0);

        System.out.println("\n" + "Хеш-образ: " + result);
    }

    static int[] getNumbers(String msg) {
        int[] numbers = new int[msg.length()];
        char[] arr = msg.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            numbers[i] = GeneralCrypto.ALPHABET.indexOf(arr[i]);
        }

        return numbers;
    }
}
