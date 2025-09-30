package com.example.talentbridge.repository;

import com.example.talentbridge.model.CompanyLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyLogoRepository extends JpaRepository<CompanyLogo, Long> {
}
