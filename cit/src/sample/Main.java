package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import java.util.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {
    Label text,workoutName,workoutTime;
    TextField nameField,workoutField;
    Button addWorkout,addRest,startButton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        class unitWorkout {
            String name;
            double time;

            public unitWorkout(String name, double time) {
                this.name = name;
                this.time = time;
            }

            public String toString() {
                String result = "";
                result += name + "\n" + time + "sec\n";
                return result;
            }
        }
        String musicFile = "timer.mp3";     // For example

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

      String musicFile1 = "timerend.wav";     // For example

       Media sound1 = new Media(new File(musicFile1).toURI().toString());
         MediaPlayer mediaPlayer1 = new MediaPlayer(sound1);

        text = new Label("Circuit Interval Timer");
        workoutName = new Label("WORKOUT NAME: ");
        ArrayList<unitWorkout> workoutsList=new ArrayList<unitWorkout>();
        ListView<unitWorkout> workouts = new ListView<unitWorkout>();
        nameField = new TextField();
        workoutTime = new Label("TIME: ");
        workoutField = new TextField();
        addWorkout = new Button("Add Workout");
        addWorkout.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
        double time=Double.parseDouble(workoutField.getText());
        workoutsList.add(new unitWorkout(nameField.getText(),time));
            workouts.getItems().clear();
            workouts.refresh();
        for (int i=0;i<workoutsList.size();i++)
        {
            workouts.getItems().add(workoutsList.get(i));
        }
         }
         });
        addRest = new Button("Add Rest");
        addRest.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                double time=Double.parseDouble(workoutField.getText());
                workoutsList.add((new unitWorkout("REST",time)));
                workouts.getItems().clear();
                workouts.refresh();
                for (int i=0;i<workoutsList.size();i++)
                {
                    workouts.getItems().add(workoutsList.get(i));
                }
            }
        });
        startButton = new Button("Start ");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (workoutsList.size()==1 && workoutsList.get(0).time==1)
                        {
                            text.setText("over!");
                            mediaPlayer1.play();
                            workouts.getItems().clear();
                            return;
                        }
                        workoutsList.get(0).time -= 1;
                        if (workoutsList.get(0).time == 0) {
                            workoutsList.remove(0);
                            mediaPlayer.play();
                            return;

                        }

                        workouts.getItems().

                                clear();
                        workouts.refresh();
                        for (int i = 0; i < workoutsList.size(); i++) {
                            workouts.getItems().add(workoutsList.get(i));
                        }
                    }
                }, 0, 1000);
                }
        });
        BorderPane root = new BorderPane();
        GridPane bottomPane = new GridPane();
        bottomPane.add(workoutName, 0, 0);
        bottomPane.add(nameField, 1, 0);
        bottomPane.add(workoutTime, 0, 1);
        bottomPane.add(workoutField, 1, 1);
        bottomPane.add(addWorkout, 1, 2);
        bottomPane.add(addRest, 2, 2);
        bottomPane.add(startButton, 1, 4);
        root.setTop(text);
        root.setCenter(workouts);
        root.setBottom(bottomPane);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
