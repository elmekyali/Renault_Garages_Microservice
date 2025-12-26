package com.renault.garage.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.renault.garage.model.Garage;
import com.renault.garage.repository.GarageRepository;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageRepository;

    public Page<Garage> getAllGarages(Pageable pageable) {
        return garageRepository.findAll(pageable);
    }

    public Garage getGarageById(Long id) {
        return garageRepository.findById(id).orElse(null);
    }

    public Garage createGarage(Garage garage) {
        return garageRepository.save(garage);
    }

    public Garage updateGarage(Long id, Garage garageDetails) {
        Garage existingGarage = garageRepository.findById(id).orElse(null);
        if (existingGarage != null) {
            Garage updatedGarage = Garage.builder()
                .id(existingGarage.getId())
                .name(garageDetails.getName())
                .address(garageDetails.getAddress())
                .telephone(garageDetails.getTelephone())
                .email(garageDetails.getEmail())
                .openingHours(garageDetails.getOpeningHours())
                .vehicles(existingGarage.getVehicles() != null ? existingGarage.getVehicles() : new ArrayList<>())
                .build();
            return garageRepository.save(updatedGarage);
        }
        return null;
    }

    public void deleteGarage(Long id) {
        garageRepository.deleteById(id);
    }
}
