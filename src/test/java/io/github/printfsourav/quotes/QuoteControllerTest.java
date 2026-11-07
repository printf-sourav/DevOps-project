package io.github.printfsourav.quotes;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuoteController.class)
class QuoteControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void listsTheThreeQuotes() throws Exception {
        mvc.perform(get("/quotes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].text").value("Start small. Keep improving."));
    }

    @Test
    void randomQuoteBelongsToTheList() throws Exception {
        var controller = new QuoteController();
        for (int i = 0; i < 20; i++) {
            var response = mvc.perform(get("/quotes/random"))
                .andExpect(status().isOk()).andReturn();
            var quote = mapper.readValue(response.getResponse().getContentAsString(),
                QuoteController.Quote.class);
            assertTrue(controller.list().contains(quote));
        }
    }

    @Test
    void healthEndpointIsReadyForProbes() throws Exception {
        mvc.perform(get("/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("UP"));
    }
}
