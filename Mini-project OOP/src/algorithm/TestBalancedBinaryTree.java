package algorithm;

public class TestBalancedBinaryTree {
	public static void main (String[] args) {
		BalancedBinaryTree bbt = new BalancedBinaryTree(1);
		bbt.setRoot(new Node(100));
		bbt.insert(100, 200);
		bbt.insert(100, 250);
		bbt.insert(200, 300);
		bbt.insert(300,400);
		
		TreeAction.printTree(bbt);
		System.out.println(bbt.checkTreeBalance());
		bbt.balanceTree();
		TreeAction.printTree(bbt);
		System.out.println(bbt.checkTreeBalance());
		
		
	}
	
	
}
