package org.afeka.fi.backend.services;

import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.PermissionExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;

public interface EntityService <E>  {
    E add(ViewCreateRequest viewCreateRequest,String parentId) throws ResourceNotFoundException, AddEntityExption;
    E update(E e) throws ResourceNotFoundException, PermissionExption;
    void delete(String id) throws ResourceNotFoundException, DeleteEntityExption, PermissionExption;
    E get(String id) throws ResourceNotFoundException;
    E find(String id) throws ResourceNotFoundException;
    List<E> getAll(String parentId) throws ResourceNotFoundException;
    default E add (E e) throws PermissionExption, AddEntityExption {
        throw new NotImplementedException("add object");
    }
}
