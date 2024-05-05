package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class GenericTree implements TreeOperation {
	//INSTANCE ATTRIBUTE
	private Node root;

	//CONSTRUCTOR
	public GenericTree() {
		root = null;
	}
	//GETTER AND SETTER
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}

	

	//TREE OPERATIONS*************************************************************************************************************************
	
	@Override
	public void create(int qty) {												//HAVE NOT TEST YET
		int length = 0;
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random random = new Random ();
		Random rd = new Random();
		while (generatedNumbers.size() < qty) {
			int randomNumber = random.nextInt(100) ;
			if(!generatedNumbers.contains(randomNumber)) {
				length = generatedNumbers.size();
				generatedNumbers.add(randomNumber);
				
				if(this.getRoot() == null) {
					this.setRoot(new Node (randomNumber)) ;	
				}else {
					
					int parent = rd.nextInt(length);
					insert(generatedNumbers.get(parent),randomNumber);
					
				}	

			}
		}

	}
	

	@Override
	public void insert(int p, int v) {						
		Node parent = search(p);
		if (parent == null) {
			System.out.println("parent node is not exist");
			return;
		}else if (search( v) != null) {
			System.out.println("The new node you want to insert is already exist");
			return;
		}else {
			parent.getChildren().add(new Node(v));
			System.out.println("The new node is inserted successfylly");
		}
		
		
	}

	@Override
	//delete a node
	//if this node is leaf, just make this node become null
	//if this node is not leaf, the first child of this node will replace it
	
	public Node delete(int v) {
		return deleteHelper(this.getRoot(), v);
	}
	protected Node deleteHelper(Node r , int v) {
		Node result = null;
		if (this.getRoot() == null) {
			return null;
		}
		else if (r != null && r.getValue() == v) {
			result = r;
			if (r.isLeaf()) {
				r = null;
				return result ;
			}
			Node tmp = r.getChildren().get(0);
			for (Node child : tmp.getChildren()) {
				r.addNode(child);
			}
			r.setValue(tmp.getValue());
			r.getChildren().remove(tmp);
			return result;
		}else {
			for (Node child : r.getChildren()) {
				result  = deleteHelper(child,v);
				return result;
			}
		}
		return result;
		
	}

	@Override
	public void update(int old, int newww) {							//have not tested
		Node upt = search(old);
		if (upt == null) {
			System.out.println("This node is not found");
		}else {
			upt.setValue(newww);
			System.out.println("This node is updated successfully");
		}
		
		
	}

	@Override
	public void traverseDFS() {
		traverseDFSHelper(this.getRoot());
	}
	public void traverseDFSHelper(Node n) {								//tested
		if (this.getRoot()== null) {
			return;
		}else if (n == null) {
			return;
		}else {
			System.out.println(n.getValue());
			for (Node child : n.getChildren()) {
				traverseDFSHelper(child);
			}
		}
	}

	@Override
	public Node search(int value) {
		return searchHelper(this.getRoot(), value);
	}
	
	@Override
	public Node search(Node n) {
		return searchHelper(this.getRoot(), n.getValue());
	}

	private Node searchHelper(Node root, int value) {							//tested
		if(this.getRoot() == null) {
			return null;
		}else if (root == null){
			return null;
		}else if (root != null && root.getValue() == value ) {
			return root;
		}else {
			for (Node child : root.getChildren()) {
				Node result = searchHelper(child, value);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	
	//other operation
	
	public Node searchParent(Node finding) {
		return searchParentHelper(this.getRoot(), finding);
	}
	private Node searchParentHelper(Node n, Node finding ) {				
		Node result = null;
		if (this.getRoot() == finding) {
			return null;
		}
		for (Node child : n.getChildren()) {
			if (child == finding) {
				return n;
			}
		}
		for (Node child : n .getChildren()) {
			result = searchParentHelper(child, finding);
			if (result != null) {
				return result;
			}
		}
		return result;
	}
	
	

	
	public int getDepth(Node n) {
		if (n == null) {
			return 0;
		}
		int depth = 0;
		if (n.equals(this.getRoot())) {
			return depth;
		}
		Node tmp = n;
		while(!tmp.equals(this.getRoot())) {
			tmp = this.searchParent(tmp);
			depth +=1;
			if(tmp.equals(this.getRoot())) {
				return depth;
			}
		}
		return depth;
		
	}
	
}
