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
    private StackPane rootNode;
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

    public void initialize() {
        this.rootNode = this.tree.getRootNode();
        this.drawingTreePane.getChildren().add(this.rootNode);
        this.rootNode.layoutXProperty().bind(drawingTreePane.widthProperty().subtract(this.rootNode.widthProperty()).divide(2));
//        this.rootNode.layoutYProperty().bind(drawingTreePane.heightProperty().subtract(this.rootNode.heightProperty()).divide(2));

        this.failBalance.setVisible(false);
    }

    @FXML
    void btnAddNodePressed(ActionEvent event) {
        btnUndoInsert.setVisible(true);
        if (!Node.listValue.contains(Integer.parseInt(this.tfChild.getText()))) {

            Node childNode = new Node(Integer.parseInt(this.tfChild.getText()));
            if (tree.insertNode(Integer.parseInt(tfParent.getText()), childNode)){
                historyInsert = childNode.getValue();
                this.drawingTreePane.getChildren().add(childNode);
                this.drawingTreePane.getChildren().add(childNode.getParentLine());
            }
            this.tfChild.clear();
        } else {
            this.tfChild.clear();
            JOptionPane.showMessageDialog(null, "Added node already exist!");
        }
    }
    @FXML
    void btnRunPressed(ActionEvent event) {
    	btnRun.setVisible(false);
    	btnStop.setVisible(true);
    	pseudoCode1.setVisible(true);
    	pseudoCode2.setVisible(true);
    	pseudoCode3.setVisible(true);
    	pseudoCode4.setVisible(true);
    	boxControl.setVisible(true);
    	queueFlowPane.setVisible(true);
    	
    	if(this.choiceTraversal.equals("BFS")) {
    		bfsPseudoCode.setVisible(true);
    		traversalBFS();
    		
    	}else if (choiceTraversal.equals("DFS")) {
    		dfsPseudoCode.setVisible(true);
    		traversalDFS();
    	}
    	
    }
    
    @FXML
    void btnPausePressed(ActionEvent event) {
    	tree.getTimeline().pause();
    	this.boxNevigate.setVisible(true);
    }
    
    @FXML
    void btnContinuePressed(ActionEvent event) {
    	this.boxNevigate.setVisible(false);
    	tree.getTimeline().play();
    }

    public void setTree(GenericTree tree) {
        this.tree = tree;
    }
    public void setTreeScreen(TreeScreen treeScreen) {
        this.treeScreen = treeScreen;
    }

    public void setFailBalance(boolean appear) {
        this.failBalance.setVisible(appear);
    }
    
    @FXML
    void btnBackPressed(ActionEvent event) {
    	if (this.choiceTraversal.equals("BFS")) {
	    	codeUpdate();
	    	tree.backBFS();
	    	queueUpdate();
    	} else if (this.choiceTraversal.equals("DFS")) {
    		codeUpdate();
    		tree.backDFS();
    		queueUpdate();
    	}
    }
    
    @FXML
    void btnForwardPressed(ActionEvent event) {
    	if (this.choiceTraversal.equals("BFS")) {
	    	codeUpdate();
	    	tree.forwardBFS();
	    	queueUpdate();
    	} else if (this.choiceTraversal.equals("DFS")) {
    		codeUpdate();
    		tree.forwardDFS();
    		queueUpdate();
    	}
    }
    
    @FXML
    void btnStopPressed(ActionEvent event) {
    	tree.getTimeline().stop();
    	tree.updateState();
    	
    	boxControl.setVisible(false);
    	boxNevigate.setVisible(false);
    	btnStop.setVisible(false);
    	pseudoCode1.setVisible(false);
    	pseudoCode2.setVisible(false);
    	pseudoCode3.setVisible(false);
    	pseudoCode4.setVisible(false);
    	bfsPseudoCode.setVisible(false);
    	dfsPseudoCode.setVisible(false);
    	queueFlowPane.setVisible(false);
    	btnRun.setVisible(true);
    }
    
    @FXML
    void btnChoiceBFSPressed(ActionEvent event) {
    	this.choiceTraversal = new String("BFS");
    	queueFlowPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }
    
    @FXML
    void btnChoiceDFSPressed(ActionEvent event) {
    	this.choiceTraversal = new String("DFS");
    	queueFlowPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    }

    
    
 
    
 
    void traversalBFS() {
    	KeyFrame step = new KeyFrame(Duration.seconds(1), 
				new EventHandler<ActionEvent>() {
			  		public void handle(ActionEvent event) {
			  			codeUpdate();
			  			tree.forwardBFS();
			  			queueUpdate();
			  		}
			} );
			
			tree.setState(2);
    	tree.setQueue(new LinkedList<Node>());
    	tree.setTraveledNode(new LinkedList<Node>());
    	tree.getQueue().add(tree.getRootNode());
    	queueUpdate();
    	
    	Timeline timeline = new Timeline();
    	timeline.getKeyFrames().add(step);
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	
    	tree.setTimeline(timeline);
    	tree.getTimeline().play();
    }
    
    void traversalDFS() {
    	KeyFrame step = new KeyFrame(Duration.seconds(1),
    			new EventHandler<ActionEvent>() {
    				public void handle(ActionEvent event) {
    					codeUpdate();
    					tree.forwardDFS();
    					queueUpdate();
    				}
    	});
    	
    	tree.setState(2);
    	tree.setQueue(new LinkedList<Node>());
    	tree.setTraveledNode(new LinkedList<Node>());
    	tree.getQueue().add(tree.getRootNode());
    	
    	Timeline timeline = new Timeline();
    	timeline.getKeyFrames().add(step);
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	
    	tree.setTimeline(timeline);
    	tree.getTimeline().play();
    }
    
    @FXML
    void btnInsertUndoPressed(ActionEvent event) {
    	Node node = tree.searchNode(historyInsert);
    	drawingTreePane.getChildren().remove(node.getParentLine());
    	drawingTreePane.getChildren().remove(node);
    	tree.deleteNode(historyInsert);
    	btnUndoInsert.setVisible(false);

    	Node parent = node.getParentNode();
    	parent.getChildNodes().remove(node);
    }
    
    @FXML
    void btnRedoPressed(ActionEvent event) {
	    Node rootNode = tree.getRootNode();
	    while (!rootNode.getChildNodes().isEmpty()) {
	    	this.removeFromPane(rootNode.getChildNodes().getLast());
	    	tree.deleteNode(rootNode.getChildNodes().getLast().getValue());
	    }
    }
    @FXML
    void btnBackMainMenuPressed(ActionEvent event) {
    	Node.listValue = new LinkedList<Integer>();
    	this.treeScreen.getMainMenu().setVisible(true);
    	this.treeScreen.setVisible(false);
    }
    
    @FXML
    void btnDeleteNodePressed(ActionEvent event) {
        if (Node.listValue.contains(Integer.parseInt(this.tfDelete.getText()))) {
        	Node delNode = tree.searchNode(Integer.parseInt(this.tfDelete.getText()));
        	this.removeFromPane(delNode);
        	tree.deleteNode(Integer.parseInt(this.tfDelete.getText()));
        }else{
            JOptionPane.showMessageDialog(null, "Provided node not found!");
        }
    }
    
    @FXML
    void buttonUpdate(ActionEvent event) {
    	/*for (int i:Node.listValue) {
    		System.out.println(i);
    	}*/ //for testing purpose
        if (Node.listValue.contains(Integer.parseInt(this.tfUpdateOldValue.getText()))) {
            if (!Node.listValue.contains(Integer.parseInt(this.tfUpdateNewValue.getText()))) {
	            Node oldNode = tree.searchNode(Integer.parseInt(this.tfUpdateOldValue.getText()));
	            oldNode.setValue(Integer.parseInt(this.tfUpdateNewValue.getText()));    
            } else {
                JOptionPane.showMessageDialog(null, "New node has been in tree!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Node not found!");
        }
    }
    @FXML
    void btnSearchPressed(ActionEvent event) {  // them dieu kien cho method traversalBFS

	    Node searchedNode = tree.searchNode(Integer.parseInt(this.tfSearchFor.getText()));
        Timeline timeline = new Timeline();
        tree.setState(1);

        KeyFrame popQueue = new KeyFrame(Duration.seconds(1),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (tree.getState() == 1) {
                            if (!tree.getQueue().isEmpty()) {
                                tree.setCurrentNode(tree.getQueue().getFirst());
                                tree.getQueue().removeFirst();
                                tree.getTraveledNode().add(tree.getCurrentNode());
                                tree.getCurrentNode().getCircle().setFill(Color.LIGHTCORAL);
                                tree.setState(2);

                                if (tree.getCurrentNode() == searchedNode){         //them dieu kien
                                    for (int i:Node.listValue){
                                        Node node = tree.searchNode(i);
                                        node.getCircle().setFill(Color.WHITE);
                                    }
                                    searchedNode.getCircle().setFill(Color.LIGHTGREEN);
                                    timeline.stop();

                                    Timeline resetToWhite = new Timeline();
                                    KeyFrame reset = new KeyFrame(Duration.seconds(2),
                                            new EventHandler<ActionEvent>() {
                                                public void handle(ActionEvent event) {
                                                    for (int i:Node.listValue){
                                                        Node node = tree.searchNode(i);
                                                        node.getCircle().setFill(Color.WHITE);
                                                    }
                                                }
                                            } );
                                    resetToWhite.getKeyFrames().add(reset);
                                    resetToWhite.play();
                                }

                            } else {
                                timeline.stop();
                                JOptionPane.showMessageDialog(null, "Node not found!");
                            }
                        }
                    }
                } );
        KeyFrame pushQueue = new KeyFrame(Duration.seconds(2),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        if (tree.getState() == 2) {
                            if (!tree.getCurrentNode().getChildNodes().isEmpty()) {
                                for (Node node: tree.getCurrentNode().getChildNodes()) {
                                    tree.getQueue().add(node);
                                    node.getCircle().setFill(Color.LIGHTYELLOW);
                                }
                            }
                            tree.setState(1);
                        }
                    }
                } );


        tree.setQueue(new LinkedList<Node>());
        tree.setTraveledNode(new LinkedList<Node>());
        tree.getQueue().add(tree.getRootNode());
        timeline.getKeyFrames().add(popQueue);
        timeline.getKeyFrames().add(pushQueue);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
 
    
    
    void queueUpdate() {
    	queueFlowPane.getChildren().clear();
    	Pane p;
    	Circle c;
    	for (Node node: tree.getQueue()) {
    		p = new Pane();
    		p.setPrefSize(60, 60);
    		c = new Circle(30);
    	    c.setFill(Color.LIGHTYELLOW);
    	    c.setStroke(Color.BLACK);
    	    p.getChildren().add(c);
    	    p.getChildren().add(new Text(node.getValue()+""));
    		queueFlowPane.getChildren().add(p);
    	}
    }
    
    void codeUpdate() {
    	if (tree.getState() == 2) {
				pseudoCode1.setFill(Color.LIGHTBLUE);
				pseudoCode2.setFill(Color.LIGHTBLUE);
				pseudoCode3.setFill(Color.WHITE);
				pseudoCode4.setFill(Color.WHITE);

			} else if (tree.getState() == 1) {
				pseudoCode3.setFill(Color.LIGHTBLUE);
				pseudoCode4.setFill(Color.LIGHTBLUE);
				pseudoCode1.setFill(Color.WHITE);
				pseudoCode2.setFill(Color.WHITE);
			}
    }
    
    void removeFromPane(Node removeNode) {
    	LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(removeNode);
		Node currentNode;
		
		this.drawingTreePane.getChildren().remove(removeNode.getParentLine());
		this.drawingTreePane.getChildren().remove(removeNode);
		
		while(!queue.isEmpty()) {
			currentNode = queue.getFirst();
			
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
						this.drawingTreePane.getChildren().remove(node.getParentLine());
						this.drawingTreePane.getChildren().remove(node);
				}
			}
			queue.removeFirst();
		}
    }
}






