
package COMP424;
import java.util.*;

/* DANA, Luna (260857641)
 
 COMP424 - Assignment 1
 Question 1a)
 
 METHODS :
 - FindNeigbors : find the neigbors of a state
 - BFS : solve the puzzle with breadth first search with a Queue
 - DFS : solve the puzzle with depth first search with a Stack
 - IDDFS : Iterative Deepening
 - EqualStates : check if two states are the same
 - PrintPath : print the path from the start state to the goal state
 - Main : main method where operations are made
 
 CLASSES RELATED :
 - Node (each node is a state)
 
*/

public class Puzzle {

	// Method to find where the 0 is, the 0 represent the hole in the puzzle.
	public ArrayList<Node> FindNeigbors(Node Parent) {
		int x = -1 ,y = -1 ;
		for (int i = 0 ; i < Parent.State.length; i++) {
		    for(int j = 0 ; j < Parent.State[0].length ; j++) {
		         if (Parent.State[i][j] == 0) {
		         x = i;
		         y = j;  
		              break;
		         }
		    }
		}

		// FIND THE CHILDREN (THE NEIGHBORS OF A STATE)
		ArrayList<Node> Children = new ArrayList<>();
		if (x==1) {
			Node Child1 = new Node(Parent.State, Parent, false, 0,-1);
			Child1.State[x][y] = Child1.State[x-1][y];
			Child1.State[x-1][y] = 0;	
			Child1.Parent_depth = Parent.Parent_depth +1;
			Child1.Piece_Moved = Child1.State[x][y];
			Children.add(Child1);
		} 
		if (x==0) {
			Node Child3 = new Node(Parent.State, Parent, false, 0,-1);
			Child3.State[x][y] = Child3.State[x+1][y];
			Child3.State[x+1][y] = 0;
			Child3.Parent_depth = Parent.Parent_depth +1;
			Child3.Piece_Moved = Child3.State[x][y];
			Children.add(Child3);
		}
		if (y!=0) {
			Node Child2 = new Node(Parent.State, Parent, false, 0,-1);
			Child2.State[x][y] = Child2.State[x][y-1];
			Child2.State[x][y-1] = 0;
			Child2.Parent_depth = Parent.Parent_depth +1;
			Child2.Piece_Moved = Child2.State[x][y];
			Children.add(Child2);
		} 
		if (y!=2) {
			Node Child4 = new Node(Parent.State, Parent, false, 0,-1);
			Child4.State[x][y] = Child4.State[x][y+1];
			Child4.State[x][y+1] = 0;
			Child4.Parent_depth = Parent.Parent_depth +1;
			Child4.Piece_Moved = Child4.State[x][y];
			Children.add(Child4);

		} 
	return Children;	
		
	}
		
	
public ArrayList<Node> BFS(Node startstate) {
		
	    // ------------------------------------------------
		// Initialization of the data structures
		// ------------------------------------------------
		ArrayList<Node> Discovered = new ArrayList<Node>(); 
		Queue<Node> BFS_Queue = new LinkedList<Node>(); 
		ArrayList<Node> Path = new ArrayList<Node>(); 
		// ------------------------------------------------

		BFS_Queue.add(startstate);
		Discovered.add(startstate);

		while (BFS_Queue.size() != 0) { 
            Node temp = BFS_Queue.poll();
			Discovered.add(temp);
     
            // IF GOAL STATE IS FOUND
            if (temp.IsGoalState() == true){
            	Node toReturn = temp;
            	while(temp.Parent != null) {
            		Path.add(temp.Parent);
            		temp = temp.Parent;
            	}
        		Path.add(0,toReturn);
            	return Path;
            }
            
            ArrayList<Node> temp_children = FindNeigbors(temp);
            temp_children.sort((Node z1, Node z2) -> {
 			   if (z1.Piece_Moved > z2.Piece_Moved)
 			     return 1;
 			   if (z1.Piece_Moved < z2.Piece_Moved)
 			     return -1;
 			   return 0;
 			});
            
            for (Node child : temp_children) {
            	int counter = 0;
    			for (Node disco : Discovered) {
    				if(EqualStates(child.State, disco.State)) {
    					break;
    				}
    				if(counter == Discovered.size()-1) {
    					BFS_Queue.add(child);	

    				}
    				counter++;
    			}
    		}  
		}
		
		return null;
		}


public ArrayList<Node> DFS(Node startstate) {
	
	// ------------------------------------------------
	// Initialization of the data structures
	// ------------------------------------------------
	ArrayList<Node> Discovered = new ArrayList<Node>(); 
	ArrayList<Node> Path       = new ArrayList<Node>(); 
	ArrayList<Node> StateSpace = new ArrayList<>();
	
	// Create the stack and add the first element
	Stack<Node> s = new Stack<Node>();	
	s.push(startstate);
	
	while(s.size() != 0) {
		
		Node temp = s.pop();
		StateSpace.add(temp);
		Discovered.add(temp);

		// ------------------------------------------------
		// IF GOAL STATE IS FOUND 
		// ------------------------------------------------
		if (temp.IsGoalState() == true){ 
        	Node toReturn = temp;
        	while(temp.Parent != null) {
        		Path.add(temp.Parent);
        		temp = temp.Parent;
        	}
    		Path.add(0,toReturn);
        	return Path;
        } 
		// -----------------------------------------------
		
		ArrayList<Node> temp_children = FindNeigbors(temp);
		temp_children.sort((Node z1, Node z2) -> {
			   if (z1.Piece_Moved > z2.Piece_Moved)
			     return -1;
			   if (z1.Piece_Moved < z2.Piece_Moved)
			     return 1;
			   return 0;
			});
		
		
		for (Node child : temp_children) {
	    	int counter = 0;
			for (Node disco : Discovered) {
				if(EqualStates(child.State, disco.State)) {
					break; 
				} 
				if(counter == Discovered.size()-1){
					s.push(child);
				}
				counter++;
			}
		
		}
	}
	return Path;
}


public ArrayList<Node> IDDFS(Node startstate, int MaxDepth){
	
	// SOLVING THE PUZZLE WITH ITERATIVE DEEPENING 
	// SAME AS DFS WITH MAX_DEPTH THAT WILL BE +1 AT EACH 
	// ITERATION.
	
	ArrayList<Node> Discovered = new ArrayList<Node>(); 
	ArrayList<Node> Path       = new ArrayList<Node>(); 
	Stack<Node> s = new Stack<Node>();
	
	s.push(startstate);
	while(s.size() != 0) {
		
		Node temp = s.pop();	
		Discovered.add(temp);

		// ------------------------------------------------		
		// IF GOAL STATE IS FOUND 
		// ------------------------------------------------
		if (temp.IsGoalState() == true){ 
        	Node toReturn = temp;
        	while(temp.Parent != null) {
        		Path.add(temp.Parent);
        		temp = temp.Parent;
        	}
    		Path.add(0,toReturn);
        	return Path;
        } 
		// -----------------------------------------------
		
		ArrayList<Node> temp_children = FindNeigbors(temp);
		temp_children.sort((Node z1, Node z2) -> {
			   if (z1.Piece_Moved > z2.Piece_Moved)
			     return -1;
			   if (z1.Piece_Moved < z2.Piece_Moved)
			     return 1;
			   return 0;
			});
		
		for (Node child : temp_children) {
			for (Node disco : Discovered) {
				if(EqualStates(child.State, disco.State)) {
					break; 
				} else { 
					if((Discovered.indexOf(disco) == Discovered.size()-1) && (child.Parent_depth < MaxDepth))
						s.push(child);
				}
			}
		}
	}
	return Path;
}


public boolean EqualStates(int[][] State1, int[][] State2) {
		if (State1[0][0] == State2[0][0] && State1[0][1] == State2[0][1] &&
			State1[0][2] == State2[0][2] && State1[1][0] == State2[1][0] &&
			State1[1][1] == State2[1][1] && State1[1][2] == State2[1][2]) {
			return true;
		}
		return false;
}

	
public static void PrintPath(ArrayList<Node> Path, String nameOfAlgo) {
	System.out.println("Using " + nameOfAlgo + " to solve 6 puzzle problem...");
	for (int iLoop=Path.size()-1; iLoop >= 0; iLoop--) {
	for (int i = 0; i < Path.get(iLoop).State.length; i++) { //this equals to the row in our matrix.
		for (int j = 0; j < Path.get(iLoop).State[i].length; j++) { //this equals to the column in each row
			if (i==1 && j==0) {
				System.out.println("");
			}
			System.out.print(Path.get(iLoop).State[i][j] + " ");
    	 } 
     }
	System.out.println("  ");
	System.out.println(" | ");
	System.out.println(" v ");

}
	System.out.println("PATH LENGTH IS " + Path.size());
	System.out.println("-------------------------------");

}

public static void main(String[] args) {
	
		// START STATE
		int[][] start = {{1,4,2},{5,3,0}}; //Define the initial puzzle
		Node StartState = new Node(start,null,false,0,-1);	
		
		// CREATE A NEW INSTANCE OF A PUZZLE TO SOLVE
		Puzzle toSolve = new Puzzle();
		
		//-----------------------------------------------------------------
		//------------ ALGORITHMS TO SOLVE THE PUZZE-----------------------
		//-----------------------------------------------------------------

		// SOLVE THE PUZZLE WITH BFS
		ArrayList<Node> Path_BFS = toSolve.BFS(StartState);
		PrintPath(Path_BFS, "BFS");
		
		// SOLVE THE PUZZLE WITH UNIFORM COST SEARCH WHICH IS THE SAME AS BFS
		// SINCE THE OPERATORS HAVE THE SAME COSTS
		ArrayList<Node> Path_UCS = toSolve.BFS(StartState);
		PrintPath(Path_UCS, "UNIFORM COST SEARCH");
		
		// SOLVE THE PUZZLE WITH DFS
		ArrayList<Node> Path_DFS = toSolve.DFS(StartState);
		PrintPath(Path_DFS, "BFS");

		// SOLVE THE PUZZLE WITH IDDFS
		int maximum = 0;
		int Path_length = 0;
		ArrayList<Node> Path_IDDFS = toSolve.IDDFS(StartState,maximum);
		while(Path_IDDFS.size() == 0) {
			Path_IDDFS = toSolve.IDDFS(StartState,maximum+1);
			Path_length = Path_length + Path_IDDFS.size();
			maximum++;
		}
		System.out.println("IDDFS : Found a solution at level : " + maximum);
		PrintPath(Path_DFS, "Iterative Deepening (IDDFS)");

		//-----------------------------------------------------------------

		}
		
}
	 
	
	

