package com.springbootlearning.learningspringboot3;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

    @Autowired MockMvc mvc;

    @MockBean VideoService videoService;

    @Test
    @WithMockUser
    void indexPageHasSeveralHtmlForms() throws Exception {
        String html = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username: user")))
                .andExpect(content().string(containsString("Authorities: [ROLE_USER]")))
                .andReturn()
                .getResponse().getContentAsString();

        assertThat(html).contains(
                "<form action=\"/logout\"",
                "<form action=\"/search\"",
                "<form action=\"/new-video\"");
    }

    @Test
    @WithMockUser
    void postNewVideoWorks() throws Exception {
        mvc.perform(post("/new-video")
                        .param("name", "new video")
                        .param("description", "new description")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));

        verify(videoService).create(
                new NewVideo(
                        "new video",
                        "new description"),
                "user");
    }

    @Test
    @WithMockUser
    void universalSearchWorks() throws Exception {
        String searchValue = "test";
        Search search = new Search(searchValue);
        videoService.create(new NewVideo("test video 1", "description 1"), "user");
        videoService.create(new NewVideo("test video 2", "description 2"), "user");
        List<VideoEntity> mockVideos = videoService.getVideos();
        when(videoService.search(search)).thenReturn(mockVideos);

        mvc.perform(post("/search")
                .param("value", searchValue)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("videos", mockVideos));

        verify(videoService).search(search);
    }

    @Test
    @WithMockUser
    void searchWithMultipleWords() throws Exception {
        String searchValue = "test video";
        Search search = new Search(searchValue);
        videoService.create(new NewVideo("test video 1", "description 1"), "user");
        videoService.create(new NewVideo("test video 2", "description 2"), "user");
        List<VideoEntity> mockVideos = videoService.getVideos();
        when(videoService.search(search)).thenReturn(mockVideos);

        mvc.perform(post("/search")
                        .param("value", searchValue)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("videos", mockVideos));

        verify(videoService).search(search);
    }

    @Test
    @WithMockUser
    void searchWithNoResults() throws Exception {
        String searchValue = "non-existent";
        Search search = new Search(searchValue);
        when(videoService.search(search)).thenReturn(Collections.emptyList());

        mvc.perform(post("/search")
                        .param("value", searchValue)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("videos", Collections.emptyList()));

        verify(videoService).search(search);
    }

    @Test
    @WithMockUser
    void deleteVideoWorks() throws Exception {
        videoService.create(new NewVideo("test video 1", "description 1"), "user");

        mvc.perform(post("/delete/videos/1")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser
    void deleteVideoWorksWhenNoVideo() throws Exception {
        mvc.perform(post("/delete/videos/1")
                        .with(csrf()))
                .andExpect(redirectedUrl("/"));
    }
}