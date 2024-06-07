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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.AlertUtil;

public class TreeSceneController implements Initializable {
	
	private MyApp myApp;
	
	private GenericTree tree;
	
	

	
    private StackPane rootNode;
    
    private String choiceTraversal = new String("BFS");
    
    private int historyInsert;
    
    @FXML
    private Label bfsPseudoCode1;

    @FXML
    private Label bfsPseudoCode2;

    @FXML
    private Label bfsPseudoCode3;

    @FXML
    private Label bfsPseudoCode4;
    
    @FXML
    private AnchorPane BFScode;

    @FXML
    private AnchorPane DFScode;
    
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
		this.DFScode.setVisible(false);
		this.BFScode.setVisible(false);
	}
	
//INSERT TAB PANE
	 @FXML
	 public void btnInsertPressed(ActionEvent event) {
		 try {
			 String parentNodeValue = this.tfInsertParent.getText();
			 String childNodeValue = this.tfInsertChild.getText();
			 if(parentNodeValue.isEmpty() || childNodeValue.isEmpty()) {
				 throw new IllegalArgumentException("Parent value or child value is empty");
			 }
			 this.btnInsertUndo.setVisible(true);
			 if(!Node.listValue.contains(Integer.parseInt(childNodeValue))) {
				 Node childNode = new Node(Integer.parseInt(childNodeValue));
				 if (tree.insertNode(Integer.parseInt(parentNodeValue), childNode)) {
					 this.historyInsert = childNode.getValue();
					 this.drawingPane.getChildren().add(childNode);
					 this.drawingPane.getChildren().add(childNode.getParentLine());
				 }
				 this.tfInsertChild.clear();
				 
			 }else {
				 this.tfInsertChild.clear();
				 AlertUtil.warning("Insertion Error", "Added node already exist!");
			 }
		 
			 
		 }catch(NumberFormatException e) {
			 AlertUtil.warning("Insertion Error", "Please enter integer value");
			 
		 }catch(IllegalArgumentException e ) {
			 AlertUtil.warning("Insertion Error", e.getMessage());
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
		 try {
			 String deleteNodeValue = this.tfDeleteValue.getText();
			 if(deleteNodeValue.isEmpty()) {
				 throw new IllegalArgumentException("Detele Node Value is empty");

			 }
			 
			 if (Node.listValue.contains(Integer.parseInt(deleteNodeValue))) {
				 Node delNode = tree.searchNode(Integer.parseInt(this.tfDeleteValue.getText()));
				 this.removeFromPane(delNode);
				 tree.deleteNode(Integer.parseInt(this.tfDeleteValue.getText()));
			 }else{
				 AlertUtil.warning("Deletion Error", "Provided node not found!");
			 }
			 
	 	}catch(NumberFormatException e) {
			 AlertUtil.warning("Deletion Error", "Please enter integer value");
			 
	 	}catch(IllegalArgumentException e ) {
			 AlertUtil.warning("Deletion Error", e.getMessage());
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
		try {
			 String oldNodeValue = this.tfUpdateOldValue.getText();
			 String newNodeValue = this.tfUpdateNewValue.getText();
			 if(oldNodeValue.isEmpty() || newNodeValue.isEmpty()) {
				 
				 throw new IllegalArgumentException("Detele Node Value is empty");

			 }
			 if (Node.listValue.contains(Integer.parseInt(oldNodeValue))) {
		            if (!Node.listValue.contains(Integer.parseInt(newNodeValue))) {
			            Node oldNode = tree.searchNode(Integer.parseInt(newNodeValue));
			            oldNode.setValue(Integer.parseInt(this.tfUpdateNewValue.getText()));    
		            } else {
		   			 AlertUtil.warning("Updation Error", "This new node value is not avalable");

		            }
		        } else {
		   			 AlertUtil.warning("Updation Error", "The old node is not found in this tree");

		        }
			 
			 
			 
	 	}catch(NumberFormatException e) {
			 AlertUtil.warning("Updation Error", "Please enter integer value");
			 
	 	}catch(IllegalArgumentException e ) {
			 AlertUtil.warning("Updation Error", e.getMessage());
	 	}
		
    }
	
	
	//SEARCH TAB PANE
	@FXML
    void btnSearchPressed(ActionEvent event) {
		try {
			 String searchNodeValue = this.tfSearchValue.getText();
			 if(searchNodeValue.isEmpty()) {
				 
				 throw new IllegalArgumentException("Searching Node Value is empty");

			 }
			 Node searchedNode = tree.searchNode(Integer.parseInt(searchNodeValue));
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
		                                AlertUtil.warning("Search Error", "Searching node is not found");
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
			 
			 
			 
			 
	 	}catch(NumberFormatException e) {
			 AlertUtil.warning("Search Error", "Please enter integer value");
			 
	 	}catch(IllegalArgumentException e ) {
			 AlertUtil.warning("Search Error", e.getMessage());
	 	}
    	
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
    		BFScode.setVisible(true);
    		traversalBFS();
    		
    	}else if (choiceTraversal.equals("DFS")) {
    		DFScode.setVisible(true);
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
    	BFScode.setVisible(false);
    	DFScode.setVisible(false);
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
	    	codeUpdateBFS();
	    	tree.forwardBFS();
	    	queueUpdate();
    	} else if (this.choiceTraversal.equals("DFS")) {
    		codeUpdateDFS();
    		tree.forwardDFS();
    		queueUpdate();
    	}
    }
    
    @FXML
    void btnTraversalBackwardPressed(ActionEvent event) {
    	if (this.choiceTraversal.equals("BFS")) {
	    	codeUpdateBFS();
	    	tree.backBFS();
	    	queueUpdate();
    	} else if (this.choiceTraversal.equals("DFS")) {
    		codeUpdateDFS();
    		tree.backDFS();
    		queueUpdate();
    	}
    }


    void traversalBFS() {
    	
    	KeyFrame step = new KeyFrame(Duration.seconds(1), 
				new EventHandler<ActionEvent>() {
			  		public void handle(ActionEvent event) {
			  			codeUpdateBFS();
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
    					codeUpdateDFS();
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
    
    void codeUpdateBFS() {
    	if (tree.getState() == 2) {
    		bfsPseudoCode1.setTextFill(Color.BLUE);
    		bfsPseudoCode2.setTextFill(Color.BLUE);
    		bfsPseudoCode3.setTextFill(Color.BLACK);
    		bfsPseudoCode4.setTextFill(Color.BLACK);

		} else if (tree.getState() == 1) {
			bfsPseudoCode3.setTextFill(Color.BLUE);
			bfsPseudoCode4.setTextFill(Color.BLUE);
			bfsPseudoCode1.setTextFill(Color.BLACK);
			bfsPseudoCode2.setTextFill(Color.BLACK);
		}
    }

    
    void codeUpdateDFS() {
    	if (tree.getState() == 2) {
				pseudoCode2.setTextFill(Color.BLUE);
				pseudoCode3.setTextFill(Color.BLUE);
				pseudoCode4.setTextFill(Color.BLACK);
				pseudoCode5.setTextFill(Color.BLACK);

			} else if (tree.getState() == 1) {
				pseudoCode4.setTextFill(Color.BLUE);
				pseudoCode5.setTextFill(Color.BLUE);
				pseudoCode2.setTextFill(Color.BLACK);
				pseudoCode3.setTextFill(Color.BLACK);
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