package sample;

import app.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    Pane pane;

    @FXML
    Button startButton;

    @FXML
    Slider gridSizeSlider;

    @FXML
    ChoiceBox algorithmBox;

    @FXML
    Slider delaySlider;

    @FXML
    Button stopButton;

    @FXML
    Button clearButton;

    @FXML
    Button gridButton;


    //variables
    private Grid grid;
    private Thread workerThread;
    private int nodeSize = 10;
    private boolean dragStartNode = false;
    private boolean dragEndNode = false;
    private int algorithmDelay = 10;
    public static boolean stopAlgorithm = true;
    ObservableList<String> sortAlgorithms = FXCollections.observableArrayList("Dijktra's Algorithm (Straight)", "Dijktra's Algorithm (Diagonal)", "A* Algorithm (Straight)", "A* Algorithm (Diagonal)");

    @FXML
    public void initialize(){
        delaySlider.setValue(3.5);
        gridSizeSlider.setValue(2.5);
        algorithmBox.setItems(sortAlgorithms);
        algorithmBox.setValue("Dijktra's Algorithm (Straight)");

        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("DRAG");
                Node n = getNodeByPaneCords((int)mouseEvent.getX(), (int)mouseEvent.getY());

                dragEvent(n);

            }
        });
        pane.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            System.out.println("RELEASE");
            dragStartNode = false;
            dragEndNode = false;
            }
        });
        gridSizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setNodeSize();
            }
        });
        delaySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setDelay();
            }
        });
        startButton.setDisable(true);
        delaySlider.setDisable(true);
        delaySlider.setDisable(true);
        clearButton.setDisable(true);
        gridButton.setDisable(true);
    }

    private void setDelay() {
        int value = (int)delaySlider.getValue();

        switch(value){
            case 1:
                algorithmDelay = 10;
                break;
            case 2:
                algorithmDelay = 20;
                break;
            case 3:
                algorithmDelay = 50;
                break;
            case 4:
                algorithmDelay = 100;
                break;
            default:
                algorithmDelay = 50;
                break;
        }
        grid.setDelay(algorithmDelay);
    }

    public void dragEvent(Node n) {
        if (n != null) {
            if (n == grid.getStartNode()) {
                dragStartNode = true;

            } else if (dragStartNode && n != grid.getStartNode() && n != grid.getEndNode()) {
                grid.setStartNode(n);

            }

            if (n == grid.getEndNode()) {
                dragEndNode = true;

            } else if (dragEndNode && n != grid.getEndNode() && n != grid.getStartNode()) {
                grid.setEndNode(n);

            }

            if (!dragEndNode && !dragStartNode) {
                n.makeBlock();
            }
            grid.drawThread();
        }
    }
    public Node getNodeByPaneCords(int x, int y) {

        if (x > 0 && y > 0) {
            return grid.getNodeByCords((int) x / nodeSize, (int) y / nodeSize);
        }else {
            return null;
        }
    }
    public void setNodeSize(){
        int sliderValue = (int)gridSizeSlider.getValue();
        if(sliderValue == 1){
            nodeSize = 10;
        }else if(sliderValue == 2){
            nodeSize = 20;
        }else if(sliderValue == 3){
            nodeSize = 25;
        }else {
            nodeSize = 50;
        }
        build();
    }
    @FXML
    public void build(){
        if(stopAlgorithm) {
            grid = new Grid((int) pane.getWidth() / nodeSize, (int) pane.getHeight() / nodeSize, pane, nodeSize, 1.0);
            grid.drawThread();
            setDelay();
            startButton.setDisable(false);
            delaySlider.setDisable(false);
            delaySlider.setDisable(false);
            clearButton.setDisable(false);
            gridButton.setDisable(false);
        }
    }

    @FXML
    public void start(){
            stopAlgorithm = false;
            startButton.setDisable(true);
            workerThread = new Thread(this::pathFinderThread);
            workerThread.start();
    }

    private void pathFinderThread() {
        if(algorithmBox.getValue() == "A* Algorithm (Straight)") {
            AStarStraight pf = new AStarStraight(grid);
            pf.findRoute();
        }else if(algorithmBox.getValue() == "A* Algorithm (Diagonal)") {
            AStar pf = new AStar(grid);
            pf.findRoute();
        }else if(algorithmBox.getValue() == "Dijktra's Algorithm (Straight)") {
            PathFinder pf = new PathFinder(grid);
            pf.findRoute();
        }else{
            PathDiagonal pf = new PathDiagonal(grid);
            pf.findRoute();
            grid.draw();
        }

    }
    @FXML
    private void changeGrid(){
      grid.changeGrid();

    }

    @FXML
    private void random(){
        grid = new Grid((int)pane.getWidth()/nodeSize,(int)pane.getHeight()/nodeSize,pane, nodeSize, 0.8);
        grid.drawThread();
        setDelay();
        startButton.setDisable(false);
        delaySlider.setDisable(false);
    }

    @FXML
    private void clear(){
        grid.clear();
    }

    @FXML
    public void stop(){
        stopAlgorithm = true;
    }


}
