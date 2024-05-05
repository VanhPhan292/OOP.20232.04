package algorithm;

public interface TreeOperation {
	
	public void create(int numnode); // create a tree randomly with the number of node given, each node's value is random
	
	public void insert(int parentvalue, int newnodevalue); // insert the node to the parent node which identify by value
	
	public Node delete( int v); // delete the node determined by it's value
	
	public void update(int old, int newww); //change the value of the node 
	
	public void traverseDFS (); // using DFS
	
	public Node search(int value); //search the node in the tree using DFS
	
	public Node search(Node n);

}
