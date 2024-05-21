package algorithm;

import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import treeScreen.TreeScreenController;

public class GenericTree {
	private Node rootNode;
	private LinkedList<Node> queue;
	private LinkedList<Node> traveledNode;
	private Node currentNode;
	private Timeline timeline;
	private int state;
	private TreeScreenController controller;

	public GenericTree(Node node) {
		this.rootNode = node;
	}


	public Node searchNode(int nodeValue) {
		if (rootNode.getValue() == nodeValue) {
			return rootNode;
		}

		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(rootNode);
		Node currentNode;

		while(!queue.isEmpty()) {
			currentNode = queue.getFirst();

			if (!currentNode.getChildNodes().isEmpty()) { //neu current node co con
				for (Node node: currentNode.getChildNodes()) {
					if (node.getValue() == nodeValue) {
						return node;
					} else {
						queue.add(node);
					}
				}
			}
			queue.removeFirst();
		}
		return new Node(0);
	}

	public boolean insertNode(int parentValue, Node childNode) {
		Node parentNode = this.searchNode(parentValue);
		parentNode.addChild(childNode);
		return true;
	}
	public boolean deleteNode(int nodeValue) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(rootNode);
		Node currentNode;

		while(!queue.isEmpty()) {
			currentNode = queue.getFirst();

			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					if (node.getValue() == nodeValue) {
						currentNode.deleteChild(nodeValue);
						return true;
					} else {
						queue.add(node);
					}
				}
			}
			queue.removeFirst();
		}
		return false;
	}

	public Node getRootNode() {
		return rootNode;
	}


	public void backBFS() {
		if (state == 2) {
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					queue.removeLast();
					node.setState(0);
				}
			}
			timeline.setCycleCount(timeline.getCycleCount()+1);
			state = 1;
		} else if (state == 1) {
			queue.addFirst(currentNode);
			currentNode.setState(1);
			traveledNode.removeLast();
			currentNode = traveledNode.getLast();
			state = 2;
		}
	}
	public void forwardBFS() {
		if (state == 2) {
			if (!queue.isEmpty()) {
				currentNode = queue.getFirst();
				queue.removeFirst();
				traveledNode.add(currentNode);
				currentNode.setState(state);
				state = 1;
			} else {
				timeline.pause();
			}
		} else if (state == 1) {
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					queue.add(node);
					node.setState(state);
				}
			}
			state = 2;
		}
	}

	public void backDFS() {
		if (state == 2) {
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					queue.removeLast();
					node.setState(0);
				}
			}
			timeline.setCycleCount(timeline.getCycleCount()+1);
			state = 1;
		} else if (state == 1) {
			queue.add(currentNode);
			currentNode.setState(1);
			traveledNode.removeLast();
			currentNode = traveledNode.getLast();
			state = 2;
		}

	}

	public void forwardDFS() {
		if (state == 2) {
			if (!queue.isEmpty()) {
				currentNode = queue.getLast();
				queue.removeLast();
				traveledNode.add(currentNode);
				currentNode.setState(state);
				state = 1;
			} else {
				timeline.pause();
			}
		} else if (state == 1) {
			if (!currentNode.getChildNodes().isEmpty()) {
				LinkedList<Node> childReverse = new LinkedList<Node>();
				for (Node node: currentNode.getChildNodes()) {
					childReverse.addFirst(node);
				}
				for (Node node: childReverse) {
					queue.add(node);
					node.setState(state);
				}
			}
			state = 2;
		}
	}

	public void updateState() {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.add(rootNode);
		Node currentNode;

		while(!queue.isEmpty()) {
			currentNode = queue.getFirst();
			currentNode.setState(0);
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					queue.add(node);
				}
			}
			queue.removeFirst();
		}
	}


	public Timeline getTimeline() {
		return timeline;
	}

	public LinkedList<Node> getQueue() {
		return queue;
	}

	public void setQueue(LinkedList<Node> queue) {
		this.queue = queue;
	}

	public int getState() {
		return state;
	}

	public void setTraveledNode(LinkedList<Node> traveledNode) {
		this.traveledNode = traveledNode;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public void setState(int state) {
		this.state = state;
	}


	public LinkedList<Node> getTraveledNode() {
		return traveledNode;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public void setController(TreeScreenController controller) {
		this.controller = controller;
	}

	public TreeScreenController getController() {
		return controller;
	}
}
