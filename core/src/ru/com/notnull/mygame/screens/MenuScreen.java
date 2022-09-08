package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.ImgTexture;
import ru.com.notnull.mygame.common.Const;

public class MenuScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private ImgTexture bg;
    private ImgTexture startImg;
    private Rectangle startMenuItem;
    private ShapeRenderer shapeRenderer;
    private Music music;
    private Sound error;

    public MenuScreen(Game game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        bg = new ImgTexture("menu_bg.png", 2);
        startImg = new ImgTexture("start.png", 2);
        startMenuItem = new Rectangle(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1, Gdx.graphics.getWidth() >> 2, Gdx.graphics.getHeight() >> 4);
        shapeRenderer = new ShapeRenderer();

        music = Gdx.audio.newMusic(Gdx.files.internal("musics/Pleasant_Porridge.mp3"));
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();

        error = Gdx.audio.newSound(Gdx.files.internal("sounds/04.wav"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.valueOf("d0f4f7"));
        spriteBatch.begin();
//        spriteBatch.draw(img.getTexture(), Gdx.graphics.getWidth()/2-img.getWidth()/2, Gdx.graphics.getHeight()/2-img.getHeight()/2, img.getWidth(), img.getHeight());
        spriteBatch.draw(bg.getTexture(), 0, (Gdx.graphics.getHeight() >> 1) - bg.getHeight() / 2, bg.getWidth(), bg.getHeight());
        spriteBatch.draw(startImg.getTexture(), startMenuItem.x, startMenuItem.y, startMenuItem.width, startMenuItem.height);
        spriteBatch.end();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(startMenuItem.x, startMenuItem.y, startMenuItem.width, startMenuItem.height);
//        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (startMenuItem.contains(x, y)) {
                newGameScreen();
            } else error.play();
        }
    }

    public void newGameScreen(){
        GameScreen gameScreen = new GameScreen(game);
        gameScreen.init(new TmxMapLoader().load(Const.mapOfGameMaps.get(Const.mapNumb)));
        Const.mapNUmbChanged = false;
        dispose();
        game.setScreen(gameScreen);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.bg.dispose();
        this.spriteBatch.dispose();
        this.music.dispose();
        this.error.dispose();
    }
}
