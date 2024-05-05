package algorithm;

public class TestBinaryTree {
	public static void main(String[] args) {
		BinaryTree bt = new BinaryTree();
		bt.create(10);
		
		TreeAction.printTree(bt);
	}
	
}
