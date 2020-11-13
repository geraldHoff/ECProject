import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //calls start
        launch(args);
    }

    /**
     * Sets up the window.
     */
    @Override
    public void start(Stage window) throws Exception {

        //get paper image from resources, and pass it to an ImageView.
        Image aiBackground = new Image(
                getClass().getResource("resources/aiBackground.jpg").toString()
        );
        ImageView paperView = new ImageView(aiBackground);
        paperView.setPreserveRatio(true);


        //create textField so that the user can choose the text.
        TextField textField = new TextField();
        textField.setTranslateY(30);
        textField.setText("PP");

        Font font = Font.font("Verdana", FontWeight.BLACK, FontPosture.REGULAR, 20);

        //displays the newly created sentence when the button is clicked.
        Label sentence = new Label("");
        sentence.setTextFill(Color.SILVER);
        sentence.setFont(font);
        sentence.setTranslateY(520);
        sentence.setTranslateX(650);

        Label hashLabel = new Label("");
        hashLabel.setTextFill(Color.SILVER);
        hashLabel.setFont(font);
        hashLabel.setTranslateY(50);
        hashLabel.setTranslateX(700);

        //button for generating new sentence.
        Button startButton = new Button("new sentence");
        startButton.setLayoutY(60);
        startButton.setOnAction(e -> {

            try {
                //create new thread to do call the OpusWriter
                WriterThread writerThread = new WriterThread(textField.getText(), 5);
                String sentenceString = writerThread.call();

                //format the string so that it will fit on the screen
                sentence.setText(format(sentenceString));
                hashLabel.setText("");

            } catch (Exception exception) {
                //walter
                sentence.setText("walter");
                hashLabel.setText("");
            }
        });

        Button hashButton = new Button("show hashMap");
        hashButton.setLayoutY(90);
        hashButton.setOnAction(e -> {
            try {
                OpusWriter opusWriter = new OpusWriter("src/texts/" + textField.getText() + ".txt", 5);
                sentence.setText("");
                hashLabel.setText(opusWriter.getString().substring(0, 650));
            } catch (Exception exception) {}
        });

        //creates root node of graphics objects, than passes them to new scene.
        Group root = new Group(paperView, startButton, textField, sentence, hashButton, hashLabel);
        Scene scene = new Scene(root, 1200, 600);

        //window gets scene, and is shown.
        window.setMaximized(true);
        window.setScene(scene);
        window.setTitle("Grand Opus");
        window.show();
    }

    /**
     * Creates new lines in the string so that it can fit on the screen
     * @param sentenceString, The sentence, a String, to be formatted.
     * @return The formatted string.
     */
    String format(String sentenceString){
        //wraps String around with a new line to make it fit on the screen if too long.
        int sentenceLength = sentenceString.length();
        int spaceChar;
        if (sentenceLength > 50){
            spaceChar = 50;
            while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 60){
                spaceChar++;
            }
            sentenceString =
                    sentenceString.substring(0, spaceChar) + "\n   " + sentenceString.substring(spaceChar);
        }
        if (sentenceLength > 100){
            spaceChar = 100;
            while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 110){
                spaceChar++;
            }
            sentenceString =
                    sentenceString.substring(0, spaceChar) + "\n   " + sentenceString.substring(spaceChar);
        }
        if (sentenceLength > 150){
            spaceChar = 150;
            while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 160){
                spaceChar++;
            }
            sentenceString =
                    sentenceString.substring(0, spaceChar) + "\n   " + sentenceString.substring(spaceChar);
        }
        if (sentenceLength > 200){
            spaceChar = 200;
            while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 210){
                spaceChar++;
            }
            sentenceString =
                    sentenceString.substring(0, spaceChar) + "\n   " + sentenceString.substring(spaceChar);
        }
        if (sentenceLength > 250){
            spaceChar = 250;
            while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 260){
                spaceChar++;
            }
            sentenceString =
                    sentenceString.substring(0, spaceChar) + ".";
        }
        return characterize(sentenceString);
    }

    /**
     * Removes all annoying characters at start of string,
     * and capitalizes the first letter.
     * @param sentence The sentence.
     * @return The sentence butt better.
     */
    static String characterize(String sentence){
        while (!Character.isLetter(sentence.charAt(0))){
            sentence = sentence.substring(1);
        }
        sentence = Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1);
        return sentence;
    }
}


