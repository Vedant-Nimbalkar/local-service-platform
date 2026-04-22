package com.localservice.repository;

import com.localservice.model.ServicePartner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePartnerRepository extends JpaRepository<ServicePartner, Long> {

    ServicePartner findByEmail(String email);

}