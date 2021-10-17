package tasks.task1;

import tasks.GeneralCrypto;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class DES {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите сообщение (8 бит):");
        String msg = sc.nextLine().toUpperCase();

        System.out.println("Введите подключ:");
        String key = sc.nextLine().toUpperCase();

        msg = GeneralCrypto.stringToBinarySeq(msg);
        key = GeneralCrypto.stringToBinarySeq(key);

        System.out.println("Исходное сообщение                       : " + msg);
        System.out.println("Исходный ключ                            : " + key);

        String firstPermutation = permutation(msg, Table.INITIAL_PERMUTATION);

        System.out.println("Сообщение после перестановки             : " + firstPermutation + "\n");

        String l0 = firstPermutation.substring(0,32);
        String r0 = firstPermutation.substring(32,64);

        System.out.println("L                                        : " + l0);
        System.out.println("R                                        : " + r0 + "\n");

        String r0Ext = permutation(r0, Table.EXTENSION);

        System.out.println("R рассширенный                           : " + r0Ext);

        String keyConv = keyConversion(key);

        System.out.println("Измененный ключ                          : " + keyConv);

        String xor = GeneralCrypto.xorOperation(r0Ext, keyConv, r0Ext.length());

        System.out.println("Сумирование                              : " + xor + "\n");

        String stringAfterSTrans = permutationViaSTables(xor);

        System.out.println("После S1-S8 перестановки                 : " + stringAfterSTrans);

        String stringAfterPTrans = permutation(stringAfterSTrans, Table.P_TRANSFORMATION);

        System.out.println("После P перестановки                     : " + stringAfterPTrans + "\n");

        String sum = l0 + stringAfterPTrans;

        System.out.println("Сумма строки после перстановки и L       : " + sum);

        String result = permutation(sum, Table.LAST_PERMUTATION);

        System.out.println("Результат после последней перестановки   : " + result);
    }

    // перестановка согласно таблице
    static String permutation(String message, int[] table) {
        char[] messageArr = message.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int t : table)
            result.append(messageArr[t - 1]);
        return result.toString();
    }

    // преобразование ключа
    static String keyConversion(String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        for (int j = 0; j < 2; j++) {
            for (int i = 1; i < sb.length(); i++) {
                if (i % 7 == 0) {
                    sb.deleteCharAt(i);
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    // преобразование согласно S1-S8 таблицам
    static String permutationViaSTables(String r0XorKey) {
        StringBuilder sb = new StringBuilder();
        String[] bites = GeneralCrypto.stringConversion(r0XorKey, 6).split(" ");

        for (int i = 0; i < bites.length; i++) {
            int lastIndex = 5;
            String twoBite = bites[i].charAt(0) + bites[i].substring(lastIndex);
            String fourBite = bites[i].substring(1, 5);

            int x = Integer.parseInt(twoBite, 2);
            int y = Integer.parseInt(fourBite, 2);

            int numbInTable = getNumberFromSTable(x, y, i + 1);

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

    static int[] switchTable(int numb) {
        switch (numb) {
            case 1:
                return Table.S1;
            case 2:
                return Table.S2;
            case 3:
                return Table.S3;
            case 4:
                return Table.S4;
            case 5:
                return Table.S5;
            case 6:
                return Table.S6;
            case 7:
                return Table.S7;
            case 8:
                return Table.S8;
            default:
                return null;
        }
    }

    // получение элемента из S таблицы согласно номерам строки и столбца
    static int getNumberFromSTable(int x, int y, int tableNumber) {
        int ind = x * 16 + y;
        int[] table = switchTable(tableNumber);
        return table != null ? table[ind] : 0;
    }
}
