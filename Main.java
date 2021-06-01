import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane p = new FlowPane();

        Scene scene = new Scene(p, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        Media m = new Media(new File("target.mp4").toURI().toString());

        MediaPlayer mp = new MediaPlayer(m);

        MediaView view = new MediaView(mp);
        p.getChildren().add(view);

        mp.setAutoPlay(true);

        /*Button startButton = new Button("Start");
        startButton.setOnAction(event -> {
            mp.play();

        });
        p.getChildren().add(startButton);

        Button stopButton = new Button("Stop");
        startButton.setOnAction(event -> {
            mp.stop();

        });
        p.getChildren().add(stopButton);
        */



        /*Timer time = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(mp.getCurrentTime());
            }
        };
        time.scheduleAtFixedRate(task, 0, 5000);*/

        AtomicInteger count = new AtomicInteger(0);
        AtomicBoolean isPushed = new AtomicBoolean(false);
        AtomicReference<String> bTime = new AtomicReference<>("");
        Button printButton = new Button("Print Current Time[0]");
        printButton.setOnAction(event -> {
            if(isPushed.get()) {

                System.out.println(count.incrementAndGet());
                System.out.println(bTime + " --> " + convert(mp.getCurrentTime()));
                System.out.println();

                bTime.set("");
                isPushed.set(false);
                printButton.setText("Print Current Time[0]");
            }else{
                bTime.updateAndGet(v -> convert(mp.getCurrentTime()));
                isPushed.set(true);
                printButton.setText("Print Current Time[1]");
            }
        });
        p.getChildren().add(printButton);
    }

    private String convert(Duration t) {
        return convert2digit((int)(t.toHours())) + ":" + convert2digit((int)(t.toMinutes() % 60)) + ":" + convert2digit((int)(t.toSeconds() % 60)) + "," + convert3digit((int)(t.toMillis() % 1000));
    }

    private String convert2digit(int target) {
        if(target >= 10) return String.valueOf(target);
        else return "0" + target;
    }

    private String convert3digit(int target) {
        if(target >= 100) return String.valueOf(target);
        else if(target >= 10) return "0" + target;
        return "00" + target;
    }
}