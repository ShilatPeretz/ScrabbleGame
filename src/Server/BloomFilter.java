package Server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class BloomFilter {
    private int size;
    private BitSet bitSet;
    List<MessageDigest> messageDigests;

    public BloomFilter(int size, String... hashFunctions) {
        this.size = size;
        this.bitSet = new BitSet();
        this.messageDigests = new ArrayList<>();
        for (String hash : hashFunctions) {
            try {
                this.messageDigests.add(MessageDigest.getInstance(hash));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getWordIndex(String word, MessageDigest md) {
        byte[] hashByteValue = md.digest(word.getBytes());
        BigInteger hashIntegerValue = new BigInteger(hashByteValue);
        return Math.abs(hashIntegerValue.intValue()) % size;
    }

    public void add(String word) {
        for (MessageDigest md : messageDigests) {
            int index = getWordIndex(word, md);
            bitSet.set(index);
        }
    }

    public boolean contains(String word) {
        for (MessageDigest md : messageDigests) {
            int index = getWordIndex(word, md);
            if (!bitSet.get(index)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return IntStream
                .range(0, bitSet.length())
                .mapToObj(i -> bitSet.get(i) ? '1' : '0')
                .collect(
                        () -> new StringBuilder(bitSet.length()),
                        StringBuilder::append,
                        StringBuilder::append
                )
                .toString();
    }
}
