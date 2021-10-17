package tasks.task5;

import tasks.GeneralCrypto;

import java.math.BigInteger;
import java.util.Scanner;

public class EDS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите 2 простых числа p и q: ");

        int p = sc.nextInt();
        int q = sc.nextInt();
        int n = p * q;

        System.out.println("Введите хеш-образ: ");

        int hashImg = sc.nextInt();

        System.out.println("\n" + "Хеш образ: " + hashImg + "\n");

        int phi = GeneralCrypto.functionEuler(p, q);
        int d = GeneralCrypto.getD(phi);
        int e = GeneralCrypto.findE(d, phi);

        System.out.println("Открытый ключ: (" + e + ", " + n + ")");
        System.out.println("Закрытый ключ: (" + d + ", " + n + ")" + "\n");

        int result = eds(hashImg, d, n);

        int test = eds(result, e, n);

        System.out.println("Результат: " + result);

        System.out.println("Проверка: " + test);
    }

    static int eds(int x, int y, int z) {
        BigInteger xBig = BigInteger.valueOf(x);
        BigInteger yBig = BigInteger.valueOf(y);
        BigInteger zBig = BigInteger.valueOf(z);

        BigInteger r = xBig.modPow(yBig, zBig);

        return r.intValue();
    }
}
