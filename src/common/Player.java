package common;

import Server.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Server.Tile.Bag;

public class Player {
    private String name;
    private Map<Character, Tile> playersTiles;
    private int score;
    private Bag bag;

    public List<Tile> getPlayersTiles() {
        List<Tile> ts = new ArrayList<>();
        playersTiles.forEach((k,v)->ts.add(v));
        return ts;
    }

    public int getScore() {
        return score;
    }
    public Player(String name){
        this.playersTiles=new HashMap<>();
        this.name=name;
        this.score=0;
        this.bag = Tile.Bag.getBag();;
        completePlayersTiles();
    }
    public void completePlayersTiles(){
        while(playersTiles.size()!=7){
            Tile t = bag.getRand();
            playersTiles.put(t.letter, t);
        }
    }
    public void removeTiles(String word){
        for (char ch : word.toCharArray()){
            playersTiles.remove(ch);
        }
        completePlayersTiles();
    }
    public void updateScore(int i)
    {
        score+=i;
    }
}
