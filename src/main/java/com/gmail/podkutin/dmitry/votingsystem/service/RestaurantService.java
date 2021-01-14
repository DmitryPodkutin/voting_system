package com.gmail.podkutin.dmitry.votingsystem.service;

import com.gmail.podkutin.dmitry.votingsystem.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.gmail.podkutin.dmitry.votingsystem.model.restaurant.Restaurant;
import com.gmail.podkutin.dmitry.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static com.gmail.podkutin.dmitry.votingsystem.util.ValidationUtil.*;

@Service
public class RestaurantService {
    protected final Logger log = LoggerFactory.getLogger(RestaurantService.class);
    private final RestaurantRepository repository;

    public RestaurantService(@Autowired RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> getAll(Boolean withMenu) {
        log.info("getAll");
        if (withMenu) {
            return repository.getAllWithMenuDay(LocalDate.now());
        }
        return repository.getAll();
    }

    public Restaurant get(int id, boolean withMenu) {
        log.info("get {}", id);
        if (withMenu) {
            repository.getWithMenuDay(id, LocalDate.now()).orElseThrow(() -> new NotFoundException(" Not found entity with " + id));
        }
        return repository.findById(id).orElseThrow(() -> new NotFoundException(" Not found entity with " + id));
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return repository.save(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        assureIdConsistent(restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        get(id, false);
        log.info("update {} with id={}", restaurant, id);
        repository.save(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }
}