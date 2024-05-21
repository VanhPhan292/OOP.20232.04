package algorithm;

import java.util.LinkedList;
import java.util.Queue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;

public class BinaryTree extends GenericTree {

	public BinaryTree(Node node) {
		super(node);
	}

	// override
	public boolean insertNode(int parentValue, Node childNode) {
		Node parentNode = this.searchNode(parentValue);
		if (parentNode.getChildNodes().size() < 2){
			parentNode.addChild(childNode);
			return true;
		} else {
			Node.listValue.remove(Node.listValue.indexOf(childNode.getValue()));
			JOptionPane.showMessageDialog(null, "Node can not have more than 2 child nodes!");
			return false;
		}

	}

}




