package ru.graduation.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.graduation.UserTestData;
import ru.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.graduation.RestaurantTestData.*;
import static ru.graduation.TestUtil.userHttpBasic;

public class ProfileRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestaurantController.REST_URL + '/';

    @Autowired
    ProfileRestaurantController restController;

    @Test
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT_2_ORDERED));
    }
    @Test
    public void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID_NOT_FOUND)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAllWithMenuDay() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(getWithMenu()));
    }
}
