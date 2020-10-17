package app;

import sample.Controller;

import java.util.ArrayList;
import java.util.Scanner;

public class PathDiagonal {

    private Grid grid;
    private Node startNode;
    private Node endNode;
    private Scanner scanner = new Scanner(System.in);

    public PathDiagonal(Grid grid){
        this.grid = grid;
        this.startNode = grid.getStartNode();
        this.endNode = grid.getEndNode();
    }

    public void findRoute(){

        grid.addToSearchArray(startNode);

        while(!Controller.stopAlgorithm){
            Node[] Searcharray = grid.getSearchArray();
            for(Node n: Searcharray) {

                Node[] neighbourArray = grid.getAllNeighbours(n);
                if(neighbourArray.length == 0){
                    Controller.stopAlgorithm = true;
                }

                for(Node neighbour:neighbourArray ){

                    if(neighbour == endNode){
                        Controller.stopAlgorithm = true;
                    }
                    if(neighbour.getState() == NodeState.NODE || neighbour.getState() == NodeState.SEARCH || neighbour.getState() == NodeState.END_NODE){

                        neighbour.setGCost(grid.getDistance(n,neighbour) + n.getGCost(), n);
                        grid.addToSearchArray(neighbour);

                    }
                    grid.deleteForomSearchArray(n);

                }
            }
            grid.draw();

        }

        grid.createRoute(endNode);

    }

}
