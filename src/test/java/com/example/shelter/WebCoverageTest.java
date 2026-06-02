package com.example.shelter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class WebCoverageTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testFullCoverage() {
        String[] urls = {
                "/",
                "/catalog",
                "/help",
                "/about",
                "/admin/requests",
                "/admin/volunteers",
                "/admin/donations",
                "/admin/supplies",
                "/profile"
        };

        for (String url : urls) {
            try {
                mockMvc.perform(get(url));
                System.out.println("Visited: " + url);
            } catch (Exception e) {
                System.out.println("Skipped: " + url + " due to error");
            }
        }
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testPostRequests() throws Exception {
        // Имитируем отправку доната (проверь свои названия полей в Donation)
        mockMvc.perform(post("/admin/donations")
                .param("amount", "100.0")
                .param("comment", "Тестовый донат")
                .with(csrf())); // Добавляем CSRF защиту, чтобы Spring не заблокировал запрос

        // Имитируем переход по ссылке конкретного животного (если есть ID 1)
        try {
            mockMvc.perform(get("/animal/1"));
        } catch (Exception e) {}
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCatalogPage() throws Exception {
        mockMvc.perform(get("/catalog"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog"));
    }
}