
package algorithm;

import sprite.Sprite;
import java.util.ArrayDeque;


public abstract class Algorithm {
    
    public abstract void calculateRoute();
    public abstract ArrayDeque<Vertex> getRoute();
    protected abstract void buildRoute();
    public abstract ArrayDeque<Vertex> getMapScan();
}
