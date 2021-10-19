package tasks.task2;

import tasks.GeneralCrypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Scanner;


public class GOST28147_89 {
    public static final int[] TABLE = {
            // 0  1   2  3  4  5  6   7
            1, 13, 4, 6, 7, 5, 14, 4,       // 0
            15, 11, 11, 12, 13, 8, 11, 10,  // 1
            13, 4, 10, 7, 10, 4, 4, 9,      // 2
            0, 1, 0, 1, 1, 13, 12, 2,       // 3
            5, 3, 7, 5, 0, 10, 6, 13,       // 4
            7, 15, 2, 15, 8, 3, 13, 8,      // 5
            10, 5, 1, 13, 9, 4, 15, 0,      // 6
            4, 9, 13, 8, 15, 2, 10, 14,     // 7
            9, 0, 3, 4, 14, 14, 2, 6,       // 8
            2, 10, 6, 10, 4, 15, 3, 11,     // 9
            3, 14, 8, 9, 6, 12, 8, 1,       // 10
            14, 7, 5, 14, 12, 7, 1, 12,     // 11
            6, 6, 9, 0, 11, 6, 0, 7,        // 12
            11, 8, 12, 3, 2, 0, 7, 15,      // 13
            8, 2, 15, 11, 5, 9, 5, 5,       // 14
            12, 12, 14, 2, 3, 11, 9, 3      // 15
    };

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите сообщение:");
        String msg = sc.nextLine().toUpperCase();

        System.out.println("Введите ключ (32 бит):");
        String x0 = sc.nextLine().toUpperCase().substring(0, 4);

        msg = GeneralCrypto.stringToBinarySeq(msg);
        x0 = GeneralCrypto.stringToBinarySeq(x0);

        System.out.println("Исходное сообщение                            : " + msg);
        System.out.println("Подключ X0                                    : " + x0 + "\n");

        String l0 = msg.substring(0, 32);
        String r0 = msg.substring(32, 64);

        System.out.println("L                                             : " + l0);
        System.out.println("R                                             : " + r0 + "\n");

        String sum = sumR0andX(r0, x0);

        System.out.println("Сумма R и подключа X                          : " + sum + "\n");

        String tableConvString = tableConversion(sum);

        System.out.println("Преобразование в блоке подстановки            : " + tableConvString + "\n");

        String shiftedStr = shiftLeft11(tableConvString);

        System.out.println("Сдвинутая последовательность                  : " + shiftedStr + "\n");

        String result = GeneralCrypto.xorOperation(l0, shiftedStr, l0.length());

        System.out.println("Результат - сумма преобразования и L по mod2  : " + result);
    }

    // преобразование в блоке подстановки
    static String tableConversion(String str) {
        StringBuilder sb = new StringBuilder();
        String[] arrBites = GeneralCrypto.stringConversion(str, 4).split(" ");

        for (int i = 0; i < arrBites.length; i++) {
            int y = Integer.parseInt(arrBites[i], 2);
            int x = 8 - i;

            int numbInTable = getNumberFromTable(x, y);

            String result = Integer.toBinaryString(numbInTable);
            String resultWithPadding;

            if (result.length() < 4) {
                resultWithPadding = String.format("%4s", result).replaceAll(" ", "0");
            } else {
                resultWithPadding = result;
            }

            sb.append(resultWithPadding);
        }

        return sb.toString();
    }

    // получение элемента из таблицы согласно номерам строки и столбца
    static int getNumberFromTable(int x, int y) {
        int ind = y * 8 + (8 - x);
        return GOST28147_89.TABLE[ind];
    }

    static String sumR0andX(String r0, String x) {
        BigInteger first = new BigInteger(r0, 2);
        BigInteger second = new BigInteger(x, 2);
        BigInteger result = first.add(second);
        String str = result.toString(2);
        if (str.length() == 33) {
            str = str.substring(1);
        }
        return str;
    }

    static String shiftLeft11(String biteStr) {
        char[] a = biteStr.toCharArray();
        int length = a.length;
        char[] b = new char[length];
        System.arraycopy(a, 11, b, 0, length - 11);
        System.arraycopy(a, 0, b, length - 11, 11);
        return String.valueOf(b);
    }
}