package ru.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.UserTestData;
import ru.graduation.web.AbstractControllerTest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.graduation.TestUtil.userHttpBasic;
import static ru.graduation.VoteTestData.*;

public class AdminVoteControllerTest extends AbstractControllerTest {

    @Autowired
    AdminVoteController restController;

    @Test
    public void getAllForRestaurant() throws Exception {
        VOTE_1.setDate(LocalDate.of(2020,10,23));
        VOTE_2.setDate(LocalDate.of(2020,10,19));
        VOTE_3.setDate(LocalDate.of(2020,11,9));
        perform(MockMvcRequestBuilders.get("/admin/restaurants/100004/votes/")
                .with(userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(VOTE_2,VOTE_1, VOTE_3));
    }
}
