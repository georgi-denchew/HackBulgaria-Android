package com.example.georgi.week3flappybirds.webService;

/**
 * Created by Georgi on 17.11.2014 г..
 */
public class UserData {
    private String username;
    private String email;
    private String university;
    private Long score;

    public UserData() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
