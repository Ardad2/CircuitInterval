package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import java.util.*;
import java.util.concurrent.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.*;

import javafx.util.Duration;
import sun.audio.*;

public class Main extends Application {
    Label text,workoutName,workoutTime;
    TextField nameField,workoutField;
    Button addWorkout,addRest,startButton,stopButton;
    Boolean running;
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
        AudioClip mediaPlayer = new AudioClip(new File(musicFile).toURI().toString());

      String musicFile1 = "timerend.wav";     // For example

       Media sound1 = new Media(new File(musicFile1).toURI().toString());
        AudioClip mediaPlayer1 = new AudioClip(new File(musicFile1).toURI().toString());

        text = new Label("Circuit Interval Timer");
        workoutName = new Label("NAME"+": ");
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

        Timeline oneSecond = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {

                                    if (workoutsList.size() == 0) {
                                        return;
                                    }
                                    if (workoutsList.size() == 1 && workoutsList.get(0).time == 1) {
                                        text.setText("over!");
                                        mediaPlayer1.play();
                                        workoutsList.clear();
                                        workouts.getItems().clear();
                                        return;
                                    }
                                    workoutsList.get(0).time -= 1;
                                    if (workoutsList.get(0).time == 0) {
                                        workoutsList.remove(0);
                                        mediaPlayer.play();
                                        return;
                                    }
                                    workouts.getItems().clear();
                                    workouts.refresh();
                                    for (int i = 0; i < workoutsList.size(); i++) {
                                        workouts.getItems().add(workoutsList.get(i));
                                    }

                            }
                        }));
        startButton = new Button("Start ");
        stopButton = new Button("Stop ");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneSecond.play();
                oneSecond.setCycleCount(Timeline.INDEFINITE);
                }
        });
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneSecond.stop();
            }
        });
        if (running=true)
        {
            oneSecond.play();
            oneSecond.setCycleCount(Timeline.INDEFINITE);
        }
        if (running=false)
        {
            oneSecond.stop();
        }
        BorderPane root = new BorderPane();
        GridPane rightPane=new GridPane();
        rightPane.add(workoutName,0,0);
        rightPane.add(nameField,1,0);
        rightPane.add(workoutTime,0,1);
        rightPane.add(workoutField,1,1);
        rightPane.add(addWorkout,0,2);
        rightPane.add(addRest,1,2);
        HBox bottomPane = new HBox();
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.getChildren().addAll(startButton,stopButton);
        Pane leftPane=new Pane();
        root.setTop(text);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setBottom(bottomPane);
        root.setCenter(workouts);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
