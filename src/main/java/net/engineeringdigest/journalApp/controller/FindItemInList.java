package net.engineeringdigest.journalApp.controller;
import java.util.List;
import java.util.Optional;

public class FindItemInList {

    public static void main(String[] args) {
        List<String> names = List.of("Alice", "Bob", "Charlie", "David");

        // Find first item matching a condition
        Optional<String> result = names.stream()
                .filter(name -> name.contentEquals("Alic"))
                .findFirst();

        if (result.isPresent()) {
            System.out.println("Found: " + result.get()); // Output: Found: Bob
        } else {
            System.out.println("Not found");
        }
    }
}