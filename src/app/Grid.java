package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Scanner;

public class Grid {

    private Node[] nodeArray;
    private int xValues;
    private int yValues;
    private int gridLenght;
    private Node startNode;
    private Node endNode;
    private Node[] route;
    private Pane pane;
    private int nodeSize;
    private boolean grindOn = false;
    private double random = 1;
    ArrayList<Node> searchList= new ArrayList<>();
    ArrayList<Node> expandList= new ArrayList<>();
    ArrayList<Node> routeList = new ArrayList<>();
    public static boolean gridOn = false;
    private int delay;

    public Grid(int xValues, int yValues, Pane pane, int nodeSize, double random){
        this.xValues = xValues;
        this.yValues = yValues;
        this.gridLenght = xValues*yValues;
        this.nodeArray = new Node[gridLenght];
        this.pane = pane;
        this.nodeSize = nodeSize;
        this.random = random;
        this.delay = delay;
        createNodeArray();
        setStartNode(getNodeByCords(2,2));
        setEndNode(getNodeByCords(xValues - 2 , yValues-2));
    }

    public Node getNodeByCords(int x,int y) {
      for (Node n : nodeArray) {
          if (n.getX() == x && n.getY() == y) {

                  return n;
                    }
      }
      return null;
  }

    public void createNodeArray(){
        int i = 0;
        int startNode = 65;
        int endNode = 134;
        for(int y = 0; y < yValues; y++){
            for(int x = 0; x < xValues; x++){


                    if(Math.random() > random){
                        nodeArray[i] = new Node(x,y,NodeState.BLOCK_NODE);
                    }else{
                        nodeArray[i] = new Node(x,y,NodeState.NODE);
                    }


                i++;
            }
        }
        System.out.println(nodeArray.length);
    }

    public Node[] getNodeArray(){
        return nodeArray;
    }

    public Node getStartNode(){
        return startNode;
    }

    public Node getEndNode(){
        return endNode;
    }

    public void addToSearchArray(Node n){
        if(!searchList.contains(n)){
            searchList.add(n);
        }

        if(n.getState() == NodeState.NODE) {
            n.setState(NodeState.SEARCH);
        }
    }
    public void deleteForomSearchArray(Node n){
        searchList.remove(n);
        if(n.getState() == NodeState.SEARCH) {
            n.setState(NodeState.EXPAND);
        }
    }
    public Node[] getSearchArray(){
        return searchList.toArray(new Node[searchList.size()]);
    }

    public void addToExpandArray(Node n){
        expandList.add(n);
        if(n.getState() == NodeState.SEARCH) {
            n.setState(NodeState.EXPAND);
        }
    }
    public void deleteFormExpandArray(Node n){
        searchList.remove(n);
        if(n.getState() == NodeState.SEARCH) {
            n.setState(NodeState.EXPAND);
        }
    }
    public Node[] getExpandArray(){
        return expandList.toArray(new Node[expandList.size()]);
    }





    public Node[] getAllNeighbours(Node n){
        ArrayList<Node> neighbourList= new ArrayList<>();


            if (getNodeByCords(n.getX(), n.getY() + 1) != null) {
                neighbourList.add(getNodeByCords(n.getX(), n.getY() + 1));
            }
            if (getNodeByCords(n.getX(), n.getY() - 1) != null) {
                neighbourList.add(getNodeByCords(n.getX(), n.getY() - 1));
            }
            if (getNodeByCords(n.getX() + 1, n.getY()) != null) {
                neighbourList.add(getNodeByCords(n.getX() + 1, n.getY()));
            }
            if (getNodeByCords(n.getX() - 1, n.getY()) != null) {
                neighbourList.add(getNodeByCords(n.getX() - 1, n.getY()));
            }


        if (getNodeByCords(n.getX()+1, n.getY() + 1) != null) {
            neighbourList.add(getNodeByCords(n.getX()+1, n.getY() + 1));
        }
        if (getNodeByCords(n.getX()-1, n.getY() - 1) != null) {
            neighbourList.add(getNodeByCords(n.getX()-1, n.getY() - 1));
        }
        if (getNodeByCords(n.getX() + 1, n.getY()-1) != null) {
            neighbourList.add(getNodeByCords(n.getX() + 1, n.getY()-1));
        }
        if (getNodeByCords(n.getX() - 1, n.getY()+1) != null) {
            neighbourList.add(getNodeByCords(n.getX() - 1, n.getY()+1));
        }

        return neighbourList.toArray(new Node[neighbourList.size()]);
    }

    public Node[] getNeighbours(Node n){
        ArrayList<Node> neighbourList= new ArrayList<>();


        if (getNodeByCords(n.getX(), n.getY() + 1) != null) {
            neighbourList.add(getNodeByCords(n.getX(), n.getY() + 1));
        }
        if (getNodeByCords(n.getX(), n.getY() - 1) != null) {
            neighbourList.add(getNodeByCords(n.getX(), n.getY() - 1));
        }
        if (getNodeByCords(n.getX() + 1, n.getY()) != null) {
            neighbourList.add(getNodeByCords(n.getX() + 1, n.getY()));
        }
        if (getNodeByCords(n.getX() - 1, n.getY()) != null) {
            neighbourList.add(getNodeByCords(n.getX() - 1, n.getY()));
        }


        return neighbourList.toArray(new Node[neighbourList.size()]);
    }

    public void createRoute(Node node) {
        node.setState(NodeState.ROUTE_NODE);
        //routeList.add(node);
        draw();

        Node prev_node =node.getPreviousNode();
        if(prev_node != null) {


            createRoute(prev_node);
        }


    }




    public void draw() {


        Platform.runLater(this::drawThread);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ignore) {

        }
    }

    public void drawThread(){
        pane.getChildren().clear();

        for(Node n: getNodeArray()) {

            if(n.getState() != NodeState.NODE) {
                Rectangle rect = new Rectangle(nodeSize, nodeSize, n.getColor());
                rect.setTranslateX(n.getX() * nodeSize);
                rect.setTranslateY(n.getY() * nodeSize);
                pane.getChildren().add(rect);
                //Label cost = new Label("G: "+n.getGCost() + " \n H: "+n.getHCost()+"\n f:"+n.getFCost());

            }


        }
        if(gridOn) {
            for (int x = 0; x < xValues; x++) {
                Line line = new Line(x * nodeSize, 0, x * nodeSize, xValues * nodeSize);
                line.setStroke(Color.LIGHTBLUE);
                pane.getChildren().add(line);
            }
            for (int y = 0; y < yValues; y++) {
                Line line = new Line(0, y * nodeSize, xValues * nodeSize, y * nodeSize);
                line.setStroke(Color.LIGHTBLUE);
                pane.getChildren().add(line);
            }
        }
    }

    public int getDistance(Node startNode,Node endNode){
        int dx = startNode.getX()-endNode.getX();
        int dy = startNode.getY() -endNode.getY();

        if(dx < 0){
            dx *= -1;
        }
        if(dy < 0){
            dy *= -1;
        }

        int rest;

        if(dx > dy){
            rest = dx -dy;
            return rest*10 +(dx-rest)*14;
        }else{
            rest = dy -dx;
            return rest*10 +(dy-rest)*14;
        }
    }

    public int getStraightDistance(Node startNode, Node endNode){
        int dx = startNode.getX()-endNode.getX();
        int dy = startNode.getY() -endNode.getY();

        int distance = dx + dy;

        if(distance < 0){
            distance *= -1;
        }

        return distance;
    }

    public void setStartNode(Node n){
        if(startNode != null){
            startNode.setState(NodeState.NODE);
            startNode.setCost(1000,0,null);
        }

        n.setState(NodeState.START_NODE);
        startNode = n;
        drawThread();
    }

    public void setEndNode(Node n){
        if(endNode != null){
            endNode.setState(NodeState.NODE);
        }

        n.setState(NodeState.END_NODE);
        endNode = n;
        drawThread();
    }

    public void setDelay(int delay){
        System.out.println("delay set");
        this.delay = delay;
    }

    public void clear() {
        for(Node n:nodeArray){
            if(n.getState() == NodeState.BLOCK_NODE){
                n.setState(NodeState.NODE);
            }
        }
        draw();
    }

    public void changeGrid(){
        if(gridOn){
            gridOn = false;
        }else{
            gridOn = true;
        }
        draw();

    }


}
