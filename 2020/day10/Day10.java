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
        System.out.println("-----------------------------------------");
        long combs = countAdapterComb(input);
        System.out.println("Number of possible combinations: " + combs);
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
    }

    private static long countAdapterComb(List<Integer> input) {

        List<Integer> optionalList = new ArrayList<>();

        //Starting from 1 since the sorted list itself is a valid comb
        int optionals = 0;

        for (int i = 2; i < input.size(); i++) {

            int delta = input.get(i) - input.get(i - 2);
            if (delta == 3 || delta == 2) {
                optionals++;
            }

            optionalList.add(optionals);

            //
            //if (dirtyList.get(i - 2) + 3 > dirtyList.get(i - 1) && dirtyList.get(i - 2) + 3 >= dirtyList.get(i)) {
            //    dirtyList.remove(dirtyList.get(i - 1));
            //    count++;
            //
            //    for (int i = 2; i < dirtyList.size(); i++) {
            //        if (dirtyList.get(i - 2) + 3 > dirtyList.get(i - 1) && dirtyList.get(i - 2) + 3 >= dirtyList.get(i)) {
            //            dirtyList.remove(dirtyList.get(i - 1));
            //            count++;
            //        }
            //    }
            //}
        }

        long fact = 1;
        for (int i = 1; i <= optionals; i++) {
            fact*= i;
        }
        //return fact / (fact / optionals);

        fact = 1;
        for (int i = 0; i < optionalList.size(); i++) {
            fact *= 2;

        }
        
        fact -= 2;
        return fact;

        //return Math.pow(2, optionalList.size());
        //int sum = optionalList.stream().reduce(Integer::sum).get();
        //for (int i = 1; i <= optionalList.size(); i++) {
        //
        //}


    }
}
