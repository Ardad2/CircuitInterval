package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
    Button addWorkout,addRest,startButton,stopButton,removeButton,clearButton,moveUp,moveDown,homeButton;
    Boolean end;
    Scene main,home,timerWindow;
    int count=0;
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
        workoutsList.add(new unitWorkout("Test",1));
        workoutsList.add(new unitWorkout("Test",1));
        ListView<unitWorkout> workouts = new ListView<unitWorkout>();
        ListView<unitWorkout> workouts2 = new ListView<unitWorkout>();
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
            workouts2.getItems().clear();
            workouts2.refresh();
        for (int i=0;i<workoutsList.size();i++)
        {
            workouts.getItems().add(workoutsList.get(i));
            workouts2.getItems().add(workoutsList.get(i));
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
                workouts2.getItems().clear();
                workouts2.refresh();
                for (int i=0;i<workoutsList.size();i++)
                {
                    workouts.getItems().add(workoutsList.get(i));
                    workouts2.getItems().add(workoutsList.get(i));
                }
            }
        });
        Timeline oneSecond = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                    if (workoutsList.size() == 0) {
                                        stopButton.fire();
                                        return;
                                    }
                                    if (workoutsList.size() == 1 && workoutsList.get(0).time == 1) {
                                            mediaPlayer1.play();
                                        workoutsList.clear();
                                        workouts.getItems().clear();
                                        workouts2.getItems().clear();
                                        stopButton.fire();
                                        count++;
                                        return;
                                    }
                                    if (workoutsList.get(0).time == 0) {
                                        workoutsList.remove(0);
                                        mediaPlayer.play();
                                        return;
                                    }
                                workoutsList.get(0).time-=1;
                                    workouts.getItems().clear();
                                    workouts.refresh();
                                workouts2.getItems().clear();
                                workouts2.refresh();
                                    for (int i = 0; i < workoutsList.size(); i++) {
                                        workouts.getItems().add(workoutsList.get(i));
                                        workouts2.getItems().add(workoutsList.get(i));
                                    }

                            }
                        }));

        Button returnButton=new Button("return");
        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(main);
            }
        });




        startButton = new Button("Start ");
        stopButton = new Button("Stop ");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneSecond.play();
                oneSecond.setCycleCount(Timeline.INDEFINITE);
                if (workoutsList.size() != 0) {
                    primaryStage.setScene(timerWindow);
                }
                }
        });
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneSecond.pause();
            }
        });
        startButton.fire();
        removeButton=new Button("X");
        clearButton=new Button("C");
        moveUp=new Button("↑");
        moveUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int currIndex = workouts.getSelectionModel().getSelectedIndex();
                int prevIndex = currIndex - 1;
                try {
                    Collections.swap(workoutsList, currIndex, prevIndex);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {

                }
                    workouts.getItems().clear();
                workouts.getItems().addAll(workoutsList);
                workouts2.getItems().clear();
                workouts2.getItems().addAll(workoutsList);
                oneSecond.stop();
            }
        });
        moveDown=new Button("↓");
        moveDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int currIndex=workouts.getSelectionModel().getSelectedIndex();
                int nextIndex=currIndex+1;
                try {
                    Collections.swap(workoutsList, currIndex, nextIndex);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {

                }
                workouts.getItems().clear();
                workouts.getItems().addAll(workoutsList);
                workouts2.getItems().clear();
                workouts2.getItems().addAll(workoutsList);
                oneSecond.stop();
            }
        });
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index=workouts.getSelectionModel().getSelectedIndex();
                try{
                    workoutsList.remove(index);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {

                }
                workouts.getItems().clear();
                workouts.getItems().addAll(workoutsList);
                workouts2.getItems().clear();
                workouts2.getItems().addAll(workoutsList);
                oneSecond.stop();
            }
        });
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                oneSecond.stop();
                workoutsList.clear();
                workouts.getItems().clear();
                workouts2.getItems().clear();
            }
        });
        homeButton=new Button("Home");
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
        VBox leftPane=new VBox();
        leftPane.getChildren().addAll(moveUp,moveDown,removeButton,clearButton);
        HBox topPane=new HBox();
        topPane.getChildren().addAll(homeButton,text);
        root.setTop(topPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setBottom(bottomPane);
        root.setCenter(workouts);
        main = new Scene(root, 400, 400);


        GridPane centerPane=new GridPane();


       workouts2.setPrefWidth(600);
       workouts2.setPrefHeight(400);
        workouts2.setStyle("-fx-font-size: 9.5em ;");
        workouts2.getItems().add(workoutsList.get(0));
        centerPane.getChildren().add(workouts2);

        BorderPane timerBody=new BorderPane();
        timerBody.setTop(returnButton);
        timerBody.setCenter(centerPane);

        timerWindow=new Scene(timerBody,600,400);

        BorderPane homeMain=new BorderPane();
        Button startApp=new Button("Start");
        Button exitApp=new Button("Exit");

        startApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(main);
            }
        });

        exitApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });


        Label heading=new Label("CIRCUIT INTERVAL TIMER APP");
        VBox buttons=new VBox();
        buttons.getChildren().addAll(startApp,exitApp);
        HBox top=new HBox(heading);
        homeMain.setCenter(buttons);
        homeMain.setTop(top);



        home = new Scene(homeMain, 400,400);

         returnButton=new Button("RETURN");
        primaryStage.setScene(home);
        primaryStage.show();
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                primaryStage.setScene(home);
                workouts.getItems().clear();
                workoutsList.clear();
                workoutField.clear();
                nameField.clear();
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
