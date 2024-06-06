package algorithm;

import java.util.LinkedList;

import controller.TreeSceneController;
import javafx.animation.Timeline;


public class GenericTree {
	private Node rootNode;

	private LinkedList<Node> ll;

	private LinkedList<Node> traveledNode;

	private Node currentNode;

	private Timeline timeline;

	private int state;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	private TreeSceneController controller;

	public GenericTree(Node node) {
		this.rootNode = node;
	}


	public Node searchNode(int nodeValue) {
		if (rootNode.getValue() == nodeValue) {
			return rootNode;
		}

		LinkedList<Node> ll = new LinkedList<Node>();
		ll.add(rootNode);
		Node currentNode;

		while(!ll.isEmpty()) {
			currentNode = ll.getFirst();

			if (!currentNode.getChildNodes().isEmpty()) { //neu current node co con
				for (Node node: currentNode.getChildNodes()) {
					if (node.getValue() == nodeValue) {
						return node;
					} else {
						ll.add(node);
					}
				}
			}
			ll.removeFirst();
		}
		return new Node(0);
	}

	public boolean insertNode(int parentValue, Node childNode) {
		Node parentNode = this.searchNode(parentValue);
		parentNode.addChild(childNode);
		return true;
	}
	public boolean deleteNode(int nodeValue) {
		LinkedList<Node> ll = new LinkedList<Node>();
		ll.add(rootNode);
		Node currentNode;

		while(!ll.isEmpty()) {
			currentNode = ll.getFirst();

			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					if (node.getValue() == nodeValue) {
						currentNode.deleteChild(nodeValue);
						return true;
					} else {
						ll.add(node);
					}
				}
			}
			ll.removeFirst();
		}
		return false;
	}

	public void backBFS() {
		if (state == 2) {
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					ll.removeLast();
					node.setState(0);
				}
			}
			timeline.setCycleCount(timeline.getCycleCount()+1);
			state = 1;
		} else if (state == 1) {
			ll.addFirst(currentNode);
			currentNode.setState(1);
			traveledNode.removeLast();
			currentNode = traveledNode.getLast();
			state = 2;
		}
	}
	public void forwardBFS() {
		if (state == 2) {
			if (!ll.isEmpty()) {
				currentNode = ll.getFirst();
				ll.removeFirst();
				traveledNode.add(currentNode);
				currentNode.setState(state);
				state = 1;
			} else {
				timeline.pause();
			}
		} else if (state == 1) {
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					ll.add(node);
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
					ll.removeLast();
					node.setState(0);
				}
			}
			timeline.setCycleCount(timeline.getCycleCount()+1);
			state = 1;
		} else if (state == 1) {
			ll.add(currentNode);
			currentNode.setState(1);
			traveledNode.removeLast();
			currentNode = traveledNode.getLast();
			state = 2;
		}

	}

	public void forwardDFS() {
		if (state == 2) {
			if (!ll.isEmpty()) {
				currentNode = ll.getLast();
				ll.removeLast();
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
					ll.add(node);
					node.setState(state);
				}
			}
			state = 2;
		}
	}

	public void updateState() {
		LinkedList<Node> ll = new LinkedList<Node>();
		ll.add(rootNode);
		Node currentNode;

		while(!ll.isEmpty()) {
			currentNode = ll.getFirst();
			currentNode.setState(0);
			if (!currentNode.getChildNodes().isEmpty()) {
				for (Node node: currentNode.getChildNodes()) {
					ll.add(node);
				}
			}
			ll.removeFirst();
		}
	}

	public Node getRootNode() {
		return rootNode;
	}

	public LinkedList<Node> getLl() {
		return ll;
	}

	public void setLl(LinkedList<Node> ll) {
		this.ll = ll;
	}

	public LinkedList<Node> getTraveledNode() {
		return traveledNode;
	}

	public void setTraveledNode(LinkedList<Node> traveledNode) {
		this.traveledNode = traveledNode;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public Timeline getTimeline() {
		return timeline;
	}

	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}

	public TreeSceneController getController() {
		return controller;
	}

	public void setController(TreeSceneController treeSceneController) {
		this.controller = treeSceneController;
	}
	
	
	public LinkedList<Node> getQueue() {
		return ll;
	}
	public void setQueue(LinkedList<Node> queue) {
		this.ll = queue;
	}
}
