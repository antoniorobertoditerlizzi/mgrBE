package com.vigilfuoco.mgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vigilfuoco.mgr.model.Modello;
import com.vigilfuoco.mgr.repository.ModelloRepository;

import java.util.List;

@Service
public class ModelloService {

    @Autowired
    private ModelloRepository modelloRepository;

    public List<Modello> getAllModelli() {
        return modelloRepository.findAll();
    }

    public Modello getModelloById(Long id) {
        return modelloRepository.findById(id).orElse(null);
    }
}