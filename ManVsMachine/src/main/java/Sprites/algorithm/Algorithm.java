
package Sprites.algorithm;

import Sprites.Sprite;
import java.util.ArrayDeque;


public abstract class Algorithm {
    
    public abstract void calculateRoute();
    public abstract void setMachine(Sprite machine);
    public abstract void setMap(int[][] map);
    public abstract void takeStep();
    public abstract ArrayDeque<Vertex> getRoute();
    public abstract void buildRoute();
    public abstract void scanTile();
}
