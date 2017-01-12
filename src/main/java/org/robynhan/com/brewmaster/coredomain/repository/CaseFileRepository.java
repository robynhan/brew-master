package org.robynhan.com.brewmaster.coredomain.repository;

import org.robynhan.com.brewmaster.coredomain.model.CaseFile;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface CaseFileRepository extends JpaRepository<CaseFile, Long> {
    Optional<CaseFile> findOneByName(String name);
}
