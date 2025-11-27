package appDomain;

import java.util.ArrayList;
import java.util.HashMap;

public class WordItem implements Comparable<WordItem> {
    private String word;
    private HashMap<String, ArrayList<Integer>> wordOccurrences;

    public WordItem(String word, String fileName, Integer lineNumber) {
        this.word = word;
        this.wordOccurrences = new HashMap<>();
        ArrayList<Integer> lines = new ArrayList<>();
        lines.add(lineNumber);
        this.wordOccurrences.put(fileName, lines);
    }

    public String getWord() {
        return word;
    }

    public WordItem occursInFile(String fileName, Integer lineNumber) {
        if (wordOccurrences.containsKey(fileName)) {
            wordOccurrences.get(fileName).add(lineNumber);
        } else {
            ArrayList<Integer> lines = new ArrayList<>();
            lines.add(lineNumber);
            wordOccurrences.put(fileName, lines);
        }
        return this;
    }

    @Override
    public int compareTo(WordItem o) {
        return this.word.compareTo(o.getWord());
    }

    public int getTotalOccurrences() {
        int total = 0;
        for (ArrayList<Integer> lines : wordOccurrences.values()) {
            total += lines.size();
        }
        return total;
    }

}
