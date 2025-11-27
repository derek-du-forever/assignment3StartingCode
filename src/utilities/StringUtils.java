package utilities;

public class StringUtils {
    public static String padLeft(String s, int totalLength, char padChar) {
        if (s == null)
            return null;
        if (s.length() >= totalLength)
            return s;

        int padCount = totalLength - s.length();
        StringBuilder sb = new StringBuilder(totalLength);
        for (int i = 0; i < padCount; i++) {
            sb.append(padChar);
        }
        sb.append(s);
        return sb.toString();
    }

    public static String padRight(String s, int totalLength, char padChar) {
        if (s == null)
            return null;
        if (s.length() >= totalLength)
            return s;

        int padCount = totalLength - s.length();
        StringBuilder sb = new StringBuilder(totalLength);
        sb.append(s);
        for (int i = 0; i < padCount; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    public static String normalizeDash(String input) {
        if (input == null)
            return null;

        String[] dashes = {
                "\u2013", // en dash
                "\u2014", // em dash
                "\u2015", // horizontal bar
                "\u2212", // minus sign
                "－" // fullwidth hyphen-minus
        };

        String result = input;
        for (String dash : dashes) {
            result = result.replace(dash, "-");
        }

        return result;
    }
}
