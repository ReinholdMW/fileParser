package FileParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class main {

    public static void main(String[] args) throws IOException {

        String invalidWords = readFile(args[0]);

        String fileToMask = readFile(args[1]);

        List<String> phrasesToReplace = parseInputString(invalidWords);

        saveFile(parsePhrase(phrasesToReplace, fileToMask));
    }

    public static String readFile(String path) throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public static void saveFile(String maskedText) {

        System.out.println(maskedText);

        try (PrintWriter out = new PrintWriter("output.txt")) {
            out.println(maskedText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<String> parseInputString(String invalidWords) {

        String badWords[] = invalidWords.replace(",", "").split("\"");
        List<String> parsedInput = new ArrayList<>();

        for (String badWord : badWords) {
            if (!invalidWords.contains("\"" + badWord + "\"")) {
                String worseWords[] = badWord.split("'");
                for (String worseWord : worseWords) {
                    if (!invalidWords.contains("'" + worseWord + "'")) {
                        String worstWords[] = worseWord.split(" ");
                        parsedInput.addAll(Arrays.asList(worstWords));
                    } else {
                        parsedInput.add(worseWord);
                    }
                }
            } else {
                parsedInput.add(badWord);
            }
        }

        parsedInput.remove("");
        return parsedInput;
    }

    public static String parsePhrase(List<String> phrasesToReplace, String inputString) {

        String outputString = inputString;

        for (String phrase : phrasesToReplace) {
            outputString = outputString.replaceAll(phrase, "xxxx");
        }

        return outputString;
    }

}
