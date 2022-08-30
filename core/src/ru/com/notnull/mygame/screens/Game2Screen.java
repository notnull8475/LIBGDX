package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.PhysX;
import ru.com.notnull.mygame.model.GameActor;
import ru.com.notnull.mygame.model.GameObject;

import java.util.HashSet;
import java.util.List;

public class Game2Screen implements Screen {
    private final Game game;
    private final SpriteBatch spriteBatch;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final PhysX physX;
    GameActor actor;
    HashSet<GameObject> boxes;
    TextureAtlas boxTextures;
    TextureAtlas lollipopTextures;
    public Game2Screen(Game game) {

        this.game = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TiledMap map = new TmxMapLoader().load("map/map_2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        physX = new PhysX();
        actor = new GameActor(map, physX, spriteBatch, camera);
        boxTextures = new TextureAtlas("items/boxes.atlas");
        lollipopTextures = new TextureAtlas("items/lollipop.atlas");

        camera.zoom = 2.0f;

        Array<RectangleMapObject> objects = map.getLayers().get("static").getObjects().getByType(RectangleMapObject.class);
        objects.addAll(map.getLayers().get("water").getObjects().getByType(RectangleMapObject.class));
        objects.addAll(map.getLayers().get("exit").getObjects().getByType(RectangleMapObject.class));
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i));
        }
        boxes = new HashSet<>();
        MapObjects mapObjects = map.getLayers().get("dynamic").getObjects();
        for (MapObject m : mapObjects) {
            if (m.getName().equals("box")) boxes.add(new GameObject(physX,spriteBatch, boxTextures.findRegion("boxAlt"),m));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(192, 232, 236, 256);

        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_0) && camera.zoom > 0) {
            camera.zoom -= 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1) && camera.zoom < 3) {
            camera.zoom += 0.01f;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            exitScreen();
        }


        mapRenderer.setView(camera);
        mapRenderer.render();

        physX.step();
        physX.debugDraw(camera);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        actor.render(Gdx.input);
        for (GameObject o :
                boxes) {
                     o.render();
        }

        spriteBatch.end();
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
        this.dispose();
        game.setScreen(new MenuScreen(game));

    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        actor.dispose();
        boxTextures.dispose();
        lollipopTextures.dispose();
    }
}


