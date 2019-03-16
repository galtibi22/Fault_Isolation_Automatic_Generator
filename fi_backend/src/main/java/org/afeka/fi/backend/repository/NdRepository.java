
package org.afeka.fi.backend.repository;

import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NdRepository extends JpaRepository<ND, String> {

    }