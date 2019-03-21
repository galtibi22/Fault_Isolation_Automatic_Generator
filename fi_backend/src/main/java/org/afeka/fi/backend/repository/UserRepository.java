package org.afeka.fi.backend.repository;

import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

}
