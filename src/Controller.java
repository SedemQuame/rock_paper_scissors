import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;

public class Controller implements Initializable {
    //    Declaring game variables.
    private int numberOfTimesProgramWon, numberOfTimesHumanWon, totalPlays = 0;

    //    JavaFx Label Ui Element.
    @FXML
    private Label programScore;

    @FXML
    private Label humanScore;

    @FXML
    private Label drawMsg;

    @FXML
    private Label systemMessage;

    //    JavaFx Button Ui Element.
    @FXML
    private Button programRockOption;

    @FXML
    private Button programPaperOption;

    @FXML
    private Button programScissorsOption;

    @FXML
    private Button humanRockOption;

    @FXML
    private Button humanPaperOption;

    @FXML
    private Button humanScissorsOption;

    @FXML
    private Button playAgainBtn;

    //  Creating VBox Element For Ui
    @FXML
    private VBox noticeBoard;

    //  Creating ImageView Element For Ui
    @FXML
    private ImageView funnygifimage;

    //    On Action Clicked.
    @FXML
    void onOptionClicked(ActionEvent event) {
        drawMsg.setText("");
        totalPlays++;
        Button btn = (Button) event.getSource();

//        Declaring variables to hold human, and Programs choices.
        String ProgramsChoice, humansChoice = "";
        if (ProgramOption() == 0) {
            ProgramsChoice = "rock";
        } else if (ProgramOption() == 1) {
            ProgramsChoice = "paper";
        } else {
            ProgramsChoice = "scissors";
        }

//        Switch case to get the option, the human chooses.
        switch (btn.getId()) {
            case "humanRockOption":
                humansChoice = "rock";
                break;
            case "humanPaperOption":
                humansChoice = "paper";
                break;
            case "humanScissorsOption":
                humansChoice = "scissors";
                break;
        }

        predictWinner(ProgramsChoice, humansChoice);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void restartGame(ActionEvent event) {
        noticeBoard.setVisible(false);
        programScore.setText("0");
        humanScore.setText("0");
        drawMsg.setText("-");
    }

    private int ProgramOption() {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(3);
    }

    private void predictWinner(String ProgramsChoice, String humansChoice) {
        String errMsg = "Combination of choices is invalid.";
        String winner;

        if (ProgramsChoice.equals(humansChoice)) {
            winner = "draw";
        } else if (ProgramsChoice.equals("rock") && humansChoice.equals("paper")) {
            winner = "human";
        } else if (ProgramsChoice.equals("rock") && humansChoice.equals("scissors")) {
            winner = "Program";
        } else if (ProgramsChoice.equals("paper") && humansChoice.equals("rock")) {
            winner = "Program";
        } else if (ProgramsChoice.equals("paper") && humansChoice.equals("scissors")) {
            winner = "human";
        } else if (ProgramsChoice.equals("scissors") && humansChoice.equals("rock")) {
            winner = "human";
        } else if (ProgramsChoice.equals("scissors") && humansChoice.equals("paper")) {
            winner = "Program";
        } else {
            winner = "error";
        }

        // TODO: 15/08/2019
        //  Add sounds/audio clip to project to make it fun
        //  Animate Program and human choices.
        //  Introduce a 1s or 2s pause before printCongratulatoryMessage()

        switch (winner) {
            case "human":
                numberOfTimesHumanWon++;
                humanScore.setText(String.valueOf(numberOfTimesHumanWon));
                break;
            case "Program":
                numberOfTimesProgramWon++;
                programScore.setText(String.valueOf(numberOfTimesProgramWon));
                break;
            case "draw":
                drawMsg.setText("Draw");
                break;
            default:
                printMessage(errMsg);
                break;
        }

        if (totalPlays >= 10) {
            printCongratulatoryMessage();
            totalPlays = 0;
            numberOfTimesHumanWon = 0;
            numberOfTimesProgramWon = 0;
        }

        System.out.println(totalPlays);
    }

    private void printMessage(String msg) {
        systemMessage.setText(msg);
    }

    private void printCongratulatoryMessage() {
        if (numberOfTimesHumanWon > numberOfTimesProgramWon) {
            systemMessage.setText("Human Won.");
            //        funnygifimage.setImage("");
        } else {
            systemMessage.setText("Program Won.");
            //        funnygifimage.setImage("");
        }
        noticeBoard.setVisible(true);
    }

    private void playSound(String pathToAudioFile) {
        File file = new File(pathToAudioFile);
        URL url = null;
        if (file.canRead()) {
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }
}