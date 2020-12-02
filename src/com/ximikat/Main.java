package com.ximikat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {

        List<String> dates = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader("input.txt"))) {

            String line;
            while ((line = input.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                dates.add(line);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try (BufferedWriter output = new BufferedWriter(new FileWriter("output.txt"))) {

            for (String date: dates) {
                output.write(date + ' ' + isValidDate(date) + '\n');
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    // YYYY/MM/DD years 1900-2020
    private static final Pattern DATE_PATTERN = Pattern.compile(

            // проще было бы уже распарсить эту дату

            "(?:" + // 29 февраля високосного года (1900-2020)
                    "(?:" + // год, кратный 4, кроме 1900
                        "19(?!00) ( [02468][048] | [13579][26] )" +
                            "|" + // (2000 високосный, так как кратен 400)
                        "20 ( 0[048] | 1[26]|20 )" +
                    ")" +
                    "/02/29" +
            "|" + // любая другая дата (не 02/29)
                    "(?: 19\\d{2} | 20(?: [01]\\d | 20 ))/" + // год между 1900 и 2000
                    "(?:" + // три варианта, в зависимости от кол-ва дней
                        "( 0[13578] | 1[02] ) / ( 0[1-9] | [12][0-9] | 3[01] ) |" + // 31
                        "( 0[469] | 11 ) / ( 0[1-9] | [12][0-9] | 30 ) |" + // 30
                        "02 / ( 0[1-9] | 1[1-9] | 2[1-8] )" + // невисокосный февраль
                    ")" +
            ")", Pattern.COMMENTS // <-- флаг позволяет расставить пробелы

    );

    private static boolean isValidDate(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

}
