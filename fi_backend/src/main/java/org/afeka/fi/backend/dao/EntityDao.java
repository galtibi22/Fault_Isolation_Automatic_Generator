package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EntityDao<E>{


    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException;
    public E find(String id) throws ResourceNotFoundException;
    public E add(E e) throws AddEntityExption;
    public E get(String id) throws ResourceNotFoundException;
    public List<E> getAll(String parentId) throws ResourceNotFoundException;
    public E update(E e) throws ResourceNotFoundException;



}
