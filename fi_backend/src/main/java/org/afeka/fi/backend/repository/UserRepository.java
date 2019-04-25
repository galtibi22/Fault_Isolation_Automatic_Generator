package org.afeka.fi.backend.repository;

import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    default User update(User user){
        User current=findById(user.userName).get();
        if (user.role!=null)
            current.role=user.role;
        if(user.firstName!=null)
            current.firstName=user.firstName;
        if(user.lastName!=null)
            current.lastName=user.lastName;
        if(user.password!=null)
            current.password=user.password;
        save(current);
        return current;
    }

}
