/**
 * A driver for CS1501 Project 3
 * @author    Dr. Farnan
 */
package cs1501_p3;

public class App {
	public static void main(String[] args) {
		CarsPQ cpq = new CarsPQ("/workspaces/project3-gordonw1271/app/src/test/resources/cars.txt");
		cpq.add(new Car("PUAF85WU5R6L6H1P9","Ford","Fiesta",0,14,"Red"));
	}
}
