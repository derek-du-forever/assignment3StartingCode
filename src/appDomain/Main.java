package appDomain;

import java.util.ArrayList;
import java.util.List;

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
                    mainInput.setDisplayFormat(args[i].substring(2));
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
