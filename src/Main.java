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

        Label hashLabel = new Label("HollyWood");
        hashLabel.setFont(font);
        hashLabel.setTextFill(Color.SILVER);
        hashLabel.setTranslateY(90);
        hashLabel.setTranslateX(10);

        //button for generating new sentence.
        Button startButton = new Button("new sentence");
        startButton.setLayoutY(60);
        startButton.setOnAction(e -> {
            try {
                //gets file based on the input of the textField.
                OpusWriter writer = new OpusWriter("src/texts/" + textField.getText() + ".txt", 5);
                String sentenceString = writer.makeSentence();

                //wraps String around with a new line to make it fit on the screen if too long.
                if (sentenceString.length() > 50){
                    int spaceChar = 50;
                    while(sentenceString.charAt(spaceChar) != ' ' || spaceChar > 60){
                        spaceChar++;
                    }
                    sentenceString =
                            sentenceString.substring(0, spaceChar) + "\n" + sentenceString.substring(spaceChar);
                }
                sentence.setText(sentenceString);
                hashLabel.setText(writer.getString());
            }
            catch(Exception exception){
                sentence.setText("walter");
            }
        });

        //creates root node of graphics objects, than passes them to new scene.
        Group root = new Group(paperView, startButton, textField, sentence, hashLabel);
        Scene scene = new Scene(root, 1200, 600);

        //window gets scene, and is shown.
        window.setMaximized(true);
        window.setScene(scene);
        window.setTitle("Grand Opus");
        window.show();
    }
}
