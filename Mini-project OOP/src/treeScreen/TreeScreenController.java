package treeScreen;

import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Main;
import algorithm.GenericTree;
import algorithm.Node;

import javax.swing.*;
import java.util.LinkedList;

public class TreeScreenController {
    private GenericTree tree;
    private TreeScreen treeScreen;
    private StackPane root;
    private String choiceTraversal = new String("BFS");
    private int historyInsert;

    @FXML
    private VBox genericTreeScreen;

    @FXML
    private TextField tfDelete;

    @FXML
    private ToggleGroup toggleControl;

    @FXML
    private VBox boxControl;

    @FXML
    private TextField tfUpdateNewValue;

    @FXML
    private TextField tfChild;

    @FXML
    private Pane drawingTreePane;

    @FXML
    private TextField tfSearchFor;

    @FXML
    private HBox boxNevigate;

    @FXML
    private TextField tfUpdateOldValue;

    @FXML
    private ToggleGroup toggleTraversal;

    @FXML
    private TextField tfParent;

    @FXML
    private Pane bfsPseudoCode;

    @FXML
    private Pane dfsPseudoCode;

    @FXML
    private Rectangle pseudoCode1;

    @FXML
    private Rectangle pseudoCode2;

    @FXML
    private Rectangle pseudoCode3;

    @FXML
    private Rectangle pseudoCode4;

    @FXML
    private Button btnRun;

    @FXML
    private Button btnStop;

    @FXML
    private Button btnUndoInsert;

    @FXML
    private FlowPane queueFlowPane;

    @FXML
    private Text failBalance;

    public TreeScreenController(GenericTree tree) {
        super();
        this.tree = tree;
        tree.setController(this);
    }

    /*public void initialize() {
        this.root = this.tree.getRoot();
        this.drawingTreePane.getChildren().add(this.root);
        this.root.setLayoutX(this.drawingTreePane.getPrefWidth()/2);
        this.failBalance.setVisible(false);
    }*/

    public void setTreeScreen(TreeScreen treeScreen) {
        this.treeScreen = treeScreen;
    }
}
