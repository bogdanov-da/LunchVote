package org.bda.voteapp.util;

import org.bda.voteapp.exception.IllegalRequestDataException;
import org.bda.voteapp.exception.NotFoundException;

import java.util.Optional;

public class ValidationUtil {
    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static <T> T checkNotFound(Optional<T> object, int id) {
        if(object.isEmpty()) throw new NotFoundException("Object with id " + id + " not found");
        return object.get();
    }
}