package engine.surface;

import engine.surface.loader.ILoader;

public abstract class AbstractSurface<TCell, TStorage> {
    ILoader<TStorage> loader;

    protected AbstractSurface(ILoader<TStorage> loader) {
        this.loader = loader;
    }

    abstract public TCell[] getNeighbours(TCell block);
    abstract public TCell getStartCell();
}
