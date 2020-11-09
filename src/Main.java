import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        Image paperImage = new Image(
                getClass().getResource("resources/paper.jpg").toString()
        );
        ImageView paperView = new ImageView(paperImage);
        paperView.setPreserveRatio(true);
        paperView.setScaleX(0.5);
        paperView.setScaleY(0.5);
        paperView.setX(-593);
        paperView.setY(-395);

        //create textField so that the user can choose the text.
        TextField textField = new TextField();
        textField.setTranslateY(30);
        textField.setText("text name");

        //displays the newly created sentence when the button is clicked.
        Label output = new Label("");
        output.setLayoutY(200);
        output.setLayoutX(300);

        //button for generating new sentence.
        Button startButton = new Button("new sentence");
        startButton.setLayoutY(60);
        startButton.setOnAction(e -> {
            try {
                //gets file based on the input of the textField.
                OpusWriter writer = new OpusWriter("src/texts/" + textField.getText() + ".txt", 5);
                output.setText(writer.makeSentence());
            }
            catch(Exception exception){
                System.out.println("File not found, probably");
            }
        });

        //creates root node of graphics objects, than passes them to new scene.
        Group root = new Group(paperView, startButton, textField, output);
        Scene scene = new Scene(root, 1200, 600);

        //window gets scene, and is shown.
        window.setScene(scene);
        window.setTitle("Grand Opus");
        window.show();
    }
}
