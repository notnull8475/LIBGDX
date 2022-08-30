package ru.com.notnull.mygame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ru.com.notnull.mygame.Anim;
import ru.com.notnull.mygame.PhysX;

import java.util.HashMap;
import java.util.Map;

public class GameActor {
    public enum State {
        IDLE, WALK, JUMP, HURT, DUCK
    }

    private final Map<String, Anim> actions = new HashMap<>();
    private TiledMap map;
    Body body;
    PhysX physX;
    private float time;
    private SpriteBatch batch;
    OrthographicCamera camera;
    Anim action;
    Rectangle brect;

    boolean isMove = false;
    boolean isJump = false;


    public GameActor(TiledMap map, PhysX physX, SpriteBatch batch, OrthographicCamera camera) {
        float animSpeed = 1 / 60f;
        actions.put(State.IDLE.toString(), new Anim("atlas/stay.atlas", "p1_front", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.WALK.toString(), new Anim("atlas/walk.atlas", "p1_walk", Animation.PlayMode.LOOP, animSpeed));
        actions.put(State.JUMP.toString(), new Anim("atlas/jump.atlas", "p1_jump", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.HURT.toString(), new Anim("atlas/hurt.atlas", "p1_hurt", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.DUCK.toString(), new Anim("atlas/duck.atlas", "p1_duck", Animation.PlayMode.NORMAL, animSpeed));
        time += Gdx.graphics.getDeltaTime();
        this.map = map;
        this.physX = physX;
        this.batch = batch;
        this.camera = camera;
        this.body = physX.addObject((RectangleMapObject) map.getLayers().get("hero").getObjects().get("hero"));
        this.brect = ((RectangleMapObject) map.getLayers().get("hero").getObjects().get("hero")).getRectangle();
        this.body.setGravityScale(20);
        action = actions.get("IDLE");
    }

    public float getPositionX() {
        return body.getPosition().x;
    }

    public float getPositionY() {
        return body.getPosition().y;
    }


    public void render(Input input) {
        camera.update();
        this.setTime();
        brect.x = body.getPosition().x - brect.width / 2;
        brect.y = body.getPosition().y - brect.height / 2;

        if (input.isKeyPressed(Input.Keys.LEFT)) {
            this.moveLeft();
        } else if (input.isKeyPressed(Input.Keys.RIGHT)) {
            this.moveRight();
        } else if (input.isKeyPressed(Input.Keys.DOWN)) {
            this.duck();
        } else if (input.isKeyJustPressed(Input.Keys.UP) || input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!isJump) jump();
        } else {
//            if (!body.isAwake())
            if (!isJump)
                action = actions.get("IDLE");
        }
        if (body.getLinearVelocity().y == 0) {
            isJump = false;
        }
        action.setTime(time);
        batch.draw(action.getFrame(), brect.x, brect.y, brect.width, brect.height);
        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
    }

    private void setTime() {

        time += Gdx.graphics.getDeltaTime();
    }

    private void moveLeft() {
        action = actions.get("WALK");
        if (!action.getFrame().isFlipX()) action.getFrame().flip(true, false);
        body.applyForceToCenter(new Vector2(-1000000, 0), true);
    }

    private void moveRight() {
        action = actions.get("WALK");
        if (action.getFrame().isFlipX()) action.getFrame().flip(true, false);
        body.applyForceToCenter(new Vector2(1000000, 0), true);
    }

    private void duck() {
        action = actions.get("DUCK");
    }

    private void jump() {
        isMove = true;
        isJump = true;
        action = actions.get("JUMP");
        body.applyLinearImpulse(new Vector2(0, 100000), new Vector2(brect.x, brect.y + 10), true);
    }

    public void dispose() {
        for (Anim a : actions.values()) {
            a.dispose();
        }
    }


}
