package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class BinaryTree extends GenericTree {

	public BinaryTree()  {
		super();
	}

	public BinaryTree(Node node) {super(); }

	public void create(int numnode) {												//HAVE NOT TEST YET
		int length = 0;
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random random = new Random ();
		Random rd = new Random();
		
		while (generatedNumbers.size() < numnode) {
			int randomNumber = random.nextInt(100) ;
			if(!generatedNumbers.contains(randomNumber)) {					//distinct value of node in the tree
				length = generatedNumbers.size();
				generatedNumbers.add(randomNumber);							//add newnode value 
				
				if(this.getRoot()== null) {										// if the root is null, the first new node will be the root
					this.setRoot(new Node (randomNumber));
				}else {														//else add new node to random parent
					int parent;												// value of parent node
					int parentchild = 2;
					while(parentchild== 2) {
						parent = rd.nextInt(length);						//choose parent randomly
						Node parentNode = search(generatedNumbers.get(parent)) ;
						parentchild = parentNode.getChildren().size();
						if (parentchild < 2) {
							parentNode.getChildren().add(new Node(randomNumber));
						}
					}					
				}	

			}
		}

	}

	@Override
	public void insert(int parentvalue, int newnodevalue) {						//updated 
		Node parent = search(parentvalue);
		if (parent == null) {
			System.out.println("parent node is not exist");
			return;
		}else if (search(newnodevalue)  != null) {
			System.out.println("The new node you want to insert is already exist");
			return;
		}else if(parent != null && parent.getChildren().size() == 2){
			System.out.println("This node is already have 2 children");
			return;
		}else {
			parent.getChildren().add(new Node(newnodevalue));
			System.out.println("The new node is inserted successfylly");
			return;
		}
	}

	

}
