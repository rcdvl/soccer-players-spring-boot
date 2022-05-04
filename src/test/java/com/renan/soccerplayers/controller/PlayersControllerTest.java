package com.renan.soccerplayers.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renan.soccerplayers.domain.Player;
import com.renan.soccerplayers.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void createSuccess() throws Exception {
        MockHttpServletRequestBuilder addRequest = post("/players")
                .contentType("application/json")
                .content(toJson(new Player("Renan Cadaval")));
        mockMvc.perform(addRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void createInvalidPayload() throws Exception {
        MockHttpServletRequestBuilder addRequest = post("/players")
                .contentType("application/json");
        mockMvc.perform(addRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    void createDuplicatePlayer() throws Exception {
        MockHttpServletRequestBuilder addRequest = post("/players")
                .contentType("application/json")
                .content(toJson(new Player("Jane Doe")));
        mockMvc.perform(addRequest)
                .andExpect(status().isCreated());

        mockMvc.perform(addRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    void index() throws Exception {
        MockHttpServletRequestBuilder addRequest = post("/players")
                .contentType("application/json")
                .content(toJson(new Player("John Doe")));
        mockMvc.perform(addRequest);

        MockHttpServletRequestBuilder getRequest = get("/players")
                .contentType("application/json");
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id': " + playerRepository.count() + ", fullName: 'John Doe'}]"));

    }

    private static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}