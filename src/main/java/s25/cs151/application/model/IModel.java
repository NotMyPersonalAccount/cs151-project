package s25.cs151.application.model;

/**
 * An interface for data models
 * @param <T>
 */
public interface IModel<T extends IModel<T>> {
    /**
     * Creates a new instance of the data model object with all values copied into it
     * @return a new instance of the data model
     */
    T copy();
}
