package Server;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary {
    private CacheManager wordExist;
    private CacheManager wordNotExist;
    private BloomFilter bFilter;
    private String[] filesList;

    public Dictionary(String... filesList)
    {
        this.filesList = filesList;
        this.wordExist = new CacheManager(400, new LRU());
        this.wordNotExist = new CacheManager(100, new LFU());
        this.bFilter = new BloomFilter(256,"MD5","SHA1");
        initializeBloomFilter(filesList);
    }

    public boolean query(String word)
    {
        if(wordExist.query(word)){
            return true;
        }
        else if(wordNotExist.query(word)){
            return false;
        }
        else{
            return updateCache(word, bFilter.contains(word));
        }
    }

    private boolean updateCache(String word, boolean b)
    {
        if (b)
        {
            wordExist.add(word);
            return true;
        }
        else {
            wordNotExist.add(word);
            return false;
        }
    }

    public boolean challenge(String word)
    {
        try {
            return updateCache(word,IOSearcher.search(word, filesList));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeBloomFilter(String... filesList)
    {
        for(String fileName: filesList)
        {
            File file = new File(fileName);
            try {
                Scanner input = new Scanner(file);
                while (input.hasNext()) {
                    String word = input.next();
                    bFilter.add(word);
                }
                input.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
