package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.beans.ImgTexture;

public class MenuScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private ImgTexture img;
    private Rectangle startRect;
    private ShapeRenderer shapeRenderer;

    public MenuScreen(Game game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
        img = new ImgTexture("finalmaybe.png",2);
        startRect = new Rectangle(Gdx.graphics.getWidth()/2-img.getWidth()/2, Gdx.graphics.getHeight()/2-img.getHeight()/2, img.getWidth(), img.getHeight());
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.begin();
        spriteBatch.draw(img.getTexture(), Gdx.graphics.getWidth()/2-img.getWidth()/2, Gdx.graphics.getHeight()/2-img.getHeight()/2, img.getWidth(), img.getHeight());
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(startRect.x, startRect.y, startRect.width, startRect.height);
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight()-Gdx.input.getY();
            if (startRect.contains(x, y)) {
                dispose();
                game.setScreen(new Game2Screen(game));
            }

        }
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
        this.img.dispose();
        this.spriteBatch.dispose();
    }
}
