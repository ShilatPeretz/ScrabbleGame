package common;

import Server.Tile;

public class PlayerTest {
    public static void testcompletePlayersTiles(){
        Tile.Bag b= Tile.Bag.getBag();
        Player p= new Player("anna");
        if(p.getPlayersTiles().size()!=7)
        {
            System.out.println("completePlayersTiles Is not working well!");
        }
    }
    public static void testRemoveTiles(){
        Tile.Bag bag= Tile.Bag.getBag();
        Player p= new Player("adi");
        p.removeTiles("word");
        int i=0;
        for(Character c: "word".toCharArray()){
            if(p.getPlayersTiles().get(i).letter == c){
                System.out.println("removeTiles is not working well 1!");
            }
        }
        p.removeTiles("test");
        for(Character c: "test".toCharArray()){
            if(p.getPlayersTiles().get(i).letter == c){
                System.out.println("removeTiles is not working well 2!");
            }
        }
    }
    public static void testUpdateScore()
    {
        Tile.Bag bag= Tile.Bag.getBag();
        Player p= new Player("amir");
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

