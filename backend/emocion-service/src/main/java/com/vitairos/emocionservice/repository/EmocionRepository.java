
package com.vitairos.emocionservice.repository;

import com.vitairos.emocionservice.model.Emocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmocionRepository
        extends JpaRepository<Emocion, Integer> {

}
