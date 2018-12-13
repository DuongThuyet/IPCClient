package com.example.duongnguyen.ipcserver;

// Declare any non-default types here with import statements

interface IMusicServiceServer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
        String getSongName();

          void changeMediaStatus();

          void playSong();

          void play();

          void pause();

          int getCurrentDuration();

          int getTotalDuration();
}
