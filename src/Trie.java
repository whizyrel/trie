import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Trie {
    Node root = new Node(null);

    public List<String> findWords(String prefix) {
        List<String> words = new ArrayList<>();

        if (prefix == null)
            return words;

        Node lastCharNode = findLastNodeOf(prefix);

        if (lastCharNode == null)
            return words;

//        INFO Pre-order traversal
        findWords(prefix, lastCharNode, words);

        return words;
    }

    private void findWords(String prefix, Node root, List<String> words) {
        if (root.isEndOfWord)
            words.add(prefix);

        for (Node child : root.getChildren())
            if (child == null)
                return;
            else findWords(prefix + child.value, child, words);
    }

    private Node findLastNodeOf(String prefix) {
        if (prefix == null)
            return null;

        Node current = root;
        for (var ch : prefix.toCharArray())
            if (current.hasChild(ch))
                current = current.getChild(ch);
            else return null;

        return current;
    }

    public void remove(String word) {
        if (word == null)
            return;
        int index = 0;
        remove(word, root, index);
    }

    private void remove(String word, Node root, int index) {
        if (index == word.length()) {
            root.isEndOfWord = false;
            return;
        }

        char ch = word.charAt(index);

        if (!root.hasChild(ch))
            return;

        Node child = root.getChild(ch);
        remove(word, child, index + 1);

        if (!child.hasChildren() && !child.isEndOfWord)
            root.removeChild(ch);

        System.out.println("char -> " + ch);
//        wolverhampton university management in engineering
    }

    public boolean containsRecursive(String word) {
        if (word == null)
            return false;

        int index = 0;
        return containsRecursive(word, root, index);
    }

    private boolean containsRecursive(String word, Node root, int index) {
        if (word.length() == index)
            return root.isEndOfWord;
        char ch = word.charAt(index);
        Node child = root.getChild(ch);

        return containsRecursive(word, child, index + 1);
    }

    public boolean contains(String word) {
        if (word == null)
            return false;

        Node current = root;

        for (char ch : word.toCharArray()) {
            if (current.hasChild(ch)) {
                current = current.getChild(ch);
            } else {
                return false;
            }
        }

        return current.isEndOfWord;
    }

    public void insert(@NotNull String word) {
        Node current = root;
        for (char ch : word.toCharArray()) {
            if (current.hasChild(ch)) {
                current = current.getChild(ch);
            } else {
                Node newNode = new Node(ch);
                current.addChild(ch, newNode);
                current = newNode;
            }
        }

        current.isEndOfWord = true;
    }

    public ArrayList<String> prefixSearch(String searchStr, List<String> collection) {
        ArrayList<String> results = new ArrayList<>();

        if (searchStr == null || collection == null)
            return results;

        int top = 0;
        int bottom = collection.size() - 1;

        collection.sort(null);

        for (int i = 0; i < searchStr.toCharArray().length; i++) {
            char ch = searchStr.charAt(i);

//            INFO the top pointer has not crossed the bottom pointer && the character at index of the string at the top pointer is the same as the current character of the search string || the length of the string at the top pointer is less than the character at the current search string index
            while (top <= bottom && (collection.get(top).charAt(i) != ch || collection.get(top).length() <= i))
//                INFO we keep moving
                top += 1;

//            INFO the top pointer has not crossed the bottom pointer && the character at index of the string at the top pointer is the same as the current character of the search string || the length of the string at the top pointer is less than the character at the current search string index
            while (top <= bottom && (collection.get(bottom).charAt(i) != ch || collection.get(bottom).length() <= i))
//                INFO we keep moving
                bottom -= 1;
        }

        results.addAll(collection.subList(top, bottom + 1));

        return results;
    }

    public static class Node {
        public HashMap<Character, Node> children = new HashMap<>();
        public Character value;
        public Boolean isEndOfWord = false;

        Node(Character value) {
            this.value = value;
        }

        public void addChild(char ch, Node node) {
            children.put(ch, node);
        }

        public Collection<Node> getChildren() {
            return children.values();
        }

        public Node getChild(char ch) {
            return children.get(ch);
        }

        public boolean hasChild(char ch) {
            return children.containsKey(ch);
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

        public void removeChild(char ch) {
            children.remove(ch);
        }
    }
}
