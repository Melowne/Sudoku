package org.openjfx.javafx_archetype_simple;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

  	  var field= new Field();
  		var grid1=field.givfield();
  		
  		var header=new Header();
  		var grid2=header.givfooter();
  		header.setfield(field);
  		header.getStage(stage);
  		
  		
  	
  		 var root =  new GridPane();
  	
  		
  		root.add(grid1, 0, 1);
  		root.add(grid2, 0, 0);
  		
  		
  		
  		Scene scene = new Scene(root,550,550);
  		
        stage.setTitle("Sudoku");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}