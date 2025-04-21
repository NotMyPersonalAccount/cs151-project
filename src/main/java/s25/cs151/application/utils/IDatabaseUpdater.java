package s25.cs151.application.utils;

import java.sql.SQLException;

/**
 * Functional interface for executing a database query given both an old and new instance of type T
 */
public interface IDatabaseUpdater<T> {
    void update(T oldValue, T newValue) throws SQLException;
}
