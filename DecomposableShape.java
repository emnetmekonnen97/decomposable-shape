import java.awt.Polygon; 
import java.util.Scanner;

/**
 * Name:Emnet Mekonnen 
 * Date: 4/17/2024 
 * CSC 202 
 * Project 3-DecomposableShape.java
 * 
 * This class stores a list x/y coordinates that, when connected end to end form
 * a shape. The least important nodes can sequentially be removed from the shape
 * and restored to the list of x/y coordinates.
 * 
 * Citations of Assistance (who and what OR declare no assistance): Robel helped
 * me think about the toString method of the decomposable shape class
 * Mohamed helped me with thinking about when we need to remove front of the list
 * 
 */

public class DecomposableShape {
	private int startPoints;
	private int currentPoints;
	private PointNode front;
	private StackADT<PointNode> removedPointsStack;

	/**
	 * Constructs a DecomposableShape that is picked by the user
	 * 
	 * @param input -- file chosen by user
	 */
	public DecomposableShape(Scanner input) {
		this.front = null;
		PointNode current = front;
		removedPointsStack = new ArrayStack<>();

		while (input.hasNextLine()) {
			PointNode node = getNode(input);

			if (front == null) {
				front = node;
				current = node;
			} else {
				node.prev = current;
				current.next = node;
				current = node;
			}

			startPoints++;
		}
		current.next = front;
		front.prev = current;

		do {
			current.calculateImportance();
			current = current.next;
		} while (current.next != front);

		currentPoints = startPoints;

	}

	/**
	 * Reads the x and y values from the file passed through the input
	 * 
	 * @param input -- file chosen by user
	 * @return node -- the node constructed with the x and y values from the file
	 */
	private PointNode getNode(Scanner input) {
		String line = input.nextLine();
		line = line.strip();
		String[] data = line.split(",");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		PointNode node = new PointNode(x, y);
		return node;
	}

	/**
	 * Makes a polygon from arrays of all the x and y points currently in the shape
	 * 
	 * @return -- a polygon made from the x and y values of the points
	 */
	public Polygon toPolygon() {
		int[] xPoints = new int[currentPoints];
		int[] yPoints = new int[currentPoints];
		int nPoints = 0;
		PointNode current = front;
		
		while(nPoints < currentPoints) {
			xPoints[nPoints] = current.x;
			yPoints[nPoints] = current.y;
			current = current.next;
			nPoints++;
		}


		Polygon shape = new Polygon(xPoints, yPoints, currentPoints);

		return shape;
	}

	/**
	 * Finds the least important node in the shape by calculating the lowest
	 * importance
	 * 
	 * @return -- the node with the least importance
	 */
	private PointNode findLeastImportantNode() {
		PointNode leastImportantNode = front;
		PointNode current = front;
		double lowestImportance = Double.MAX_VALUE;
		do {
			if (current.importance < lowestImportance) {
				lowestImportance = current.importance;
				leastImportantNode = current;
			}
			current = current.next;
		} while (current != front);

		return leastImportantNode;
	}

	/**
	 * Adds the node to the shape from the removedPointsStack
	 */
	private void addNode() {
		if(!removedPointsStack.isEmpty()) {
			PointNode removedNode = removedPointsStack.pop();
			removedNode.prev.next = removedNode;
			removedNode.next.prev = removedNode;
			removedNode.next.calculateImportance();
			removedNode.prev.calculateImportance();
		}


	}

	/**
	 * Removes the least important node from the shape and adds it to the removedPointsStack
	 */
	private void removeNode() {
		PointNode leastImportantNode = findLeastImportantNode();
		removedPointsStack.push(leastImportantNode);
		
		if(leastImportantNode == front) {
			front = front.next;
		}
		
		leastImportantNode.prev.next = leastImportantNode.next;
		leastImportantNode.next.prev = leastImportantNode.prev;

		leastImportantNode.next.calculateImportance();
		leastImportantNode.prev.calculateImportance();
		
	
	}

	/**
	 * Calculates the number of nodes that need to be removed and added to the
	 * shape
	 * 
	 * @param target -- the target amount of nodes that need to be removed or added
	 */
	public void setToSize(int target) {
		int targetPoints = (int) (target / 100.0 * startPoints);

		int pointsToRemove = (int) (currentPoints - targetPoints);
		int pointsToAdd = (int) (targetPoints - currentPoints);

		if (targetPoints < currentPoints) {
			for (int i = 0; i < pointsToRemove; i++) {
				removeNode();
				currentPoints--;
			}
		} else if(currentPoints < targetPoints){
			for (int i = 0; i < pointsToAdd; i++) {
				addNode();
				currentPoints++;
			}
		}

	}
	
	/**
	 * Returns a string consisting of the x and y values and importance of each node in the circular doubly linked list
	 */
	public String toString() {
		PointNode current = front;
		String dataString = current.toString();
		while (current.next != front) {
			current = current.next;
			dataString += "\n" + current.toString();
		}
		return dataString;

	}

	private static class PointNode {
		private int x;
		private int y;
		private double importance;
		private PointNode prev;
		private PointNode next;

		/**
		 * Constructs a point node from desired parameters
		 * 
		 * @param x -- x coordinate of the point node
		 * @param y -- y coordinate of the point node
		 */
		public PointNode(int x, int y) {
			this.x = x;
			this.y = y;
			this.importance = 0;
			this.prev = null;
			this.next = null;
		}

		/**
		 * Calculates the distance between two nodes
		 * @param node1 -- the first node
		 * @param node2 -- the second node 
		 * @return -- the distance between two nodes
		 */
		private double calculateDistanceBetweenNodes(PointNode node1, PointNode node2) {
			return Math.sqrt(Math.pow(node2.x - node1.x, 2) + Math.pow(node2.y - node1.y, 2));
		}

		/**
		 * Calculates the importance of the each node using the distance between each node
		 */
		public void calculateImportance() {
			double lp = calculateDistanceBetweenNodes(this.prev, this);
			double lr = calculateDistanceBetweenNodes(this.prev, this.next);
			double pr = calculateDistanceBetweenNodes(this, this.next);

			this.importance = lp + pr - lr;

		}

		/**
		 * Returns a string consisting of the x and y values and importance of a point node
		 */
		public String toString() {
			return "x = " + this.x + ", y = " + this.y + ", importance = " + this.importance;
		}
	}

}
