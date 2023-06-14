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
    private SimpleStringProperty wordInfo;
    public SimpleStringProperty playerName; //the current playing player
    private Word word; //holds current word in case of challenge

    public scrabbleViewModel(scrabbleModel scrabbleModel) {
        playersProperty = new SimpleMapProperty<>();
        this.scrabbleModel = scrabbleModel;
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
        scrabbleModel.TryAddWordToBoard(word,playerName.get());
    }
    public void endGame(){
        scrabbleModel.finalizeGame();
    }

    private Word createWord() {
        //TODO: implement this function
        String w=word.getWord()+" "+word.Get_row()+" "+word.Get_col()+" ";
        if(word.Get_vertical())
            w=w+"t";
        else
            w=w+"f";
        wordInfo.set(w);
        return new Word(word.Get_Tiles(),word.Get_row(), word.Get_col(), word.Get_vertical());
    }
}
