/*-------------------*
 *  Author: Davoleo  *
 *-------------------*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {

    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            //scanner = new Scanner(new File("2020/Day-2/input.txt"));
            scanner = new Scanner(new File("input.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error Opening the specified file!");
            return;
        }

        List<PasswordModel> passwords = new ArrayList<>();

        while (scanner.hasNext()) {
            String range = scanner.next();
            String charColon = scanner.next();
            String password = scanner.next();

            int min = Integer.parseInt(range.split("-")[0]);
            int max = Integer.parseInt(range.split("-")[1]);
            char character = charColon.charAt(0);

            passwords.add(new PasswordModel(min, max, character, password));
        }

        long count = passwords.stream().filter(PasswordModel::isValid).count();
        long countV2 = passwords.stream().filter(PasswordModel::isValidV2).count();
        System.out.println("The number of valid passwords is: " + count);
        System.out.println("The number of valid passwords with the second policy is: " + countV2);
    }

    private static class PasswordModel {
        private int min;
        private int max;
        private char character;
        private String password;

        public PasswordModel(int min, int max, char character, String password) {
            this.min = min;
            this.max = max;
            this.character = character;
            this.password = password;
        }

        public boolean isValid() {
            int charCount = 0;
            for (char c : this.password.toCharArray()) {
                if (c == this.character)
                    charCount++;
            }

            return charCount >= min && charCount <= max;
        }

        public boolean isValidV2() {
            char[]  cArray = password.toCharArray();
            return (cArray[min - 1] == character) ^ (cArray[max - 1] == character);
        }
    }
}
