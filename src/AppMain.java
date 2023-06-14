import BookScrabbleServer.BookScrabbleServer;
import Model.scrabbleModel;
import ViewModel.scrabbleViewModel;
import sun.net.www.ApplicationLaunchException;


public class AppMain extends ApplicationLaunchException {
    public AppMain(String reason) {
        super(reason);
    }

    public void Start() {
        //satrting the server
        BookScrabbleServer bookScrabbleServer = new BookScrabbleServer();
        bookScrabbleServer.start();

        //initializing the model
        scrabbleModel model = new scrabbleModel();

        //initializing the view model
        scrabbleViewModel viewModel = new scrabbleViewModel(model);

        //connecting the 2
        model.addObserver(viewModel);


        //.....
    }
}
