import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
        playSound();

        Button btn = (Button) event.getSource();
//        Animating the buttons.
//        Getting button position.



        drawMsg.setText("");
        totalPlays++;
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
        animateButton(btn, ProgramsChoice,  humansChoice);
        predictWinner(ProgramsChoice, humansChoice);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void restartGame() {
        noticeBoard.setVisible(false);
        programScore.setText("0");
        humanScore.setText("0");
        drawMsg.setText("-");

        programRockOption.setDisable(false);
        programPaperOption.setDisable(false);
        programScissorsOption.setDisable(false);

        humanRockOption.setDisable(false);
        humanPaperOption.setDisable(false);
        humanScissorsOption.setDisable(false);
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
        programRockOption.setDisable(true);
        programPaperOption.setDisable(true);
        programScissorsOption.setDisable(true);

        humanRockOption.setDisable(true);
        humanPaperOption.setDisable(true);
        humanScissorsOption.setDisable(true);

        if (numberOfTimesHumanWon > numberOfTimesProgramWon) {
            systemMessage.setText("Human Won.");
            //        funnygifimage.setImage("");
        } else {
            systemMessage.setText("Program Won.");
            //        funnygifimage.setImage("");
        }
        noticeBoard.setVisible(true);
    }

    private void playSound() {
        File file = new File("C:\\Users\\Quame\\Documents\\projects\\rock_paper_scissors\\assets\\audio_files\\rps_sound.wav");
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

    private void animateButton(Button btn, String ProgramsChoice, String humansChoice){
        Duration duration = Duration.millis(2500);
        Button dummyBtn =null;

        /*Animating the computer's choice.*/
        TranslateTransition animateProgramsChoice = null;
        switch (ProgramsChoice) {
            case "rock":
                animateProgramsChoice = new TranslateTransition(duration, programRockOption);
                dummyBtn = programRockOption;
                animateProgramsChoice.setByY(90);
                animateProgramsChoice.setByX(180);
                break;
            case "paper":
                animateProgramsChoice = new TranslateTransition(duration, programPaperOption);
                dummyBtn = programPaperOption;
                animateProgramsChoice.setByX(180);
                break;
            case "scissors":
                animateProgramsChoice = new TranslateTransition(duration, programScissorsOption);
                dummyBtn = programScissorsOption;
                animateProgramsChoice.setByY(-90);
                animateProgramsChoice.setByX(180);
                break;
        }
        assert animateProgramsChoice != null;
        animateProgramsChoice.setAutoReverse(true);
        animateProgramsChoice.setCycleCount(2);
        animateProgramsChoice.play();
        scalingAnimation(duration, dummyBtn);


        /*Animating the human's choice.*/
        TranslateTransition animateHumansChoice = new TranslateTransition(duration, btn);

        switch (humansChoice) {
            case "rock":
                animateHumansChoice.setByY(90);
                animateHumansChoice.setByX(-180);
                break;
            case "paper":
                animateHumansChoice.setByX(-180);
                break;
            case "scissors":
                animateHumansChoice.setByY(-90);
                animateHumansChoice.setByX(-180);
                break;
        }
        animateHumansChoice.setAutoReverse(true);
        animateHumansChoice.setCycleCount(2);
        animateHumansChoice.play();
        scalingAnimation(duration, btn);
    }

    private void scalingAnimation(Duration duration, Button btn){
        ScaleTransition scaleTransition = new ScaleTransition(duration, btn);
        scaleTransition.setByX(0.45);
        scaleTransition.setByY(0.45);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }
}