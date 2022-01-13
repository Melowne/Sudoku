package org.openjfx.javafx_archetype_simple;
import java.util.ArrayList;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.stage.Stage;
public class Header {
private GridPane pane =new GridPane();
private GridPane mainpane =new GridPane();

StackPane timerpane=new StackPane();Label l=new Label("Sudoku");

private Timeline timer;
private Button complete =new Button("Lösen");
private Button tip= new Button("Tip");
private Button quit=new Button("Ende");
private Button switsch=new Button("Anderes");
private Button check=new Button("Prüfe");
private Button start=new Button("Start");
private Field field;private Stage stage;
private int help=0;private boolean isdone;
private boolean exists=false;private int isdoneI=0;
private ArrayList<Integer>emptyfields =new ArrayList<Integer>();	
private ArrayList<Integer>randomvalues=new ArrayList<Integer>();

private Date date = new Date();private int sec=0,min=0;
Header(){
	
	 l.setFont(new Font("arial",25));
	 timerpane.getChildren().add(l);

	
	switsch.prefWidthProperty().bind(pane.widthProperty());
	complete.prefWidthProperty().bind(pane.widthProperty());
	tip.prefWidthProperty().bind(pane.widthProperty());
	quit.prefWidthProperty().bind(pane.widthProperty());
	check.prefWidthProperty().bind(pane.widthProperty());
	start.prefWidthProperty().bind(pane.widthProperty());
	
	pane.add(start,0,0);
	pane.add(tip,1,0);
	pane.add(check,2,0);
	pane.add(switsch,3,0);
	pane.add(complete,4,0);
	pane.add(quit,5,0);
	
	mainpane.add(timerpane, 0, 0);
	mainpane.add(pane, 0, 1);
	
	//timer
	timer = new Timeline(new KeyFrame(Duration.millis(100), e -> {	
		date.setMinutes(min);date.setSeconds(sec/10);
		String s=String.format("%tM : %tS", date,date);
		l.setText(s);
		sec++;if(sec!=0&&sec%600==0)min=sec/600-1;
		for (int i = 0;!field.givelabels()[i/9][i%9].getText().isEmpty()&& i < 81; i++) {
			if(
		Integer.parseInt(field.givelabels()[i/9][i%9].getText())
					!=field.givsudoku()[i/9][i%9]) {
					break;	}
			else if(i==80&&help==0) {l.setText("Bravo");isdone=true;timer.stop();}
			else if(i==80&&help>0&&help<4) {l.setText("Nicht schlecht !");isdone=true;timer.stop();}
			else if(i==80&&help>3) {l.setText("Naja !");isdone=true;timer.stop();}
			
		}
		if(isdone) {
			field.givelabels()[emptyfields.get(randomvalues.get(isdoneI))/10][emptyfields.get(randomvalues.get(isdoneI))%10].setText(field.givsudoku()[emptyfields.get(randomvalues.get(isdoneI))/10][emptyfields.get(randomvalues.get(isdoneI))%10]+"");
			//field.givelabels()[emptyfields.get(randomvalues.get(isdoneI))/10][emptyfields.get(randomvalues.get(isdoneI))%10].setStyle(s)
			annoyingborder(field.givelabels(),emptyfields.get(randomvalues.get(isdoneI))/10,emptyfields.get(randomvalues.get(isdoneI))%10,"0,235,0");
					
			isdoneI++;
			if(isdoneI>=emptyfields.size()) {l.setText("Naja !");isdoneI=0;timer.stop();}
		}
		
	}));	 
	timer.setCycleCount(Animation.INDEFINITE); 
	
	//Events
	start.setOnMouseClicked(e->{
		exists=true;isdone=false;
		field.createsudoku();
		sec=0;min=0;help=0;isdone=false;
		field.givfield().setVisible(true);
		timer.play();
		
		
	});
	quit.setOnMouseClicked(e->{
		
		timer.stop();stage.close();
		
	});

tip.setOnMouseClicked(e->{
	help++;
	var i=(int)(Math.random() * 81 );
	while (exists&&!isdone&&!field.givelabels()[i/9][i%9].getText().isEmpty()) {
		i=(int)(Math.random() * 81 );
	}
	field.givelabels()[i/9][i%9].setText(field.givsudoku()[i/9][i%9]+"");
	annoyingborder(field.givelabels(),i/9,i%9,"0,235,0");
	
});
switsch.setOnMouseClicked(e->{
	timer.stop();help=0;	sec=0;min=0;isdone=false;
	field.createsudoku();timer.play();
});

check.setOnMouseClicked(e->{
		help++;
		for (int i = 0; i < 81; i++) {
			if(!field.givelabels()[i/9][i%9].getText().isEmpty()&&field.givsudokuold()[i/9][i%9]!=Integer.parseInt(field.givelabels()[i/9][i%9].getText())
			&&field.givsudoku()[i/9][i%9]==Integer.parseInt(field.givelabels()[i/9][i%9].getText()))
		{
				annoyingborder(field.givelabels(),i/9,i%9,"0,235,0");
		
		}
			
		
		else if (!field.givelabels()[i/9][i%9].getText().isEmpty()&&field.givsudokuold()[i/9][i%9]!=Integer.parseInt(field.givelabels()[i/9][i%9].getText())
					&&field.givsudoku()[i/9][i%9]!=Integer.parseInt(field.givelabels()[i/9][i%9].getText()))
			{
			annoyingborder(field.givelabels(),i/9,i%9,"230, 0, 0");
		}			
							
		}

	});


complete.setOnMouseClicked(e->{
	emptyfields.clear();
	for (int i = 0; i < 81; i++) {
		if(field.givelabels()[i/9][i%9].getText().isEmpty()) {
			emptyfields.add((i/9)*10+(i%9));
		}
	}		
	randomvalues.clear();int ch=0;
	while(randomvalues.size()<emptyfields.size()) {
		ch=(int)(Math.random()*emptyfields.size());
		if(!randomvalues.contains(ch))randomvalues.add(ch);
	}
	
	isdone=true;
	
		
	});

}
private void annoyingborder(TextField[][] l,int y,int x,String s) {
	
	l[y][x].setStyle("-fx-text-inner-color: rgb("+s+");-fx-font-weight: bold;");
	if(x%3==0)l[y][x].setStyle("-fx-text-inner-color: rgb("+s+");-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
			+ " transparent transparent transparent black;-fx-border-width: 2;-fx-font-weight: bold;");
	
	if(y==3||y==6) {l[y][x].setStyle("-fx-text-inner-color:rgb(0,235,0);-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
			+ " black transparent transparent transparent;-fx-border-width: 2;-fx-font-weight: bold;");
		if(x%3==0)l[y][x].setStyle("-fx-text-inner-color: rgb("+s+");-fx-text-box-border: rgb(204,204,204) ;-fx-border-color:"
			+ " black transparent transparent black;-fx-border-width: 2;-fx-font-weight: bold;");
	
	}
}
public void getStage(Stage x) {
	stage=x;
}
public void setfield(Field f) {
	field=f;
}
public GridPane givfooter() {
	return mainpane;
}
}
