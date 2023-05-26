package Server;

import java.util.LinkedHashSet;

public class LRU implements CacheReplacementPolicy {
    LinkedHashSet<String> lruCache;

    public LRU() {
        this.lruCache = new LinkedHashSet<>();
    }

    @Override
    public void add(String word) {
        if (lruCache.contains(word)) {
            lruCache.remove(word);
        }
        lruCache.add(word);
    }

    @Override
    public String remove() {
        String oldestKey = lruCache.iterator().next();
        lruCache.remove(oldestKey);
        return oldestKey;
    }
}
