package ViewModel;

import java.util.Observable;
import java.util.Observer;
import Model.scrabbleModel;
import Server.Word;
import common.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class scrabbleViewModel extends Observable {
    private scrabbleModel scrabbleModel;
    public SimpleMapProperty<String, Player> playersProperty;
    public SimpleStringProperty playerName;
    public SimpleIntegerProperty score;

    public void addPlayer(){
        scrabbleModel.addPlayer(playerName.get());
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o==scrabbleModel)
        {
            playerName.set(scrabbleModel.getPlayers().keySet().toArray()[0].toString());
        }
    }
    public void queryServer(){
        //parse the string given, create a new word with given values
        //and check whether C\Q
    }
    private void challenge (){
        scrabbleModel.CallengeServer()
    }

    private void query (){
        scrabbleModel.CallengeServer();
    }
}
