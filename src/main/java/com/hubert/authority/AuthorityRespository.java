package com.hubert.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRespository extends JpaRepository<Authority, Long> {

}
