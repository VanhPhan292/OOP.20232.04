package algorithm;

public class TreeAction {
	public static void printTree(GenericTree gt) {
		printTreeHelper(gt.getRoot(), 0);
	}
	private static void printTreeHelper(Node r, int depth ) {
		if  (r == null) {
			return;
		}
		System.out.print("   ".repeat(depth));
		System.out.println(r.getValue());
		for(Node child : r.getChildren()) {
			printTreeHelper(child,depth + 1);
		}
			
	}
}
