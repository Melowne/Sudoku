package org.openjfx.javafx_archetype_simple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Field {
	private GridPane field=new GridPane();
	private TextField[][] labels=new TextField[9][9];
	private int sudokuold[][];
	private int sudoku[][];
	private Solver solver =new Solver();
	private ArrayList<String> sudokus=new ArrayList<String>();
	public TextField[][] givelabels() {
		return labels;
	}
	Field() throws IOException {
		readSudokus();
		createsudoku();

		field.setVisible(false);

	}
	private void readSudokus() throws IOException {

		InputStream input = getClass().getResourceAsStream("sudokus.txt");
		var sr=new BufferedReader(new InputStreamReader(input));
		String s="";
		while ((s=sr.readLine())!=null) {
			sudokus.add(s);
		}
		sr.close();
	}
	public void createsudoku() {
		String s=sudokus.get((int)(Math.random()*sudokus.size()));

		sudoku=new int[9][9];sudokuold=new int[9][9];
		for (int i = 0; i < 81; i++) {
			labels[i/9][i%9]=new TextField();
			if(s.charAt(i)!='0') {
				sudoku[i/9][i%9]=(int)(s.charAt(i)-48);		
				sudokuold[i/9][i%9]=(int)(s.charAt(i)-48);		
				labels[i/9][i%9].setText(s.charAt(i)+"");
				labels[i/9][i%9].setEditable(false);
				labels[i/9][i%9].setMouseTransparent(true);
				labels[i/9][i%9].setFocusTraversable(false);
			}

			labels[i/9][i%9].setFont(new Font("Arial",26));
			labels[i/9][i%9].setStyle("-fx-text-box-border: rgb(204,204,204) ;-fx-font-weight: bold;");

			if(i%3==0)labels[i/9][i%9].setStyle("-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
					+ " transparent transparent transparent black;-fx-border-width: 2;-fx-font-weight: bold;");
			if(i>26&&i<36||(i>53&&i<63)) {labels[i/9][i%9].setStyle("-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
					+ " black transparent transparent transparent;-fx-border-width: 2;-fx-font-weight: bold;");
			if(i%3==0)labels[i/9][i%9].setStyle("-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
					+ " black transparent transparent black;-fx-border-width: 2;-fx-font-weight: bold;");
			}

			labels[i/9][i%9].prefHeightProperty().bind(field.heightProperty());
			labels[i/9][i%9].prefWidthProperty().bind(field.heightProperty());
			labels[i/9][i%9].setAlignment(Pos.CENTER);

			field.add(labels[i/9][i%9], i%9, i/9);
		}
		solver.solve(sudoku);

	}


	public int[][] givsudoku(){
		return sudoku;
	}
	public int[][] givsudokuold(){
		return sudokuold;
	}

	public GridPane givfield() {
		return field;

	}
}
