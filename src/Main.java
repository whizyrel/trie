import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> wordRepo = Arrays.asList("Dog", "Dogs", "Eve", "Israel", "Abigail", "Cat", "Cool", "Cow", "Cart", "Carl", "Zebra", "Israelite", "Dogfish", "King", "Queen");

        Trie trie = new Trie();

        wordRepo.forEach(trie::insert);
        trie.remove("Dog");

        System.out.println("[searchResult] contains \"''\" " + trie.contains(""));
        System.out.println("[searchResult] containsRecursive \"null\" " + trie.containsRecursive(null));
        System.out.println("[searchResult] containsRecursive \"Dog\" " + trie.containsRecursive("Dog"));
        System.out.println("[searchResult] containsRecursive \"Dogs\" " + trie.containsRecursive("Dogs"));
        System.out.println("[searchResult] contains \"Dogfish\" " + trie.contains("Dogfish"));

        System.out.println();
        System.out.println("== prefix search ==");
        System.out.println(trie.findWords("D"));
        System.out.println();

        List<String> lowerCaseList = wordRepo.stream().map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new));

        String searchStr = "C";

        ArrayList<String> results = trie.prefixSearch(searchStr.toLowerCase(), lowerCaseList);

        System.out.println("== prefix search variant ==");
        System.out.println(results.toString());
    }
}