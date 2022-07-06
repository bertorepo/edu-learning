package com.hubert.freebies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreebisRepository extends JpaRepository<Freebies, Long> {

}
