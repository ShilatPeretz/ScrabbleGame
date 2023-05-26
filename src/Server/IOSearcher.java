package Server;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOSearcher {
    public static boolean search(String word, String... filesList) {
        for (String fileName : filesList) {
            if(searchInFile(word, fileName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean searchInFile(String word, String fileName)
    {
        File file = new File(fileName);
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                if (word.equals(input.next()))
                    return true;
            }
            input.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
