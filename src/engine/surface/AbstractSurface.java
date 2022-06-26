package engine.surface;

public abstract class AbstractSurface<B> {
    abstract public B[] getNeighbours(B block);
    abstract public B getStartCell();
}
