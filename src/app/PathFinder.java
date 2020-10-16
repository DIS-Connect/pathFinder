package app;

import java.util.ArrayList;

public class PathFinder {

    private Grid grid;
    private Node startNode;
    private Node endNode;
    private boolean routeFound = false;

    public PathFinder(Grid grid){
        this.grid = grid;
        this.startNode = grid.getStartNode();
        this.endNode = grid.getEndNode();
    }

    public void findRoute(){

        grid.addToSearchArray(startNode);
        boolean routeFound = false;

        while(!routeFound){
            Node[] Searcharray = grid.getSearchArray();

            for(Node n: Searcharray) {

                Node[] neighbourArray = grid.getNeighbours(n);
                if(neighbourArray.length == 0){
                    routeFound = true;
                }
                for(Node neighbour:neighbourArray ){

                    if(neighbour == endNode){
                        routeFound = true;
                        neighbour.setPreviousNode(n);
                    }
                    if(neighbour.getState() == NodeState.NODE){

                        neighbour.setPreviousNode(n);
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
