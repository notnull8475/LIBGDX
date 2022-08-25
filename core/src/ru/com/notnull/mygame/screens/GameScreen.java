package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.beans.Anim;
import ru.com.notnull.mygame.beans.ImgTexture;

public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch spriteBatch;
    private ImgTexture texture;
    private Anim anim;
    private Rectangle menuRect;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Rectangle mapSize;
    private int speed = 3;

    public GameScreen(Game game) {
        this.game = game;
        spriteBatch = new SpriteBatch();
//        texture = new ImgTexture("relief.jpg", 1);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 2.0f;
        map = new TmxMapLoader().load("map/map_1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        RectangleMapObject rmo = (RectangleMapObject) map.getLayers().get("cam").getObjects().get("camPoint");
        camera.position.x = rmo.getRectangle().x;
        camera.position.y = rmo.getRectangle().y;

        mapSize = ((RectangleMapObject) map.getLayers().get("cam").getObjects().get("perimetr")).getRectangle();

        anim = new Anim("atlas/walk.atlas", "p1_walk", Animation.PlayMode.LOOP, 1 / 30f, 0, 280, speed);


        menuRect = new Rectangle(0, 70, anim.getWidth(), anim.getHeight());
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        anim.setTime(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(Color.BLACK);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            exitScreen();
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (menuRect.contains(x, y)) {
                exitScreen();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= speed;
            if (anim.isLookForward()){
                anim.setLookForward(false);
            }
            anim.moveLeft();
//            anim.setLookForward(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += speed;
            if (!anim.isLookForward()){
                anim.setLookForward(true);
            }
            anim.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom -= 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && camera.zoom > 0) {
            camera.zoom += 0.01f;
        }
//        if (anim.getPositionX() + anim.getWidth() >= Gdx.graphics.getWidth()) anim.setLookForward(false);
//        if (anim.getPositionX() <= 0) anim.setLookForward(true);
        if (!mapSize.contains(anim.getPositionX() + anim.getWidth(),anim.getPositionY())) anim.setLookForward(!anim.isLookForward());
        if (anim.getFrame().isFlipX() && anim.isLookForward()) anim.getFrame().flip(true, false);
        if (!anim.getFrame().isFlipX() && !anim.isLookForward()) anim.getFrame().flip(true, false);

        camera.position.x = anim.getPositionX();
        camera.position.y = anim.getPositionY();
        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
//        spriteBatch.draw(texture.getTexture(), 0, 0, texture.getWidth(), texture.getHeight());
        spriteBatch.draw(anim.getFrame(), anim.getPositionX(), anim.getPositionY(), anim.getWidth(), anim.getHeight());
        spriteBatch.end();


        menuRect.set(anim.getPositionX(), anim.getPositionY(), anim.getWidth(), anim.getHeight());

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;

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

    private void exitScreen() {
        dispose();
        anim.dispose();
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        this.anim.dispose();
    }
}
