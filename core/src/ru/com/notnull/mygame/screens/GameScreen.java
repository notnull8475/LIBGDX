package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.beans.Anim;
import ru.com.notnull.mygame.beans.ImgTexture;

public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private ImgTexture texture;
    private Anim anim;
    public GameScreen(Game game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        texture = new ImgTexture("relief.jpg",1);
        anim = new Anim("atlas/RUN.atlas","Knight_03__RUN",  Animation.PlayMode.LOOP,1/30f,0,70,3);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        anim.setTime(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            anim.dispose();
            game.setScreen(new MenuScreen(game));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) anim.setLookForward(false);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) anim.setLookForward(true);
        if (anim.getPositionX() + anim.getWidth() >= Gdx.graphics.getWidth()) anim.setLookForward(false);
        if (anim.getPositionX() <= 0) anim.setLookForward(true);
        if (anim.getFrame().isFlipX() && anim.isLookForward()) anim.getFrame().flip(true, false);
        if (!anim.getFrame().isFlipX() && !anim.isLookForward()) anim.getFrame().flip(true, false);

        if (anim.isLookForward()) {
            anim.moveRight();
        } else {
            anim.moveLeft();
        }

        spriteBatch.begin();
        spriteBatch.draw(texture.getTexture(), 0, 0,texture.getWidth(),texture.getHeight());
        spriteBatch.draw(anim.getFrame(), anim.getPositionX(), anim.getPositionY(), anim.getWidth(), anim.getHeight());
        spriteBatch.end();

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
        this.texture.dispose();
        this.spriteBatch.dispose();
        this.anim.dispose();
    }
}
