package ru.com.notnull.mygame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ru.com.notnull.mygame.Anim;
import ru.com.notnull.mygame.PhysX;
import ru.com.notnull.mygame.common.Const;

import java.util.HashMap;
import java.util.Map;

public class GameActor extends GameObject {
    public enum State {
        IDLE, WALK, JUMP, HURT, DUCK
    }

    private final Map<String, Anim> actions = new HashMap<>();
    //    Body body;
//    PhysX physX;
    //    private SpriteBatch batch;
    OrthographicCamera camera;
    Anim action;
//    Rectangle brect;

    boolean isMove = false;
    boolean isJump = false;


    public GameActor(PhysX physX, SpriteBatch batch, MapObject object, OrthographicCamera camera) {
        super(physX, batch, object);
        float animSpeed = 1 / 40f;
        actions.put(State.IDLE.toString(), new Anim("atlas/stay.atlas", "p1_front", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.WALK.toString(), new Anim("atlas/walk.atlas", "p1_walk", Animation.PlayMode.LOOP, animSpeed));
        actions.put(State.JUMP.toString(), new Anim("atlas/jump.atlas", "p1_jump", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.HURT.toString(), new Anim("atlas/hurt.atlas", "p1_hurt", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.DUCK.toString(), new Anim("atlas/duck.atlas", "p1_duck", Animation.PlayMode.NORMAL, animSpeed));
        this.camera = camera;
//        this.body.setGravityScale(20);
        this.body.setFixedRotation(true);
        action = actions.get("IDLE");
        System.out.println("mass " + body.getMass());
        System.out.println(getJumpPower());
        System.out.println(getMovePower());

    }

    public void render(Input input) {
        if (!this.isDie()) {
            camera.update();
            action.setTime(Gdx.graphics.getDeltaTime());
            rectangle.x = body.getPosition().x - rectangle.width / 2 * Const.PPM;
            rectangle.y = body.getPosition().y - rectangle.height / 2 * Const.PPM;

            if (input.isKeyPressed(Input.Keys.LEFT)) {
                this.moveLeft();
            } else if (input.isKeyPressed(Input.Keys.RIGHT)) {
                this.moveRight();
            } else if (input.isKeyPressed(Input.Keys.DOWN)) {
                this.duck();
            } else if (input.isKeyJustPressed(Input.Keys.UP) || input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (!isJump) jump();
            } else {
                if (body.getLinearVelocity().y<0.5){
                    isJump = false;
                    action = actions.get("IDLE");
                }
            }
            batch.draw(action.getFrame(), rectangle.x, rectangle.y, rectangle.width * Const.PPM, rectangle.height * Const.PPM);
            camera.position.x = body.getPosition().x;
            camera.position.y = body.getPosition().y;
        }
    }

    private void moveLeft() {
        action = actions.get("WALK");
        if (!action.getFrame().isFlipX()) action.getFrame().flip(true, false);
        body.applyForceToCenter(new Vector2(-getMovePower(), 0), true);
    }

    private void moveRight() {
        action = actions.get("WALK");
        if (action.getFrame().isFlipX()) action.getFrame().flip(true, false);
        body.applyForceToCenter(new Vector2(getMovePower(), 0), true);
    }

    private void duck() {
        action = actions.get("DUCK");
    }

    private void jump() {
        isMove = true;
        isJump = true;
        action = actions.get("JUMP");
        body.applyLinearImpulse(new Vector2(0, getJumpPower()), new Vector2(rectangle.x, rectangle.y + 10), true);
    }

    private float getMovePower() {
        float power = body.getMass() * Const.bigG * Const.moveSpeed;
        if (Math.abs(body.getLinearVelocity().x) >= 15) {
            power = body.getMass() * Const.bigG;
        }
        return power;
    }

    private float getJumpPower() {
        return body.getMass() * Const.bigG * Const.jumpPower;
    }

    public void dispose() {
        for (Anim a : actions.values()) {
            a.dispose();
        }
    }


}
