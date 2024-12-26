package com.springbootlearning.learningspringboot3.ch2;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private List<Video> videos = List.of(
            new Video("Need HELP with your SPRING BOOT 3 App?"),
            new Video("What is Spring Boot 3?"),
            new Video("How to use Spring Boot 3?"));

    public List<Video> getVideos() {
        return videos;
    }

    public Video create(Video newVideo) {
        List<Video> extend = new ArrayList<>(videos);
        extend.add(newVideo);
        this.videos = List.copyOf(extend);
        return newVideo;
    }
}
