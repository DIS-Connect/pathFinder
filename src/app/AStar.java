package app;

import app.Grid;
import app.Node;
import sample.Controller;

import java.util.Scanner;

public class AStar {

    private final Grid grid;
    private final Node startNode;
    private final Node endNode;

    public AStar(Grid grid){
        this.grid = grid;
        this.startNode = grid.getStartNode();
        this.endNode = grid.getEndNode();
    }
    public void findRoute() {
        grid.addToExpandArray(startNode);
        boolean routeFound = false;
        Node[] expandArray;
        Node[] searchArray;

        Scanner scanner = new Scanner(System.in);

        while(!Controller.stopAlgorithm){

            expandArray = grid.getExpandArray();
            for (Node n : expandArray) {
                //System.out.println("Expand:                     x: "+ n.getX() + ", y:  "+n.getY() +" | gCost: "+ n.getGCost()+", fCost: "+n.getFCost());

                for (Node neighbour : grid.getAllNeighbours(n)) {
                    if(neighbour == endNode){
                        Controller.stopAlgorithm = true;
                        neighbour.setPreviousNode(n);
                    }
                    if (neighbour.getState() != NodeState.EXPAND && neighbour.getState() != NodeState.BLOCK_NODE && neighbour.getState() != NodeState.START_NODE) {

                        int GCost = grid.getDistance(n, neighbour) + n.getGCost();
                        int HCost = grid.getDistance(endNode, neighbour);

                        neighbour.setCost(GCost, HCost,n);
                        neighbour.setState(NodeState.SEARCH);

                        grid.addToSearchArray(neighbour);

                       // System.out.println("x: "+ neighbour.getX() + ", y:  "+neighbour.getY() +" | gCost: "+ neighbour.getGCost()+", fCost: "+neighbour.getFCost());

                    }
                }

            }

            grid.draw();

            searchArray = grid.getSearchArray();
            Node bestNode = searchArray[0];
            for (Node n : searchArray) {

                if (n.getFCost() < bestNode.getFCost()) {
                    bestNode = n;
                }else if(n.getFCost() == bestNode.getFCost()){
                    if(n.getHCost() < bestNode.getHCost()){
                        bestNode = n;
                    }
                }
            }

            grid.addToExpandArray(bestNode);
            grid.deleteForomSearchArray(bestNode);

        }
        grid.createRoute(endNode);
    }
}
