package appDomain;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Class representing a word and its occurrences in files and line numbers.
 */
public class WordItem implements Comparable<WordItem>, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String word;
    private HashMap<String, TreeSet<Integer>> wordOccurrences;

    public WordItem(String word, String fileName, Integer lineNumber) {
        this.word = word;
        this.wordOccurrences = new HashMap<>();
        TreeSet<Integer> lines = new TreeSet<>();
        lines.add(lineNumber);
        this.wordOccurrences.put(fileName, lines);
    }

    public String getWord() {
        return word;
    }

    public HashMap<String, TreeSet<Integer>> getWordOccurrences() {
        return wordOccurrences;
    }

    public WordItem occursInFile(String fileName, Integer lineNumber) {
        if (wordOccurrences.containsKey(fileName)) {
            wordOccurrences.get(fileName).add(lineNumber);
        } else {
            TreeSet<Integer> lines = new TreeSet<>();
            lines.add(lineNumber);
            wordOccurrences.put(fileName, lines);
        }
        return this;
    }

    @Override
    public int compareTo(WordItem o) {
        return this.word.toLowerCase().compareTo(o.getWord().toLowerCase());
    }

    public int getTotalOccurrences() {
        int total = 0;
        for (TreeSet<Integer> lines : wordOccurrences.values()) {
            total += lines.size();
        }
        return total;
    }

}
