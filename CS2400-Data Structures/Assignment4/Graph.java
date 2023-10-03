import java.util.*;

// Adjacency List Representation
public class Graph {
	
	// ArrayList of LinkedLists
	ArrayList<LinkedList<Vertex>> adjacencyList;

	// Default constructor
	Graph() {
		adjacencyList = new ArrayList<>();
	} // end constructor

	//Method that adds a vertex to the graph
	public void addVertex(Vertex vertex) {
		LinkedList<Vertex> currentList = new LinkedList<>();
		currentList.add(vertex);
		adjacencyList.add(currentList);
	} // end addVertex
	
	// Method that adds an edge between two vertices
	public void addEdge(int from, int to) {
		LinkedList<Vertex> currentList = adjacencyList.get(from);
		Vertex toVertex = adjacencyList.get(to).get(0);
		currentList.add(toVertex);
	} // end addEdge
	
	// Checks if there is an edge between two vertices
	public boolean checkEdge(int from, int to) {
		LinkedList<Vertex> currentList = adjacencyList.get(from);
		Vertex toVertex = adjacencyList.get(to).get(0);

		for (Vertex Vertex : currentList) {
			if (Vertex == toVertex) {
				return true;
			} // end if
		} // end for
		return false;
	} // end checkEdge
	
	// Prints the graph as adjacency list
	public void print() {
		for (LinkedList<Vertex> currentList : adjacencyList) {
			for (Vertex Vertex : currentList) {
				System.out.print(Vertex.data + " -> ");
			} // end for
			System.out.println();
		} // end for
	} // end print

	// Obtain a list of neighbors of a specified vertex of this Graph
	public char[] neighbors(int vertex) {
		int count = 0;
		char[] answer;
		
		// loop that gets number of neighbors for array initialization
		for(Vertex Vertex : adjacencyList.get(vertex)) {
			count++;
		} // end for
		
		answer = new char[count];
		
		// reuse count variable
		count = 0;
		
		// saves each neighbor to array 'answer'
		// returns the answer array
		for(Vertex Vertex : adjacencyList.get(vertex)) {
			answer[count] = Vertex.data; 
			count++;
		} // end for
		
		return answer;
	} // end neighbors

} // end Graph
