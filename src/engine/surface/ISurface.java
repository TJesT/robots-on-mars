package engine.surface;

/* TODO: dk how, but AbstractSurface must be thread safe */
public interface ISurface<TCell> {
    abstract public boolean hasBlock(TCell block);
    abstract public TCell[] getNeighbours(TCell block);
    abstract public TCell getStartCell();
}
