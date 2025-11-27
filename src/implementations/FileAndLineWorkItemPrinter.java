package implementations;

public class FileAndLineWorkItemPrinter implements utilities.WordItemPrinter {
    @Override
    public String format(appDomain.WordItem wordItem) {
        StringBuilder sb = new StringBuilder();
        sb.append("===").append(wordItem.getWord()).append("=== ");
        boolean firstFile = true;
        for (String fileName : wordItem.getWordOccurrences().keySet()) {
            if (!firstFile) {
                sb.append(", ");
            } else {
                firstFile = false;
            }
            sb.append("found in file: ").append(fileName).append(" on lines: ");
            boolean firstLine = true;
            for (Integer lineNumber : wordItem.getWordOccurrences().get(fileName)) {
                if (!firstLine) {
                    sb.append(", ");
                } else {
                    firstLine = false;
                }
                sb.append(lineNumber);
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }

}
