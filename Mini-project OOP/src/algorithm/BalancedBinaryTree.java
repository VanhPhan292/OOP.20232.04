package algorithm;

import javax.swing.*;

public class BalancedBinaryTree extends BalancedTree{

	public BalancedBinaryTree(Node node, int maximumDiff){
		super(node,maximumDiff);
	}

	public boolean insertNode(int parentValue, Node childNode) {
		Node parentNode = this.searchNode(parentValue);
		if (parentNode.getChildNodes().size() < 2){
			parentNode.addChild(childNode);
			checkBalance();
			return true;
		} else {
			Node.listValue.remove(Node.listValue.indexOf(childNode.getValue()));
			JOptionPane.showMessageDialog(null, "Node can not have more than 2 child nodes!");
			checkBalance();
			return false;
		}
	}
}
