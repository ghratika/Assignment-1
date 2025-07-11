
package com.example.alumnilinkedinsearcher.repository;

import com.example.alumnilinkedinsearcher.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, Long> {}
