package Model;

import BookScrabbleServer.BookScrabbleServer;
import Server.Tile;
import Server.Word;

public class scrabbleModelTest {
    public static void main(String[] args) {
        scrabbleModel scrabbleModel = new scrabbleModel();
        scrabbleModel.initializeGame();
        TestAddingPlayers(scrabbleModel);
        TestAddingWords(scrabbleModel);
        TestCallengingServer(scrabbleModel);
        //scrabbleModel.finalizeGame();
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
        //this test might not get the given result since the tiles has been divided
        //betwwen the players and might not exist in the bag any more

        Word horn=new Word(get("HORN"), 7, 5, false);
        int score = scrabbleModel.TryAddWordToBoard(horn, "player1");
        if(score!=14 || scrabbleModel.players.get("player1").getScore()!=14)
            System.out.println("problem in placeWord for 1st word (-10)");

        Word farm=new Word(get("FA_M"), 5, 7, true);
        score = scrabbleModel.TryAddWordToBoard(farm, "player2");
        if(score!=9)
            System.out.println("problem in placeWord for 2ed word (-10)");

        Word paste=new Word(get("PASTE"), 9, 5, false);
        score = scrabbleModel.TryAddWordToBoard(paste, "player3");
        if(score!=25)
            System.out.println("problem in placeWord for 3ed word (-10)");

        System.out.println("done testing query");
    }
    public static void TestCallengingServer(scrabbleModel scrabbleModel){
        Word horn=new Word(get("IN"), 7, 5, false);
        int score = scrabbleModel.CallengeServer(horn, "player1");
        if(score!=28)
            System.out.println("problem in placeWord for 1st word (-10)");



        System.out.println("done testing challenge");
    }

    public static void TestAddingPlayers(scrabbleModel scrabbleModel){
        scrabbleModel.addPlayer("player1");
        scrabbleModel.addPlayer("player2");
        scrabbleModel.addPlayer("player3");
        scrabbleModel.addPlayer("player4");
        scrabbleModel.addPlayer("player5");
        int num = scrabbleModel.players.size();
        if(num != 4){
            System.out.println("you have problem with adding players");
        }
    }
}
