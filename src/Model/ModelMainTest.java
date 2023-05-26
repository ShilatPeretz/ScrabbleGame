package Model;

import Server.Board;
import Server.Tile;
import Server.Word;

public class ModelMainTest {
    public static void main(String[] args) {
        scrabbleModel scrabbleModel = new scrabbleModel();
        scrabbleModel.initializeGame();
        TestAddingWords(scrabbleModel);
        TestCallengingServer(scrabbleModel);
    }
    private static Tile[] get(String s) {
        Tile[] ts=new Tile[s.length()];
        int i=0;
        for(char c: s.toCharArray()) {
            ts[i]= Tile.Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }
    public static void TestAddingWords(scrabbleModel scrabbleModel){
        System.out.println("here");
        Word horn=new Word(get("HORN"), 7, 5, false);
        if(scrabbleModel.TryAddWordToBoard(horn)!=14)
            System.out.println("problem in placeWord for 1st word (-10)");

        Word farm=new Word(get("FA_M"), 5, 7, true);
        if(scrabbleModel.TryAddWordToBoard(farm)!=9)
            System.out.println("problem in placeWord for 2ed word (-10)");

        Word paste=new Word(get("PASTE"), 9, 5, false);
        if(scrabbleModel.TryAddWordToBoard(paste)!=25)
            System.out.println("problem in placeWord for 3ed word (-10)");

        Word mob=new Word(get("_OB"), 8, 7, false);
        if(scrabbleModel.TryAddWordToBoard(mob)!=18)
            System.out.println("mob point sould be 18");

        Word bit=new Word(get("BIT"), 10, 4, false);
        if(scrabbleModel.TryAddWordToBoard(bit)!=22)
            System.out.println("bitpoint should be 22 (-15)");

        Word sbta=new Word(get("S_TA"), 9, 4, true);
        if(scrabbleModel.TryAddWordToBoard(sbta)!=28)
            System.out.println("SBTA should be 28 (-15)");

        Word alone=new Word(get("A_ONE"), 11, 3, false);
        if(scrabbleModel.TryAddWordToBoard(alone)!=26)
            System.out.println("ATONE should be 26 (-15)");
    }
    public static void TestCallengingServer(scrabbleModel scrabbleModel){
        Word horn=new Word(get("THE"), 7, 5, false);
        if(scrabbleModel.CallengeServer(horn)!=28)
            System.out.println("problem in placeWord for 1st word (-10)");
    }
}
