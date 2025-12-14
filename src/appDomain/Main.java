package appDomain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import implementations.FileAndLineWorkItemPrinter;
import implementations.FileWordItemPrinter;
import implementations.OccurrenceWordItemPrinter;
import utilities.Iterator;
import utilities.WordItemPrinter;

/**
 * Main class for the Word Tracker application.
 * It processes command line arguments, tracks words in files,
 * and outputs the results using specified formatting.
 */
public class Main {
    public static void main(String[] args) {
        WordTracker tracker = new WordTracker();
        if (args == null || args.length == 0) {
            System.out.println("No command line arguments provided. Using default settings.");
        }
        String[] fixedArgs = fixArgs(args != null ? args : new String[0]);
        MainInput input = parseArgs(fixedArgs);

        tracker.addWordsFromFile(input.getInputFilePath());
        // Serialize the word tree to save the state
        tracker.serializeWordTree();

        Iterator<WordItem> wordTrackerIterator = tracker.iterator();
        WordItemPrinter printer = input.getPrinter();

        if (input.getOutputFilePath() != null) {
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(input.getOutputFilePath(), false))) {
                while (wordTrackerIterator.hasNext()) {
                    WordItem item = wordTrackerIterator.next();
                    writer.write(printer.format(item));
                    System.out.print(printer.format(item));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            while (wordTrackerIterator.hasNext()) {
                WordItem item = wordTrackerIterator.next();
                System.out.print(printer.format(item));
            }
        }

    }

    public static String[] fixArgs(String[] args) {
        List<String> fixed = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (i + 1 < args.length && args[i + 1].equals(".txt")) {
                fixed.add(arg + args[i + 1]);
                i++;
            } else {
                fixed.add(arg);
            }
        }
        return fixed.toArray(new String[0]);
    }

    private static MainInput parseArgs(String[] args) {
        MainInput mainInput = new MainInput();
        mainInput.setInputFilePath(args[0]);

        for (int i = 1; i < args.length; i++) {
            String prefix = args[i].substring(0, 2);
            switch (prefix) {
                case "-p":
                    String printerType = args[i].substring(2);
                    switch (printerType) {
                        case "f":
                            mainInput.setPrinter(new FileWordItemPrinter());
                            break;
                        case "l":
                            mainInput.setPrinter(new FileAndLineWorkItemPrinter());
                            break;
                        case "o":
                            mainInput.setPrinter(new OccurrenceWordItemPrinter());
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown printer type: " + printerType);
                    }
                    break;
                case "-f":
                    mainInput.setOutputFilePath(args[i].substring(2));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + args[i]);
            }
        }

        return mainInput;
    }
}
