
public class Main {

	public static void main(String[] args) {
		
		//Initializes graph and vertices
		Graph graph = new Graph();
		graph.addVertex(new Vertex('A')); // index 0
		graph.addVertex(new Vertex('B')); // index 1
		graph.addVertex(new Vertex('C')); // index 2
		graph.addVertex(new Vertex('D')); // index 3
		graph.addVertex(new Vertex('E')); // index 4
		graph.addVertex(new Vertex('F')); // index 5
		graph.addVertex(new Vertex('G')); // index 6
		graph.addVertex(new Vertex('H')); // index 7
		graph.addVertex(new Vertex('I')); // index 8

		//Adds edges between vertices
		graph.addEdge(0, 1); // A -> B
		graph.addEdge(0, 3); // A -> D
		graph.addEdge(0, 4); // A -> E

		graph.addEdge(1, 4); // B -> E
		graph.addEdge(2, 1); // C -> B
		graph.addEdge(3, 6); // D -> G

		graph.addEdge(4, 7); // E -> H
		graph.addEdge(4, 5); // E -> F

		graph.addEdge(5, 7); // F -> H
		graph.addEdge(5, 2); // F -> C

		graph.addEdge(6, 7); // G -> H
		graph.addEdge(7, 8); // H -> I
		graph.addEdge(8, 5); // I -> F

//		Graph:
//		(A) → (D) → (G)
//		 ↓  ➘        ↓
//		(B) → (E) → (H)
//		 ↑     ↓  ➚  ↓
//		(C) ← (F) ← (I)

		// Prints the adjacency list representation of the graph
		graph.print();
		System.out.println();
		
		//Gets array returned by graph's neighbor() method
		char[] neighbors = graph.neighbors(4);
		printNeighbors(neighbors);
	}
	//Method to print neighbor array with a certain format
	static void printNeighbors(char[] neighbors) {
		boolean isFirst = true;
		for (char neighbor : neighbors) {
			if (isFirst) {
				System.out.printf("Neighbors of %c: ", neighbor);
				isFirst = false;
			} else
				System.out.print(neighbor + " ");
		}
	}
}
