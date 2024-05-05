package algorithm;

import java.util.ArrayList;

public class Node {
	
	//INSTANCE ATTRIBUTES
	private int value;
	private ArrayList<Node> children ;
	
	//CONSTRUCTOR
	public Node(int value) {
		this.value = value;
		this.children = new ArrayList<Node>();
	}
	
	//getter and setter
	public void addNode (Node node) {
		children.add(node);
	}
	//GETTER
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}


	public ArrayList<Node> getChildren() {
		return children;
	}
	
	//class method
	
	public int getHeight() {								//chưa test
		if (this.isLeaf()) {
			return 0;
		}
		int maxHeight = 0;
		for (Node child : this.getChildren()) {
			int height = child.getHeight();
			maxHeight = Math.max(maxHeight, height) +  1;
		}
		return maxHeight;
	}
	
	
	
	public boolean isLeaf() {
		if(this.getChildren().size() == 0 ) {
			return true;
		}
		else return false;
	}

	
	
}
