package org.robynhan.com.brewmaster.coredomain.repository;

import org.robynhan.com.brewmaster.coredomain.model.CaseFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface CaseFolderRepository extends JpaRepository<CaseFolder, Long> {
    Optional<CaseFolder> findOneByName(String name);
}
