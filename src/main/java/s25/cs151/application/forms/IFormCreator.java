package s25.cs151.application.forms;

import com.dlsc.formsfx.model.structure.Form;

/**
 * Functional interface for creating a Form instance from an object of type T
 */
public interface IFormCreator<T> {
    Form create(T object);
}
