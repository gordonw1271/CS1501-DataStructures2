/**
 * A very very basic driver for CS1501 Project 1
 * @author    Dr. Farnan
 */
package cs1501_p1;

public class App {
	public static void main(String[] args) {
		BST tree = new BST();
		BST_Inter revTree = new BST();

		tree.put(50);
		tree.put(30);
		tree.put(20);
		tree.put(10);
		tree.put(25);
		tree.put(70);
		tree.put(90);
		tree.put(80);
		tree.put(110);
		tree.put(120);
		tree.put(115);

		//InOrderTraversal
		System.out.println("\n1) Testing InOrderTraversal: "+tree.inOrderTraversal());
		//Serelize
		System.out.println("\n2) Testing Serealize: " + tree.serialize());
		//Delete and Contains
		System.out.println("\n3) Testing Delete and Contains");
		System.out.println("Does tree contian: " + tree.contains(90));
		tree.delete(300);
		System.out.println("After deletion: " + tree.contains(90));
		System.out.println(tree.inOrderTraversal());
		//Height and Balance
		System.out.println("\n4) Testing Height and Balance");
		System.out.println("Tree Height: " + tree.height());
		System.out.println("Is tree balanced: " + tree.isBalanced());

		revTree = tree.reverse();
		System.out.println("\n5) Testing Reveerse: " + revTree.inOrderTraversal());
		System.out.println(revTree.serialize());
	}
}
