package ua.com.alevel;

public class StringReverseUtil {
    public static String reverse(String src) {

        String stringWithoutDoubleSpaces = src
                .replaceAll("  +", " ")
                .trim();

        String[] words = stringWithoutDoubleSpaces.split(" ");
        String[] reverseWords = new String[words.length];
        String result;

        for (int j = 0; j < words.length; ++j) {
            char[] inputString = words[j].toCharArray();
            char[] outputString = new char[inputString.length];
            for (int i = 0; i < inputString.length; i++) {
                outputString[i] = inputString[inputString.length - i - 1];
            }
            reverseWords[j] = String.valueOf(outputString);
        }

        result = String.join(" ", reverseWords);
        return result;
    }

    public static String reverse(String src, String dest) {
        String stringWithoutDoubleSpaces = src
                .replaceAll("  +", " ")
                .trim();
        String substringWithoutDoubleSpaces = dest
                .replaceAll("  +", " ");
        char[] substring = substringWithoutDoubleSpaces.toCharArray();
        char[] reverseSubstring = new char[substringWithoutDoubleSpaces.length()];
        String result;

        for (int i = 0; i < substringWithoutDoubleSpaces.length(); i++) {
            reverseSubstring[i] = substring[substringWithoutDoubleSpaces.length() - i - 1];
        }
        String resultReverseSubstring = String.valueOf(reverseSubstring);
        if(resultReverseSubstring.isEmpty()){
            System.out.println("There is no substring in a source string");
        }
        result = stringWithoutDoubleSpaces.replace(substringWithoutDoubleSpaces, resultReverseSubstring);

        return result;
    }

    public static String reverse(String src, int firstIndex, int lastIndex) {
        String stringWithoutDoubleSpaces = src
                .replaceAll("  +", " ")
                .trim();
        char[] substring = new char[lastIndex - firstIndex];
        char[] reverseSubstring = new char[lastIndex - firstIndex];
        stringWithoutDoubleSpaces.getChars(firstIndex - 1, lastIndex - 1, substring, 0);
        for (int i = 0; i < substring.length; i++) {
            reverseSubstring[i] = substring[substring.length - i - 1];
        }
        String resultSubstring = String.valueOf(substring);
        String resultReverseSubstring = String.valueOf(reverseSubstring);
        String result = stringWithoutDoubleSpaces.replace(resultSubstring, resultReverseSubstring);
        return result;
    }

}
