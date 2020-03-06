package hw3;

import java.util.Arrays;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String[] words = {"aa", "bb", "cc", "dd", "ee", "aa", "aa", "bb", "cc", "gg", "hh", "ee"};
        System.out.println(Arrays.toString(words));

        HashMap<String, Integer> hmap = new HashMap<>();
        for (String x : words) {
            hmap.put(x, hmap.getOrDefault(x, 0) + 1);
        }
        System.out.println(hmap);


        Phone phBook = new Phone();
        phBook.add("Иванов", 654654654L);
        phBook.add("Иванов", 5645643454L);
        phBook.add("Петров", 5646452456L);
        phBook.add("Сидоров", 5546154661456L);
        phBook.add("Петров", 841564435454L);


        System.out.println("Тел:" + phBook.get("Иванов"));

    }

}
