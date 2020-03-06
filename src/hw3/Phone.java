package hw3;


import java.util.HashMap;
import java.util.LinkedList;

public class Phone {
    private HashMap<String, LinkedList<Long>> phoneBook = new HashMap<>();

    public void add(String name, Long phone) {

        LinkedList<Long> tell = phoneBook.getOrDefault(name, new LinkedList<>());
        tell.add(phone);
        phoneBook.put(name, tell);


    }

    public LinkedList<Long> get(String name) {
        return phoneBook.get(name);

    }
}
