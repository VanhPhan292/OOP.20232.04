package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class BalancedTree extends GenericTree {
	//instance attribute
	private int MAX_DIFF;
	
	//constructor
	public BalancedTree() {
		super();
		this.MAX_DIFF = 1;
	}
	public BalancedTree(int dif) {
		super();
		this.MAX_DIFF = dif;
	}
	
	//getter and setter
	public int getMAX_DIFF() {
		return MAX_DIFF;
	}
	
	

	//create: copy lại từ generictree + thực hiện cân bằng cây
	public void create(int qty) {
		int length = 0;
		ArrayList<Integer> generatedNumbers = new ArrayList<>();
		Random random = new Random ();
		Random rd = new Random();
		while (generatedNumbers.size() < qty) {
			int randomNumber = random.nextInt(100) ;
			if(!generatedNumbers.contains(randomNumber)) {
				length = generatedNumbers.size();
				generatedNumbers.add(randomNumber);
				
				if(this.getRoot() == null) {
					this.setRoot(new Node (randomNumber)) ;	
				}else {
					
					int parent = rd.nextInt(length);
					insert(generatedNumbers.get(parent),randomNumber);
					
				}	

			}
		}
	}
//	
	
	//insert: cho phép chèn một node bất kì sau đó thực hiện cân bằng nếu cây bị mất cân bằng
	//cập nhật exception sau
	public void insert(int p, int v) {
		Node parent = search(p);
		if (parent == null) {
			System.out.println("parent node is not exist");
			return;
		}else if (search(v) != null) {
			System.out.println("The new node you want to insert is already exist");
			return;
		}else {
			parent.getChildren().add(new Node(v));
			System.out.println("The new node is inserted successfylly");
		}
//		this.balanceTree();
	}


	//delete: cho phép xóa một node bất kì sau đó thực hiện cân bằng nếu cây bị mất cân bằng
	//cập nhật exception sau
	public Node delete (int v) {
		Node result = deleteHelper(this.getRoot(), v);
		this.balanceTree();
		return result;
	}


	public void balanceTree() {					// cân bằng cây
		while (!this.checkTreeBalance()) {
			Node cause = findCause();
			
			this.rotate(cause);
		}

	}
	
	private Node findCause () {		
		Node cause = null;
		
		//lấy tất cả các lá
		Node root = this.getRoot();
		ArrayList<Node> leaves = new ArrayList<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		if (root.isLeaf()) {
			leaves.add(root);
		}
		queue.add(root);
		Node tmp;
		while (queue.size() > 0) {
			tmp = queue.remove(0);
			if(!tmp.isLeaf()) {
				for(Node child : tmp.getChildren()) {
					if (child.isLeaf()) {
						leaves.add(child);
					}
					queue.add(child);
				}
			}
		}
		//từ danh sách các lá duyệt ngược lên trên để check node balance
		Node p;
		while (leaves.size() > 0) {
			tmp = leaves.remove(0);
			
			p = searchParent(tmp);			// p == null co the la root hoac khong tim thay
			
			if (!leaves.contains(p) && p != null) {
				leaves.add(p);
			}
			
			if (tmp.isLeaf()) {
				continue;
			}else if (!this.checkNodeBalance(tmp)){
				cause = tmp;
				break;										// tìm nguyên nhân thấp nhất 
			}
		}
		
		return cause;
	}

	private boolean checkNodeBalance(Node r) {				
		
		if (r.isLeaf()) {
			return true;
			
		}else if (r.getChildren().size() == 1 ) {
			int h = r.getChildren().get(0).getHeight(); 
			if (h > MAX_DIFF - 1) {
				return false;
			}else return true;
		}else {
			int highest = 0 ;
			int lowest = 1000;
			for (Node child: r.getChildren()) {
				
				if (child.getHeight() > highest) {
					highest = child.getHeight();
				}
				if (child.getHeight() < lowest) {
					lowest = child.getHeight();
				}
			}
			
			if(Math.abs(highest - lowest) > MAX_DIFF ) {
				return false;
			}else {
				return true;
			}
		}
	}

	protected void rotate(Node cause) {
		
		//tìm kiếm nút con cao nhất của cause -> hoán đổi nó với nút cause
		Node highestchild = cause.getChildren().get(0);
		for (Node child : cause.getChildren()) {
			if(child.getHeight() > highestchild.getHeight()) {
				highestchild = child;
			}
		}
		
		//case1 : nút gây mất cân bằng (cause)  là root

		if (cause.equals(this.getRoot())) {
			this.setRoot(highestchild);
			cause.getChildren().remove(highestchild);
			highestchild.getChildren().add(cause);
			
		//case2: nút gây mất cân bằng (cause) KHÔNG phải là root
		}else {	
			//tìm cha của cause để thực hiện trỏ 
			Node causeparent = this.searchParent(cause);
			causeparent.getChildren().add(highestchild);
			causeparent.getChildren().remove(cause);
			cause.getChildren().remove(highestchild);
			highestchild.getChildren().add(cause);
		}

	}
	

public boolean checkTreeBalance() {
		
		ArrayList<Node> leaves = new ArrayList<Node>();
		ArrayList<Node> queue = new ArrayList<Node>();
		
		if (this.getRoot() == null) {
			return true;
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
		int maxdepth = 0;
		int mindepth = 1000;
		for (Node leaf : leaves) {
			int d = this.getDepth(leaf);
			if (d > maxdepth) {
				maxdepth = d;
			}
			if (d < mindepth) {
				mindepth = d;
			}
		}
		if (Math.abs(maxdepth - mindepth) > this.getMAX_DIFF()) {
			return false;
		}else return true;
	}


}
