package algorithm;

import java.util.ArrayList;

public class BalancedBinaryTree extends BalancedTree {
	
	public BalancedBinaryTree () {
		super();
	}
	public BalancedBinaryTree (int maxdiff) {
		super(maxdiff);
	}
	
	
	public void balanceTree() {
		if (this.checkTreeBalance()) return;
		ArrayList<Node> leaves = new ArrayList<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		//tìm tất cả các lá theo thứ tự BFS và add lần lượt vào trong một arraylist -> là được thêm vào cuối cùng sẽ là lá sâu nhất
		if (this.getRoot() == null) {
			return;
		}if (this.getRoot().isLeaf()) {
			leaves.add(this.getRoot());
		}
		queue.add(this.getRoot());
		while (queue.size()>0) {
			Node tmp = queue.remove(0);
			if(tmp.getChildren().size()>0) {
				for (Node child : tmp.getChildren()) {
					if(child.isLeaf()) leaves.add(child);
					queue.add(child);
				}
			}
			
		}
		
		while (!this.checkTreeBalance()) {
			Node moveleaf = leaves.get(leaves.size()-1);
			Node oldparent = this.searchParent(moveleaf);
			oldparent.getChildren().remove(moveleaf);
			
			ArrayList<Node> queue2 = new ArrayList<Node>();
			queue2.add(this.getRoot());
			while(queue2.size() > 0) {
				Node tmp2 = queue2.remove(0);
				if (tmp2.getChildren().size() <2) {
					tmp2.getChildren().add(moveleaf);
					break;
				}
				for (Node child : tmp2.getChildren()) {
					queue2.add(child);
				}
			}
		}
		
			
		
	}
	
	
	
}
