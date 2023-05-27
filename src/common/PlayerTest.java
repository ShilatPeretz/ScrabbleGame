package common;

import Server.Tile;

public class PlayerTest {
    public static void testcompletePlayersTiles(){
        Tile.Bag b= Tile.Bag.getBag();
        Player p= new Player("anna",b);
        if(p.getPlayersTiles().size()!=7)
        {
            System.out.println("completePlayersTiles Is not working well!");
        }
    }
    public static void testRemoveTiles(){
        Tile.Bag bag= Tile.Bag.getBag();
        Player p= new Player("adi",bag);
        p.removeTiles("word",bag);
        for(Character c: "word".toCharArray()){
            if(p.getPlayersTiles().containsKey(c)){
                System.out.println("removeTiles is not working well 1!");
            }
        }
        p.removeTiles("test",bag);
        for(Character c: "test".toCharArray()){
            if(p.getPlayersTiles().containsKey(c)){
                System.out.println("removeTiles is not working well 2!");
            }
        }
    }
    public static void testUpdateScore()
    {
        Tile.Bag bag= Tile.Bag.getBag();
        Player p= new Player("amir",bag);
        int i=3;
        int score=p.getScore()+i;
        p.updateScore(i);
        if(score!=p.getScore())
            System.out.println("updateScore is not working well!");
    }
    public static void main(String[] args) {
        testcompletePlayersTiles();
        testRemoveTiles();
        testUpdateScore();
        System.out.println("Done!");
    }
}

