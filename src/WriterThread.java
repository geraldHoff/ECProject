import javafx.concurrent.Task;

/**
 * Runs a new thread, where the OpusWriter will generate the word.
 * This is so that the GUI won't freeze.
 * Also, makes it insanely fast for some reason.
 */
class WriterThread extends Task<String> {

    String fileRoute;
    int num;

    WriterThread(String textField, int num){
        fileRoute = "src/texts/" + textField + ".txt";
        this.num = num;
    }

    @Override
    protected String call() throws Exception {
        OpusWriter writer = new OpusWriter(fileRoute, num);
        return writer.makeSentence();
    }
}
