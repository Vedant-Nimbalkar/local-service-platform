package com.localservice.service;

import com.localservice.model.ServicePartner;
import com.localservice.model.ServicePartnerRequest;
import com.localservice.repository.ServicePartnerRepository;
import com.localservice.repository.ServicePartnerRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicePartnerService {

    @Autowired
    private ServicePartnerRequestRepository requestRepo;

    @Autowired
    private ServicePartnerRepository partnerRepo;

    // Save request
    public void submitRequest(ServicePartnerRequest request){

        request.setStatus("PENDING");

        requestRepo.save(request);

    }

    // Partner login
    public ServicePartner login(String email, String password){

        ServicePartner partner = partnerRepo.findByEmail(email);

        if(partner != null && partner.getPassword().equals(password)){
            return partner;
        }

        return null;
    }
    public void approvePartner(Long requestId){

        ServicePartnerRequest req = requestRepo.findById(requestId).orElseThrow();

        ServicePartner partner = new ServicePartner();

        partner.setName(req.getName());
        partner.setEmail(req.getEmail());
        partner.setPassword(req.getPassword()); // important
        partner.setPhone(req.getPhone());

        partnerRepo.save(partner);

        req.setStatus("APPROVED");
        requestRepo.save(req);
    }
}