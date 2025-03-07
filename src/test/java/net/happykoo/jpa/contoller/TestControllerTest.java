package net.happykoo.jpa.contoller;

import net.happykoo.jpa.controller.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("testController test")
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }
}
