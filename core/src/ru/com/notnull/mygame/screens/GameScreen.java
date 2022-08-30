package ru.com.notnull.mygame.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.com.notnull.mygame.PhysX;
import ru.com.notnull.mygame.Anim;

public class GameScreen implements Screen {
    private final Game game;
    private final SpriteBatch spriteBatch;
    private final Anim anim;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final PhysX physX;
    private final Body body;
    private final Rectangle bodyRectangle;

    public GameScreen(Game game) {

        this.game = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        TiledMap map = new TmxMapLoader().load("map/map_2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        anim = new Anim("atlas/walk.atlas", "p1_walk", Animation.PlayMode.LOOP, 1 / 30f);
        physX = new PhysX();

        camera.zoom = 2.0f;
        RectangleMapObject tmp = (RectangleMapObject) map.getLayers().get("hero").getObjects().get("hero");
        bodyRectangle = tmp.getRectangle();
        body = physX.addObject(tmp);
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;

        Array<RectangleMapObject> objects = map.getLayers().get("static").getObjects().getByType(RectangleMapObject.class);
        objects.addAll(map.getLayers().get("water").getObjects().getByType(RectangleMapObject.class));
        objects.addAll(map.getLayers().get("dynamic").getObjects().getByType(RectangleMapObject.class));
        objects.addAll(map.getLayers().get("exit").getObjects().getByType(RectangleMapObject.class));
        for (int i = 0; i < objects.size; i++) {
            physX.addObject(objects.get(i));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_0) && camera.zoom > 0) {
            camera.zoom -= 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_1) && camera.zoom < 3) {
            camera.zoom += 0.01f;
        }


        anim.setTime(Gdx.graphics.getDeltaTime());

        ScreenUtils.clear(192, 232, 236, 256);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            exitScreen();
        }

        int speed = 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= speed;
            if (anim.isLookForward()) {
                anim.setLookForward(false);
            }
//            anim.moveLeft();
            body.applyForceToCenter(new Vector2(-1000000, 0), true);
//            anim.setLookForward(false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += speed;
            if (!anim.isLookForward()) {
                anim.setLookForward(true);
            }
//            anim.moveRight();
            body.applyForceToCenter(new Vector2(1000000, 0), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) body.applyForceToCenter(new Vector2(0, 1000000), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) body.applyForceToCenter(new Vector2(0, -1000000), true);

        if (anim.getFrame().isFlipX() && anim.isLookForward()) anim.getFrame().flip(true, false);
        if (!anim.getFrame().isFlipX() && !anim.isLookForward()) anim.getFrame().flip(true, false);

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        mapRenderer.setView(camera);
        mapRenderer.render();

        physX.step();
        physX.debugDraw(camera);

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        bodyRectangle.x = body.getPosition().x - bodyRectangle.width / 2;
        bodyRectangle.y = body.getPosition().y - bodyRectangle.height / 2;
        spriteBatch.draw(anim.getFrame(), bodyRectangle.x, bodyRectangle.y, bodyRectangle.width, bodyRectangle.height);
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
        anim.dispose();
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void dispose() {
        this.spriteBatch.dispose();
        this.anim.dispose();
    }
}
