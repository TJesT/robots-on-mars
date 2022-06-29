package engine.surface;

/* TODO: dk how, but AbstractSurface must be thread safe */
public abstract class AbstractSurface<TCell> {
    abstract public TCell[] getNeighbours(TCell block);
    abstract public TCell getStartCell();
}
