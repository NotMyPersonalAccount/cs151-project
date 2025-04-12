package s25.cs151.application.utils;

import java.sql.SQLException;

/**
 * Functional interface for executing a database query given an object of type T
 */
public interface IDatabaseExecutor<T> {
    void execute(T object) throws SQLException;
}
