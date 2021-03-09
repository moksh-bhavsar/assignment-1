package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import java.io.*;

public class Main extends Application {

    private TableView table = new TableView();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Spam Master 3000");
        primaryStage.setScene(new Scene(root, 300, 275));

        TableColumn file = new TableColumn("File");
        TableColumn actualClass = new TableColumn("Actual Class");
        TableColumn spamProb = new TableColumn("Spam Probability");

        table.getColumns().addAll(file, actualClass, spamProb);

        Scene tableView = new Scene(table, 300, 275);
        primaryStage.setScene(tableView);

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File mainDirectory = directoryChooser.showDialog(primaryStage);

        primaryStage.show();


        file.setCellValueFactory(new PropertyValueFactory<TestFile,String>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<TestFile,String>("actualClass"));
        spamProb.setCellValueFactory(new PropertyValueFactory<TestFile, Double>("spamProbability"));

        ObservableList data = test.getAllFiles(mainDirectory);
        table.setItems(data);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
