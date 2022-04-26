package COMP424;

/* DANA, Luna (260857641)

COMP424 - Assignment 1
Question 1a)

CLASSES RELATED :
- Puzzle 

*/

public class Node {

	public int[][] State;
	public Node Parent;
	public boolean Isdiscovered;
	public int Parent_depth;
	public int Piece_Moved;
	

	public Node(int[][] State, Node Parent, boolean Isdiscovered, int Parent_depth, int Piece_Moved) {
		this.Parent = Parent;
		this.State = new int[State.length][];
		for (int i = 0; i < State.length; i++) {
			this.State[i] = State[i].clone();
		}
		this.Isdiscovered = false;
		this.Parent_depth = 0;
		this.Piece_Moved = -1;
	}

	
	
	public static void main(String[] args) {
		System.out.println("hello");
	
	} 
	
	public boolean IsGoalState() {
		if (this.State[0][0] == 0 && this.State[0][1] == 1 &&
				this.State[0][2] == 2 && this.State[1][0] == 5 &&
					this.State[1][1] == 4 && this.State[1][2] == 3) {
			return true;
		}
		return false;
	}
	}
	
	
	
	
	
	
	
	
	

