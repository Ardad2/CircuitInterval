package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;

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
        text = new Label("Circuit Interval Timer");
        workoutName = new Label("WORKOUT NAME: ");
        ListView<unitWorkout> workouts = new ListView<unitWorkout>();
        workouts.getItems().add(new unitWorkout("Pushups", 30));
        nameField = new TextField();
        workoutTime = new Label("TIME: ");
        workoutField = new TextField();
        addWorkout = new Button("Add Workout");
        addWorkout.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
        text.setText("Workout");
         }
         });
        addRest = new Button("Add Rest");
        addRest.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                text.setText("Rest");
            }
        });
        startButton = new Button("Start ");
        startButton.setOnAction(new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent event)
        {
        text.setText("Start");
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
