package algorithm;

public class TestBalanceTree {
	public static void main (String[] args) {
		
		
		
		
		
		BalancedTree bt = new BalancedTree();
		bt.setRoot(new Node(100));
		bt.insert(100, 200);
		bt.insert(100, 250);
		bt.insert(200, 300);
		bt.insert(300,400);


	
		bt.balanceTree();
		TreeAction.printTree(bt);
		System.out.println(bt.checkTreeBalance());
		
		
		
	}
	
	
	
}
