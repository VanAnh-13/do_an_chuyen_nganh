package com.example.talentbridge.repository;

import com.example.talentbridge.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends
        JpaRepository<Permission, Long>,
        JpaSpecificationExecutor<Permission> {

    @Query("SELECT DISTINCT p.module FROM Permission p")
    List<String> findDistinctModules();
    
}
