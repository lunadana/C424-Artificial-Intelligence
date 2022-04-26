package student_player;

import boardgame.Move;
import java.util.*;
import pentago_twist.PentagoMove;
import pentago_twist.PentagoPlayer;
import java.io.*;
import pentago_twist.PentagoBoardState;

public class StudentPlayer extends PentagoPlayer {

    public StudentPlayer() {
        super("260857641");
    }

    public Move chooseMove(PentagoBoardState boardState) {
        // Make the algorithm work for the 2 seconds allowed for each turn
        long t = System.currentTimeMillis();
        long end = t+1900;

        MyTools tool = new MyTools(boardState);
        MyTools.Node node = new MyTools.Node(boardState);

        if(boardState.getTurnNumber() == 0){
            return boardState.getRandomMove();
        }

        // Check if there is direct move that can make you win. If yes execute that move
        if(boardState.getTurnNumber() > 2){
            PentagoMove temp = MyTools.CheckDirectWin(node);
            if(temp!= null) {
                 return temp;
             }
        }

        // Give birth and create the first level of the tree
        MyTools.Node.GivingBirth(node); // It will remove the moves that can make the opponent win directly

        // Execute the Monte Carlo Algorithm during the time possible
        while(System.currentTimeMillis() < end) {
            MyTools.Node BestChild = MyTools.Node.MonteCarloSelection(node);
            int winner = MyTools.Node.MonteCarloSimulation(BestChild);
            MyTools.Node.MonteCarloBackPropagation(BestChild, winner);
        }
        return Collections.max(node.Children, Comparator.comparingDouble(c -> c.Wins/c.Visit)).Move;
    }
}