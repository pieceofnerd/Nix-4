package ua.com.alevel.util;

public class FilesConsoleUtil {

    private static final String file = "path-between-cities/src/main/java/ua/com/alevel/input.txt";
    private static final String result = "path-between-cities/src/main/java/ua/com/alevel/output.txt";

    public static void output() {
        String[] input = FileUtil.readFile(file);
        String[] output = FileUtil.readFile(result);
        System.out.println("Input.txt" +
                "\n------------------");
        assert input != null;
        for (String s : input) {
            System.out.println(s);
        }
        System.out.println("------------------");
        System.out.println("Output.txt" +
                "\n------------------");
        assert output != null;
        for (String s : output) {
            System.out.println(s);
        }
        System.out.println("------------------");

    }
}
