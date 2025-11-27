package appDomain;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import implementations.BSTree;
import implementations.BSTreeNode;
import utilities.FileHelper;

public class WordTracker {
    private static final String REPOSITORY_FILE = "res/repository.ser";

    private BSTree<WordItem> wordTree;

    public WordTracker() {
        this.wordTree = new BSTree<>();
        restoreWordTree();
    }

    public void serializeWordTree() {
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream(REPOSITORY_FILE))) {
            oos.writeObject(this.wordTree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWordsFromFile(String filePath) {
        File inputFile = FileHelper.getFile(filePath);

        try (java.util.Scanner scanner = new java.util.Scanner(inputFile)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNumber++;
                String[] words = tokenize(line);
                for (String word : words) {
                    WordItem newItem = new WordItem(word, inputFile.getName(), lineNumber);
                    BSTreeNode<WordItem> existingNode = wordTree.search(newItem);
                    if (existingNode == null) {
                        wordTree.add(newItem);
                        continue;
                    }
                    existingNode.getElement().occursInFile(inputFile.getName(), lineNumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restoreWordTree() {
        File inputFile = new File(REPOSITORY_FILE);
        if (!inputFile.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REPOSITORY_FILE))) {
            this.wordTree = (BSTree<WordItem>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] tokenize(String line) {
        if (line == null || line.isEmpty()) {
            return new String[0];
        }
        line = line.replaceAll("['`]", "");
        line = line.replaceAll("[^a-z0-9\\s-]+", " ");

        String[] tokens = line.trim().split("\\s+");

        List<String> words = new ArrayList<>();
        for (String token : tokens) {
            if (!token.isEmpty()) {
                words.add(token);
            }
        }

        return words.toArray(new String[0]);
    }

}
