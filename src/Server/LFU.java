package Server;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;


public class LFU implements CacheReplacementPolicy {
    private final HashMap<String, Integer> lfuCache;
    private final HashMap<Integer, LinkedHashSet<String>> frequencies;
    private final TreeSet<Integer> frequenciesOrder;

    public LFU() {
        this.lfuCache = new HashMap<>();
        this.frequencies = new HashMap<>();
        this.frequenciesOrder = new TreeSet<>();
    }

    private void insert(String word, int frequency) {
        lfuCache.put(word, frequency);
        frequencies.putIfAbsent(frequency, new LinkedHashSet<>());
        frequencies.get(frequency).add(word);
        frequenciesOrder.add(frequency);
    }

    @Override
    public void add(String word) {
        if (lfuCache.containsKey(word)) {
            Integer frequency = lfuCache.get(word);
            LinkedHashSet<String> frequencyWords = frequencies.get(frequency);
            frequencyWords.remove(word);

            if (frequencyWords.isEmpty()) {
                frequenciesOrder.remove(frequency);
                frequencies.remove(frequency);
            }
            insert(word, frequency + 1);
        } else {
            insert(word, 1);
        }
    }

    @Override
    public String remove() {
        Integer minFrequency = frequenciesOrder.first();
        LinkedHashSet<String> leastFrequentWords = frequencies.get(minFrequency);
        String wordToRemove = leastFrequentWords.iterator().next();

        leastFrequentWords.remove(wordToRemove);
        lfuCache.remove(wordToRemove);

        if (leastFrequentWords.isEmpty()) {
            frequenciesOrder.remove(minFrequency);
            frequencies.remove(minFrequency);
        }
        return wordToRemove;
    }
}

