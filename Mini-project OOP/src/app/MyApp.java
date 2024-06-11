package app;

import algorithm.GenericTree;
import controller.MainController;
import controller.TreeSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApp extends Application {

	private TreeSceneController treeController;

	private Stage primaryStage;
	private Scene mainscreen, treescreen;
	
	public void switchToMainScreen() {
		primaryStage.setScene(mainscreen);
		primaryStage.setResizable(false);
	}
	
	public void switchToTreeScreen() {
		primaryStage.setResizable(true);
		primaryStage.setScene(treescreen);
		primaryStage.setResizable(false);
	}
	

	//set tree for treescenecontroller
	public void setTree(GenericTree tree) {
		treeController.setTree(tree);
		tree.setController(treeController);
		
	}
	

	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Load main screen
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent mainParent = mainLoader.load();
        MainController mainController = mainLoader.getController();
        mainController.setMainApp(this);
        mainscreen = new Scene(mainParent);
        
        // Load tree screen
        FXMLLoader treeLoader = new FXMLLoader(getClass().getResource("/view/Tree.fxml"));
        Parent treeParent = treeLoader.load();
        treeController = treeLoader.getController();
        treeController.setMainApp(this);
        treescreen = new Scene(treeParent);  // Corrected here
        

        primaryStage.setTitle("Tree Visualization");
        primaryStage.setScene(mainscreen);  // Set the initial scene to mainscreen
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
