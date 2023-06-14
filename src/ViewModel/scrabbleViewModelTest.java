package ViewModel;

import Model.scrabbleModel;

public class scrabbleViewModelTest {
    public static void main(String[] args) {
        scrabbleModel m = new scrabbleModel();
        AddingPlayers(m);
        scrabbleViewModel vm = new scrabbleViewModel(m);
        m.addObserver(vm);

        TestAddingWords(vm);
        TestChallenge(vm);
        //scrabbleModel.finalizeGame();
    }

    private static void TestChallenge(scrabbleViewModel vm) {
    }

    private static void TestAddingWords(scrabbleViewModel vm) {
        vm.playerName.set("player1");

        vm.query();
    }

    public static void AddingPlayers(scrabbleModel scrabbleModel){
        scrabbleModel.addPlayer("player1");
        scrabbleModel.addPlayer("player2");
        scrabbleModel.addPlayer("player3");
        scrabbleModel.addPlayer("player4");
        scrabbleModel.addPlayer("player5");

    }
}
