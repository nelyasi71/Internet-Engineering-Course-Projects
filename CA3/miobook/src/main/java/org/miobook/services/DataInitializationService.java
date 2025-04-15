package org.miobook.services;

import org.miobook.services.ExternalDataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataInitializationService {

    @Autowired
    private ExternalDataFetcher externalDataFetcher;

    @PostConstruct
    public void init() {
        externalDataFetcher.fetchAndProcessData();  // Fetch data on startup
    }
}
