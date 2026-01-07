package com.hxi.rageclick_service.repository;

import com.hxi.rageclick_service.model.RageClickEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RageClickRepository
        extends MongoRepository<RageClickEvent, String> {
}
