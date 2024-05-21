package mainMenu;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import algorithm.*;
import treeScreen.TreeScreen;

public class MainMenuController {
    private MainMenuScreen mainMenuScreen;
    private String chooseTree = "GenericTree";

    @FXML
    private ScrollPane helpPane;

    @FXML
    private ToggleGroup treeType;

    @FXML
    private Pane treeTypePane;

    @FXML
    private TextField tfRootValue;

    @FXML
    private TextField tfMaximumDepthDiff;

    @FXML
    private Label labelMaximumDepthDiff;

    @FXML
    void btnCreatePressed(ActionEvent event) {
        helpPane.setVisible(false);
        treeTypePane.setVisible(true);
    }

    @FXML
    void btnHelpPressed(ActionEvent event) {
        helpPane.setVisible(true);
        treeTypePane.setVisible(false);
    }

    @FXML
    void btnCreateTreePressed(ActionEvent e) {
        Node root = new Node(Integer.parseInt(this.tfRootValue.getText()));

        if(chooseTree.equals("GenericTree")) {
            GenericTree tree = new GenericTree(root);
            createScreen(tree);
        } else if(chooseTree.equals("BinaryTree")) {
            BinaryTree tree = new BinaryTree(root);
            createScreen(tree);
        } else if(chooseTree.equals("Balancedtree")) {
            BalancedTree tree = new BalancedTree(root, Integer.parseInt(tfMaximumDepthDiff.getText()));
            createScreen(tree);
        } else {
            BalancedBinaryTree tree = new BalancedBinaryTree(root, Integer.parseInt(tfMaximumDepthDiff.getText()));
            createScreen(tree);
        }
    }

    public void createScreen(GenericTree tree){
        TreeScreen treeScreen = new TreeScreen(tree);
        this.mainMenuScreen.setTreeScreen(treeScreen);
        treeScreen.setMainMenu(mainMenuScreen);
        this.mainMenuScreen.setVisible(false);
        this.mainMenuScreen.getTreeScreen().setVisible(true);
        this.mainMenuScreen.getTreeScreen().setTitle(chooseTree);
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }

    @FXML
    void btnQuitPressed(ActionEvent event) {
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to Exit ?", "Exit Confirmation : ",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    @FXML
    void buttonBinaryTreePressed(ActionEvent event) {
        this.labelMaximumDepthDiff.setVisible(false);
        this.tfMaximumDepthDiff.setVisible(false);
        chooseTree = "BinaryTree";
    }

    @FXML
    void buttonGenericTreePressed(ActionEvent event) {
        this.labelMaximumDepthDiff.setVisible(false);
        this.tfMaximumDepthDiff.setVisible(false);
        chooseTree = "GenericTree";
    }

    @FXML
    void buttonBalancedTreePressed(ActionEvent event) {
        chooseTree = "BalancedTree";
        this.labelMaximumDepthDiff.setVisible(true);
        this.tfMaximumDepthDiff.setVisible(true);
    }

    @FXML
    void buttonBalancedBinaryTreePressed(ActionEvent event) {
        chooseTree = "BalancedBinaryTree";
        this.labelMaximumDepthDiff.setVisible(true);
        this.tfMaximumDepthDiff.setVisible(true);
    }
}
