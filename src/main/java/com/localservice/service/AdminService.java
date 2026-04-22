package com.localservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localservice.model.ServicePartner;
import com.localservice.model.ServicePartnerRequest;
import com.localservice.repository.ServicePartnerRepository;
import com.localservice.repository.ServicePartnerRequestRepository;

@Service
public class AdminService {

    @Autowired
    private ServicePartnerRequestRepository requestRepo;

    @Autowired
    private ServicePartnerRepository partnerRepo;

    public void approvePartner(Long id){

        ServicePartnerRequest request = requestRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        ServicePartner partner = new ServicePartner();

        partner.setName(request.getName());
        partner.setEmail(request.getEmail());
        partner.setPhone(request.getPhone());
        partner.setServiceType(request.getServiceType());
        partner.setExperience(request.getExperience());

        // temporary password for partner login
        partner.setPassword("123456");

        partnerRepo.save(partner);

        requestRepo.deleteById(id);
    }

    public void rejectPartner(Long id){

        requestRepo.deleteById(id);

    }
}