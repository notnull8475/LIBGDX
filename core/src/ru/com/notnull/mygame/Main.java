package ru.com.notnull.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import ru.com.notnull.mygame.common.Const;
import ru.com.notnull.mygame.screens.MenuScreen;

import java.util.HashMap;

public class Main  extends Game {
    @Override
    public void create() {
        Const.mapOfGameMaps = new HashMap<>();
        Const.mapOfGameMaps.put(1,"map/map_1.tmx");
        System.out.println(Const.mapOfGameMaps.get(1));
        Const.mapOfGameMaps.put(2,"map/map_2.tmx");
        System.out.println(Const.mapOfGameMaps.get(2));
        setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
