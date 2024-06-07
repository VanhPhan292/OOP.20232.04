package controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import algorithm.GenericTree;
import algorithm.Node;
import app.MyApp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TreeSceneController implements Initializable {
	
	private MyApp myApp;
	
	private GenericTree tree;
	
	
//	private TreeScreen treeScreen;
	
    private StackPane rootNode;
    
    private String choiceTraversal = new String("BFS");
    
    private int historyInsert;
    
    @FXML
    private Label pseudoCode1;

    @FXML
    private Label pseudoCode2;

    @FXML
    private Label pseudoCode3;

    @FXML
    private Label pseudoCode4;

    @FXML
    private Label pseudoCode5;
    

    @FXML
    private VBox bfsPseudoCode;

    @FXML
    private VBox dfsPseudoCode;

    @FXML
    private Pane drawingPane;
    
    @FXML
    private Label warningLabel;
    
    @FXML
    private Button btnInsertUndo;
    @FXML
    private TextField tfInsertChild;

    @FXML
    private TextField tfInsertParent;
    
    @FXML
    private TextField tfDeleteValue;
    
    @FXML
    private TextField tfUpdateNewValue;

    @FXML
    private TextField tfUpdateOldValue;
    
    @FXML
    private TextField tfSearchValue;
    
    @FXML
    private FlowPane queueFlowPane;
    

    @FXML
    private VBox navigationBox;
    
    @FXML
    private HBox stepBox;
    
    @FXML
    private Button btnTraversal;
    

    @FXML
    private Button btnTraversalStop;

    

    
	public void setMainApp(MyApp mainapp) {
		this.myApp = mainapp;
	}
	
	
	
	
	public void setTree(GenericTree tree) {
		this.tree = tree;
		this.rootNode = this.tree.getRootNode();
		this.drawingPane.getChildren().add(this.rootNode);
		this.rootNode.setLayoutX(this.drawingPane.getPrefWidth()/2);
	}
	
	public void setFailBalance(boolean appear) {
        this.warningLabel.setVisible(appear);
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.warningLabel.setVisible(false);
		this.navigationBox.setVisible(false);
		this.stepBox.setVisible(false);
	}
	
//INSERT TAB PANE
	 @FXML
	 public void btnInsertPressed(ActionEvent event) {
		 this.btnInsertUndo.setVisible(true);
		 if(!Node.listValue.contains(Integer.parseInt(this.tfInsertChild.getText()))) {
			 Node childNode = new Node(Integer.parseInt(this.tfInsertChild.getText()));
			 if (tree.insertNode(Integer.parseInt(this.tfInsertParent.getText()), childNode)) {
				 this.historyInsert = childNode.getValue();
				 this.drawingPane.getChildren().add(childNode);
				 this.drawingPane.getChildren().add(childNode.getParentLine());
			 }
			 this.tfInsertChild.clear();
			 
		 }else {
			 this.tfInsertChild.clear();
			 JOptionPane.showMessageDialog(null, "Added node already exist!");
		 }
	 }
	 
	 @FXML
	 public void btnInsertUndoPressed(ActionEvent event) {
		 Node node = tree.searchNode(historyInsert);
		 this.drawingPane.getChildren().remove(node.getParentLine());
		 this.drawingPane.getChildren().remove(node);
		 tree.deleteNode(historyInsert);
		 this.btnInsertUndo.setVisible(false);
		 
		 Node parent = node.getParentNode();
		 parent.getChildNodes().remove(node);
	 }
	//DELETE TAB PANE
	 
	 @FXML
    public void btnDeleteNodePressed(ActionEvent event) {
		 if (Node.listValue.contains(Integer.parseInt(this.tfDeleteValue.getText()))) {
			 Node delNode = tree.searchNode(Integer.parseInt(this.tfDeleteValue.getText()));
        	this.removeFromPane(delNode);
        	tree.deleteNode(Integer.parseInt(this.tfDeleteValue.getText()));
        }else{
            JOptionPane.showMessageDialog(null, "Provided node not found!");
        }
    }
	
	void removeFromPane(Node removeNode) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(removeNode);
		Node currentNode;
		
		this.drawingPane.getChildren().remove(removeNode.getParentLine());
		this.drawingPane.getChildren().remove(removeNode);
		
		while(!queue.isEmpty()) {
			currentNode = queue.getFirst();
			
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
						this.drawingPane.getChildren().remove(node.getParentLine());
						this.drawingPane.getChildren().remove(node);
				}
			}
			queue.removeFirst();
		}
	}
	 
	//UPDATE TAB PANE
	@FXML
    public void buttonUpdatePressed(ActionEvent event) {
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
	
	
	//SEARCH TAB PANE
	@FXML
    void btnSearchPressed(ActionEvent event) {
    	Node searchedNode = tree.searchNode(Integer.parseInt(this.tfSearchValue.getText()));
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

	//TRAVERSAL TAB PANE

    @FXML
    void btnChoiceBFSPressed(ActionEvent event) {
    	this.choiceTraversal = new String("BFS");
    	this.queueFlowPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }

    @FXML
    void btnChoiceDFSPressed(ActionEvent event) {
    	this.choiceTraversal = new String("DFS");
    	this.queueFlowPane.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

    }
    
    
    @FXML
    void btnTraversalPressed(ActionEvent event) {
    	
    	this.navigationBox.setVisible(true);
    	
    	this.btnTraversal.setVisible(false);
    	
    	this.btnTraversalStop.setVisible(true);
    	
    	pseudoCode1.setVisible(true);
    	pseudoCode2.setVisible(true);
    	pseudoCode3.setVisible(true);
    	pseudoCode4.setVisible(true);

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
    void btnTraversalStopPressed(ActionEvent event) {
    	tree.getTimeline().stop();
    	tree.updateState();
    	
    	this.btnTraversalStop.setVisible(false);
    	this.navigationBox.setVisible(false);
    	this.btnTraversal.setVisible(true);

    	pseudoCode1.setVisible(false);
    	pseudoCode2.setVisible(false);
    	pseudoCode3.setVisible(false);
    	pseudoCode4.setVisible(false);
    	bfsPseudoCode.setVisible(false);
    	dfsPseudoCode.setVisible(false);
    	queueFlowPane.setVisible(false);
    	
    	
    	
    }
    

    

    @FXML
    void btnTraversalContinuePressed(ActionEvent event) {
    	this.stepBox.setVisible(false);
    	tree.getTimeline().play();
    }
    

    @FXML
    void btnTraversalPausePressed(ActionEvent event) {
    	tree.getTimeline().pause();
    	this.stepBox.setVisible(true);

    }
    
    
    
    
    

    @FXML
    void btnTraversalForwardPressed(ActionEvent event) {
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
    void btnTraversalBackwardPressed(ActionEvent event) {
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
				pseudoCode1.setTextFill(Color.LIGHTBLUE);
				pseudoCode2.setTextFill(Color.LIGHTBLUE);
				pseudoCode3.setTextFill(Color.WHITE);
				pseudoCode4.setTextFill(Color.WHITE);

			} else if (tree.getState() == 1) {
				pseudoCode3.setTextFill(Color.LIGHTBLUE);
				pseudoCode4.setTextFill(Color.LIGHTBLUE);
				pseudoCode1.setTextFill(Color.WHITE);
				pseudoCode2.setTextFill(Color.WHITE);
			}
    }
	

	//WINDOW ACTION
    @FXML
    void backToMain(ActionEvent event) {
    	myApp.switchToMainScreen();
    }
    
    @FXML
    void btnRedoPressed(ActionEvent event) {
    	Node rootNode = this.tree.getRootNode();
    	while(!rootNode.getChildNodes().isEmpty()) {
    		this.removeFromPane(rootNode.getChildNodes().getLast());
    		tree.deleteNode(rootNode.getChildNodes().getLast().getValue());
    	}
    	
    }
    
    
    

}