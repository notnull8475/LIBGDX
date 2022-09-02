package ru.com.notnull.mygame.listeners;

import com.badlogic.gdx.audio.Music;
import ru.com.notnull.mygame.screens.GameScreen;

public class SoundListener implements Music.OnCompletionListener {
    @Override
    public void onCompletion(Music music) {
        GameScreen.gameOver = true;
    }
}
