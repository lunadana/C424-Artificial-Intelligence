package student_player;
import boardgame.Move;
import pentago_twist.*;
import java.util.*;

public class MyTools {

    public static int Player;

    public MyTools(PentagoBoardState s){  // check who is the player
        this.Player = s.getTurnPlayer();
    }

    public static double getSomething() {
        return Math.random();
    }

    // Creation of the Node Data Structure
    public static class Node{
           Node Parent;
           PentagoBoardState State;
           PentagoMove Move;
           ArrayList<Node> Children;
           int Visit = 1;
           int Wins = 0;

       // Constructor for Node
       public Node(PentagoBoardState s){
           this.State = s;
           this.Parent = null;
           this.Children = new ArrayList<Node>();
       }

       // Calculate UCT of a Node, Should I put it in the attributes?
       public static double UCT(Node n){
            return Double.valueOf(n.Wins)/Double.valueOf(n.Visit) + Math.sqrt(2.0)*Math.sqrt(Math.log(Double.valueOf(n.Parent.Visit))/Double.valueOf(n.Visit));
       }

       // Creating the tree structure.
       public static void GivingBirth(Node root){
           ArrayList<PentagoMove> AllMoves = AllMoves(root.State);
           ArrayList<PentagoMove> BadMoves = AvoidWinningOpponent(root, AllMoves);
           AllMoves.removeAll(BadMoves);
           root.Visit = root.Visit + 1;
           // Add all the children to the root and create the tree
            for (PentagoMove m : AllMoves){
                PentagoBoardState temp = (PentagoBoardState) root.State.clone(); // Clone state
                temp.processMove(m);
                Node n = new Node(temp);
                n.Move = m;
                n.Parent = root;
                root.Children.add(n);
            }
       }

       public static int MonteCarloSimulation(Node n){
           PentagoBoardState s = (PentagoBoardState) n.State.clone();
           while(!s.gameOver()){
               s.processMove((PentagoMove) s.getRandomMove());
           }
           if(s.getWinner() == Player){ return 2; } else if (s.getWinner() != 1-Player){ return 1; } else { return 0; }
       }

       public static Node MonteCarloSelection(Node root){
           if(((int)(Math.random()*10))%9==0) { // 1/10 times, get a random child
               return root.Children.get((int) (Math.random() * root.Children.size()));
           }else{
               return Collections.max(root.Children, Comparator.comparingDouble(c -> UCT((Node) c)));

           }
       }

        // Propagate the new data at the top of the tree
        public static void  MonteCarloBackPropagation(Node toExplore,int result){
            Node temp = toExplore;
            while(temp != null){
                temp.Visit = temp.Visit + 2;
                temp.Wins = temp.Wins + result;
                temp = temp.Parent;
            }
        }

    }
       // Reduce the number of moves by removing the moves that lead to the same BoardState
        public static ArrayList<PentagoMove> AllMoves(PentagoBoardState s) {
            ArrayList<PentagoMove> Moves = new ArrayList<>();
            ArrayList<String> toString = new ArrayList<>();
            for (int i = 0; i < 6; i++) { //Iterate through positions on board
                for (int j = 0; j < 6; j++) {
                    if (s.getBoard()[i][j] == PentagoBoardState.Piece.EMPTY) {
                        for (int k = 0; k < 4; k++) { // Iterate through valid moves for rotate/flip
                            for (int l = 0; l < 2; l++) {
                                PentagoBoardState temp = (PentagoBoardState) s.clone();
                                temp.processMove((new PentagoMove(i, j, k, l, s.getTurnPlayer())));
                                String str = temp.toString();
                                if(!toString.contains(str)){
                                    Moves.add(new PentagoMove(i, j, k, l, s.getTurnPlayer()));
                                    toString.add(str);
                                }
                            }
                        }
                    }
                }
            }
            return Moves;
        }

        // Check if there is a move that can make you win directly
        public static PentagoMove CheckDirectWin(Node n){
            ArrayList<PentagoMove> Moves = AllMoves(n.State);
            for(PentagoMove m : Moves){
                PentagoBoardState temp = (PentagoBoardState) n.State.clone();
                temp.processMove(m);
               if(temp.getWinner() == Player){
                   return m;
               }
        }
            return null;
    }

    // Returns an ArrayList of moves that will make you lose directly
    public static ArrayList<PentagoMove> AvoidWinningOpponent(Node n, ArrayList<PentagoMove> Moves){
        ArrayList<PentagoMove> BadMoves = new ArrayList<PentagoMove>();
        for(PentagoMove MyMove : Moves){
            PentagoBoardState temp = (PentagoBoardState) n.State.clone();
            temp.processMove(MyMove);
            for(PentagoMove HisMove : AllMoves(temp)){
                PentagoBoardState temp2 = (PentagoBoardState) temp.clone();
                temp2.processMove(HisMove);
                if (temp2.getWinner() == 1-Player) {
                    BadMoves.add(MyMove);
                    break;
                }
            }
        }
        return BadMoves;
    }


    public static void main(String[] args) {
    }

}