package app;


import javafx.scene.paint.Color;


public class Node {

    private int x;
    private int y;
    private NodeState state;
    private Node previousNode = null;
    private int GCost;      //distance from starting node
    private int HCost; // distance from end node
    private int FCost;
    private boolean isDiagonal;

    public Node(int x, int y, NodeState state){
        this.x = x;
        this.y = y;
        this.state = state;

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Color getColor(){
        if(state == NodeState.END_NODE){
            return Color.ORANGE;
        }else if(state == NodeState.START_NODE){
            return Color.YELLOW;
        }else if(state == NodeState.SEARCH){
            return Color.DEEPPINK;
        }else if(state == NodeState.EXPAND){
            return Color.PINK;
        }else if(state == NodeState.ROUTE_NODE){
            return Color.LIGHTGREEN;
        }else if(state == NodeState.BLOCK_NODE){
            return Color.BLACK;
        }else{
            return  Color.WHITE;
        }
    }

    public void setPreviousNode(Node node){
        previousNode = node;
    }

    public void setState(NodeState state){
        this.state = state;
    }

    public NodeState getState(){
        return state;
    }

    public Node getPreviousNode(){
        return previousNode;
    }

    public void makeBlock(){
        if(state == NodeState.NODE){
            state = NodeState.BLOCK_NODE;
        }
    }

    public int getGCost(){
        return GCost;
    }

    public int getFCost(){
        return FCost;
    }

    public void setCost(int gcost,int hcost, Node n){

        if(gcost<GCost || GCost == 0){
            System.out.println(""+gcost);
            System.out.println(""+GCost);
            GCost = gcost;
            HCost =hcost;
            FCost = gcost +hcost;
            setPreviousNode(n);
        }else{

        }
    }

    public int getHCost(){
        return HCost;
    }

    public void setGCost(int gcost, Node n){
        if(GCost == 0 || GCost > gcost) {
            setPreviousNode(n);
            GCost = gcost;
        }
    }








}
