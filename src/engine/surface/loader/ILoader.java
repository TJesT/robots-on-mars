package engine.surface.loader;

public interface ILoader<T> {
    abstract T load(String file_name);
}
