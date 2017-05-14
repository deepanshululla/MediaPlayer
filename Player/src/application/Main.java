package application;
	

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;

public class Main extends Application{
	//class variables
	Player player;
	FileChooser fileChooser = new FileChooser();
	MenuBar menu;
	
        
	public void start(final Stage primaryStage) {
		//start is a method that we are overriding
		// Defining menu item and menu bar
		MenuItem open =new MenuItem("Open");
		Menu fileMenu= new Menu("File");
		menu =new MenuBar();
		
		// adding open to file and file to menu
		fileMenu.getItems().add(open);
		menu.getMenus().add(fileMenu);
		
		
		//defining width,height for the video+ player
		double sceneHeight=430;
		double sceneWidth=640;
		
		open.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        player.player.pause();//first pause the payer
                        
                        fileChooser.setTitle("Open Resource File");
                        File file = fileChooser.showOpenDialog(primaryStage);
                        assert(file!=null);
                        try {
                            player = new Player(file.toURI().toURL().toExternalForm());
                            player.setTop(menu);
                            Scene scene = new Scene(player, 640, 430, Color.BLACK);
                            primaryStage.setScene(scene);
                        }
                        catch (MalformedURLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                });
//		open.setOnAction(event->{
//			player.player.pause();
//			File file=fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
//			assert(file!=null);
//			
//			
//		});
		//creating a player object; adding a default video
		player = new Player("file:///C:/Users/deepa/Videos/gotg.mp4");
		player.setTop(menu);//adding open menu option on top of player
		//creating scene object with black background
		Scene scene = new Scene(player, sceneWidth, sceneHeight, Color.BLACK);
		//setting properties of primary stage to our scene and displaying it
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
