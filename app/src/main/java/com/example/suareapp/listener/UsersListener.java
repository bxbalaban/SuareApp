package com.example.suareapp.listener;

import com.example.suareapp.models.User;

public interface UsersListener {
    void initiateVideoMeeting(User user);
    void initiateAudioMeeting(User user);
}
