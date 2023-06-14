package ViewModel;

import java.util.Observable;
import java.util.Observer;
import Model.scrabbleModel;
import Server.Word;
import common.Player;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;

public class scrabbleViewModel extends Observable implements Observer {
    private scrabbleModel scrabbleModel;
    ///hghroghsvshhgrighpnf
    public SimpleMapProperty<String, Player> playersProperty;
    public SimpleStringProperty playerName;
    public SimpleIntProperty score;
    @Override
    public void update(Observable o, Object arg) {

    }
    public void queryServer(){
        //parse the string given, create a new word with given values
        //and check whether C\Q
    }
    private void challenge (){
        scrabbleModel.CallengeServer()
    }

    private void query (){
        scrabbleModel.CallengeServer()
    }
}
