package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.PhysX;
import ru.com.notnull.mygame.listeners.SoundListener;
import ru.com.notnull.mygame.common.Const;
import ru.com.notnull.mygame.model.GameActor;
import ru.com.notnull.mygame.model.GameObject;

import java.util.ArrayList;
import java.util.HashSet;

public class GameScreen implements Screen {
    private final Game game;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private PhysX physX;
    GameActor actor;
    HashSet<GameObject> boxes;
    TextureAtlas boxTextures;
    TextureAtlas lollipopTextures;
    private Music music;
    private Music diedSound;
    public static ArrayList<Body> bodies;
    public static boolean gameOver = false;
    private TiledMap map;

//    TODO переделать класс GameObject без хранения своего боди (так получается двойная ссылка)

    public GameScreen(Game game) {
        bodies = new ArrayList<>();
        this.game = game;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch = new SpriteBatch();
        physX = new PhysX();
        boxTextures = new TextureAtlas("items/boxes.atlas");
        lollipopTextures = new TextureAtlas("items/lollipop.atlas");
        music = Gdx.audio.newMusic(Gdx.files.internal("musics/Ethernight_Club.mp3"));
        music.setVolume(0.09f);
        music.play();
        diedSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/08.wav"));
        diedSound.setLooping(false);
        diedSound.setOnCompletionListener(new SoundListener());
        camera.zoom = 0.1f;
    }

    public void init(TiledMap map){
        this.map = map;
        mapRenderer = new OrthogonalTiledMapRenderer(map, Const.PPM);
        Array<RectangleMapObject> objects = map.getLayers().get("static").getObjects().getByType(RectangleMapObject.class);
        objects.addAll(map.getLayers().get("water").getObjects().getByType(RectangleMapObject.class));
        objects.addAll(map.getLayers().get("exit").getObjects().getByType(RectangleMapObject.class));
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i), new GameObject(physX, spriteBatch, objects.get(i)));
        }
        boxes = new HashSet<>();
        MapObjects mapObjects = map.getLayers().get("dynamic").getObjects();
        for (MapObject m : mapObjects) {
            if (m.getName().equals("box")) {
                GameObject o = new GameObject(physX, spriteBatch, m);
                o.setTexture(boxTextures.findRegion("boxAlt"));
                boxes.add(o);
            } else if (m.getName().equals("box1")) {
                GameObject o = new GameObject(physX, spriteBatch, m);
                o.setTexture(boxTextures.findRegion("boxCoinAlt"));
                boxes.add(o);
            }
        }
        actor = new GameActor(physX, spriteBatch, map.getLayers().get("hero").getObjects().get("hero"), camera);

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

        for (int i = 0; i < bodies.size(); i++) {
            physX.destroyBody(bodies.get(i));
            boxes.remove((GameObject) bodies.get(i).getUserData());
        }
        bodies.clear();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        if (actor.isDie() && !diedSound.isPlaying()) {
            music.stop();
            diedSound.play();
        }

        if (gameOver) {
            gameOver=false;
            exitScreen();
        }

        for (GameObject o : boxes) {
            if (!o.isDestroyed()) o.render();
        }
        actor.render(Gdx.input);

        spriteBatch.end();
        if (Const.mapNUmbChanged){
            newGameScreen();
        }
    }

    public void newGameScreen(){
        GameScreen gameScreen = new GameScreen(game);
        System.out.println(Const.mapNumb);
        System.out.println(Const.mapOfGameMaps.get(Const.mapNumb));
        gameScreen.init(new TmxMapLoader().load(Const.mapOfGameMaps.get(Const.mapNumb)));
        Const.mapNUmbChanged = false;
        dispose();
        game.setScreen(gameScreen);
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
        music.dispose();
        physX.dispose();
        diedSound.dispose();
    }
}


