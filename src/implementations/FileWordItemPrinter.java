package implementations;

public class FileWordItemPrinter implements utilities.WordItemPrinter {
    @Override
    public String format(appDomain.WordItem wordItem) {
        StringBuilder sb = new StringBuilder();
        sb.append("===").append(wordItem.getWord()).append("=== found in file: ");
        boolean first = true;
        for (String fileName : wordItem.getWordOccurrences().keySet()) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            sb.append(fileName);
        }
        sb.append("\r\n");
        return sb.toString();
    }

}
