package ViewModel;

import java.util.Observable;
import java.util.Observer;
import Model.scrabbleModel;
import Server.Board;
import Server.Tile;
import Server.Word;
import common.Player;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class scrabbleViewModel extends Observable implements Observer {
    private scrabbleModel scrabbleModel;
    public SimpleMapProperty<String, Player> playersProperty; //holds the score for each player

    public SimpleStringProperty playerName; //the current playing player
    private Word word; //holds current word in case of challenge

    public scrabbleViewModel(scrabbleModel scrabbleModel) {
        this.scrabbleModel = scrabbleModel;
        playersProperty = new SimpleMapProperty<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == scrabbleModel){
            //update current player score
            //adding -1 score means the word doesn't exist
            playersProperty.get(playerName.get()).updateScore(scrabbleModel.players.get(playerName.get()));
            playersProperty.get(playerName.get()).removeTiles(word.getWord());
        }
    }

    public void challenge (){
        scrabbleModel.CallengeServer(word, playerName.get());
    }

    public void query (){
        scrabbleModel.TryAddWordToBoard(word, playerName.get());
    }
    public void endGame(){
        scrabbleModel.finalizeGame();
    }

    private Word createWord() {
        //checks the boards data and creates new word
        //TODO: implement this function
        int col=0, row=0;
        boolean vertical = true;
        Tile[] ts = new Tile[4];
        return new Word(ts, row, col, vertical);
    }
}
