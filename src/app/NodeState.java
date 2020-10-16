package app;

import javafx.scene.paint.Color;

public enum NodeState {
    START_NODE{
        public Color getColor() {
            return Color.YELLOW;
        }
    },END_NODE{
        public Color getColor() {
            return Color.BLUE;
        }
    },NODE{
        public Color getColor() {
            return Color.WHITE;
        }
    },SEARCH {
        @Override
        public Color getColor() {
            return null;
        }
    },EXPAND {
        @Override
        public Color getColor() {
            return null;
        }
    },ROUTE_NODE {
        @Override
        public Color getColor() {
            return null;
        }
    },BLOCK_NODE {
        @Override
        public Color getColor() {
            return null;
        }
    };

    public abstract Color getColor();
}
