package org.afeka.fi.backend.services;

import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;

import java.util.List;

public interface EntityService <E>  {
    E add(ViewCreateRequest viewCreateRequest,String parentId) throws ResourceNotFoundException;
    E update(E e) throws ResourceNotFoundException;
    void delete(String id) throws ResourceNotFoundException, DeleteEntityExption;
    E get(String id) throws ResourceNotFoundException;
    E find(String id) throws ResourceNotFoundException;
    List<E> getAll(String parentId) throws ResourceNotFoundException;
}
