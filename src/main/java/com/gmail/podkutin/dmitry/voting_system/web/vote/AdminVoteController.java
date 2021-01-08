package com.gmail.podkutin.dmitry.voting_system.web.vote;

import com.gmail.podkutin.dmitry.voting_system.model.restaurant.Vote;
import com.gmail.podkutin.dmitry.voting_system.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    private final VoteService service;
    static final String ADMIN_REST_URL = "/admin/restaurants/{restaurantId}/votes";

    public AdminVoteController(@Autowired VoteService service) {
        this.service = service;
    }

    @GetMapping(ADMIN_REST_URL)
    public List<Vote> getAllForRestaurant(@PathVariable("restaurantId") @NumberFormat int restaurantId) {
        return service.getAllForRestaurant(restaurantId);
    }
}