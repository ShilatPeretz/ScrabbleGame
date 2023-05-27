package Server;


import java.util.Arrays;
import java.util.HashMap;

public class DictionaryManager {
    private HashMap<String, Dictionary> dictionaryHashMap = new HashMap<String, Server.Dictionary>();
    private static DictionaryManager _instance = null;

    public static DictionaryManager get(){
        if(_instance==null)
        {
            _instance=new DictionaryManager();
        }
        return _instance;
    }
    public int getSize(){
        return this.dictionaryHashMap.size();
    }

    public boolean challenge(String... books){
        String[] AllBooks = Arrays.copyOf(books, books.length-1);
        addAllBooksToDictionary(AllBooks);
        boolean flag = false;
        String word = books[books.length-1];
        for(String book: AllBooks){
            if(dictionaryHashMap.get(book).challenge(word))
                flag=true;
        }
        return flag;
    }

    public boolean query(String... books){
        String[] AllBooks = Arrays.copyOf(books, books.length-1);
        addAllBooksToDictionary(AllBooks);
        boolean flag = false;
        String word = books[books.length-1];
        for(String book: AllBooks){
            if(dictionaryHashMap.get(book).query(word))
                flag=true;
        }
        return flag;
    }

    private void addAllBooksToDictionary(String... books) {
        for (String book:books) {
            if(!dictionaryHashMap.containsKey(book)){
                dictionaryHashMap.put(book, new Server.Dictionary(book));
            }
        }
    }

}
