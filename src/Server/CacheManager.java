package Server;


import java.util.HashSet;

public class CacheManager {
    private HashSet<String> cache;
    private int maxCacheSize;
    private CacheReplacementPolicy cachePolicy;


    public CacheManager(int size, CacheReplacementPolicy crp) {
        this.cache = new HashSet<>();
        this.maxCacheSize = size;
        this.cachePolicy = crp;
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        cachePolicy.add(word);
        cache.add(word);
        if (maxCacheSize < cache.size()) {
            String wordRemoved = cachePolicy.remove();
            cache.remove(wordRemoved);
        }
    }
}
