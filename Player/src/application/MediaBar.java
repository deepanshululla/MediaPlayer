package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

public class MediaBar extends HBox{
	
	Slider timeSlider=new Slider();
	Slider volumeSlider=new Slider();
	
	Button playButton= new Button("||");
	Label volumeLabel =new Label("Volume:");
	MediaPlayer player;
	
	public MediaBar(MediaPlayer play){
		player = play;
		setAlignment(Pos.CENTER);
		setPadding(new Insets(5,10,5,10));
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMinWidth(30);
		volumeSlider.setValue(50);
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		
		playButton.setPrefWidth(30);
		// We are adding all the media buttons
		getChildren().add(playButton);
		getChildren().add(timeSlider);
		getChildren().add(volumeLabel);
		getChildren().add(volumeSlider);
		
		// This is an event handler for playing and pausing the videos
		playButton.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				Status status = player.getStatus();
				if(status==Status.PLAYING){
					if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){
						//checking if current timeSlider is > total duration=> video has ended then replay the video
						player.seek(player.getStartTime());
						player.play();
						
					}
					else{
						//if video is playing and we want to pause it in middle
						player.pause();
						playButton.setText(">");
						
					}
				}
				if(status==Status.PAUSED || status==Status.HALTED || status== Status.STOPPED){
					//if the video is paused /halted/stopped start playing the video
					player.play();
					playButton.setText("||");
				}
			}
		});
		// we are adding a listener to our player for getting the current timeSlider
		// This is for the timeSlider slider
		player.currentTimeProperty().addListener(new InvalidationListener(){
			public void invalidated(Observable ov){
				updateValues();
			}
		});
		//we are writing code for when slider is pressed 
		//so that we can move to that position in the video file
		timeSlider.valueProperty().addListener(new InvalidationListener(){
			public void invalidated(Observable ov){
				if(timeSlider.isPressed()){
					player.seek(player.getMedia().getDuration().multiply(timeSlider.getValue()/100));
				}
			}		
		});
		//here we will try to adjust volume depending on user input by slider
		volumeSlider.valueProperty().addListener(new InvalidationListener(){
			public void invalidated(Observable ov){
				if(volumeSlider.isPressed()){
					player.setVolume((volumeSlider.getValue()/100));
				}
			}
		});
	}
	protected void updateValues(){
		// this function just moves the slider with respect to current timeSlider
		Platform.runLater(new Runnable(){
			public void run(){
				timeSlider.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
			}
		});
	}
}
