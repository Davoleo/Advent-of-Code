package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day10 {

    public static void main(String[] args) {

        Scanner scanner;

        try {
            scanner = new Scanner(new File("2020/day10/input.txt"));
            //scanner = new Scanner(new File("input.txt"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error Opening the specified file!");
            return;
        }

        List<Integer> input = new ArrayList<>();

        input.add(0);
        while (scanner.hasNext()) {
            input.add(scanner.nextInt());
        }
        Collections.sort(input);
        int max = input.get(input.size() - 1) + 3;
        input.add(max);

        countJoltNum(input);
    }

    private static void countJoltNum(List<Integer> input) {
        int jolt1 = 0;
        int jolt3 = 0;

        for (int i = 1; i < input.size(); i++) {
            int result = input.get(i) - input.get(i - 1);

            if(result == 3) {
                jolt3++;
            }
            else if(result == 1) {
                jolt1++;
            }
        }

        System.out.println("The number of 3 step jolts is: " + jolt3);
        System.out.println("The number of 1 step jolts is: " + jolt1);
        System.out.println("Their value multiplied is: " + jolt1 * jolt3);

        int combs = countAdapterComb(input);
        System.out.println("Number of possible combinations: " + combs);
    }

    private static int countAdapterComb(List<Integer> input) {

        List<Integer> dirtyList = new ArrayList<>(input.size());

        //Starting from 1 since the sorted list itself is a valid comb
        int count = 1;

        for (int offset = 2; offset < input.size(); offset++) {
            Collections.copy(dirtyList, input);

            if (dirtyList.get(offset - 2) + 3 > dirtyList.get(offset - 1) && dirtyList.get(offset - 2) + 3 >= dirtyList.get(offset)) {
                dirtyList.remove(dirtyList.get(offset - 1));
                count++;

                for (int i = 2; i < dirtyList.size(); i++) {
                    if (dirtyList.get(offset - 2) + 3 > dirtyList.get(offset - 1) && dirtyList.get(offset - 2) + 3 >= dirtyList.get(offset)) {
                        dirtyList.remove(dirtyList.get(offset - 1));
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
