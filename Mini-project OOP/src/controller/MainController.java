package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import algorithm.BalancedBinaryTree;
import algorithm.BalancedTree;
import algorithm.BinaryTree;
import algorithm.GenericTree;
import algorithm.Node;
import app.MyApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class MainController implements Initializable{
	
	private String chooseTree = "GenericTree";
	
	private MyApp myApp;
	
	@FXML
    private AnchorPane createTreePane;

    @FXML
    private AnchorPane helpPane;

    @FXML
    private HBox depthContainer;
    
    @FXML
    private TextField tfMaxDepth;

    @FXML
    private TextField tfRootValue;

    @FXML
    private ToggleGroup treeType;
    
    
    
    @FXML
    void createTree(ActionEvent event) {
    	
    	Node root = new Node(Integer.parseInt(this.tfRootValue.getText()));
    	
        if(chooseTree.equals("GenericTree")) {
            GenericTree tree = new GenericTree(root);
            myApp.setTree(tree);
            
        } else if(chooseTree.equals("BinaryTree")) {
            BinaryTree tree = new BinaryTree(root);
            myApp.setTree(tree);
            
        } else if(chooseTree.equals("Balancedtree")) {
            BalancedTree tree = new BalancedTree(root, Integer.parseInt(tfMaxDepth.getText()));
            myApp.setTree(tree);
            
        } else {
            BalancedBinaryTree tree = new BalancedBinaryTree(root, Integer.parseInt(tfMaxDepth.getText()));
            myApp.setTree(tree);
        }
        
        
    	myApp.switchToTreeScreen();

    }
    

    
    @FXML
    void quit(ActionEvent event) {
    	int result = JOptionPane.showConfirmDialog(null,
                "Do you want to Exit ?", "Exit Confirmation : ",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    @FXML
    void showCreate(ActionEvent event) {
    	helpPane.setVisible(false);
    	createTreePane.setVisible(true);

    }

    @FXML
    void showHelp(ActionEvent event) {
    	createTreePane.setVisible(false);
    	helpPane.setVisible(true);
    }
    

    @FXML
    void buttonGenericTreePressed(ActionEvent event) {
    	this.depthContainer.setVisible(false);
    	chooseTree = "GenericTree";
    }
    
    @FXML
    void buttonBinaryTreePressed(ActionEvent event) {
    	this.depthContainer.setVisible(false);
    	chooseTree = "BinaryTree";
    }
    
    @FXML
    void buttonBalancedTreePressed(ActionEvent event) {
    	this.depthContainer.setVisible(true);
    	chooseTree = "BalancedTree";
    }
    
    @FXML
    void buttonBalancedBinaryTreePressed(ActionEvent event) {
    	this.depthContainer.setVisible(true);
    	chooseTree = "BalancedBinaryTree";
    }
    

    public void setMainApp(MyApp mainApp) {
        this.myApp = mainApp;
        
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		helpPane.setVisible(false);
    	createTreePane.setVisible(true);
    	this.depthContainer.setVisible(false);

		
	}

}
