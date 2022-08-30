package ru.com.notnull.mygame.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ru.com.notnull.mygame.Anim;
import ru.com.notnull.mygame.PhysX;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GameActor {
    public enum State {
        IDLE, WALK, JUMP, HURT, DUCK
    }

    private final Map<String, Anim> actions = new HashMap<>();
    private TiledMap map;
    Body body;
    PhysX physX;
    private float time;

    public GameActor(TiledMap map, PhysX physX, SpriteBatch batch) {
        float animSpeed = 1 / 30f;
        actions.put(State.IDLE.toString(), new Anim("atlas/stay.atlas", "p1_front", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.WALK.toString(), new Anim("atlas/walk.atlas", "p1_walk", Animation.PlayMode.LOOP, animSpeed));
        actions.put(State.JUMP.toString(), new Anim("atlas/jump.atlas", "p1_jump", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.HURT.toString(), new Anim("atlas/hurt.atlas", "p1_hurt", Animation.PlayMode.NORMAL, animSpeed));
        actions.put(State.DUCK.toString(), new Anim("atlas/duck.atlas", "p1_duck", Animation.PlayMode.NORMAL, animSpeed));
        time += Gdx.graphics.getDeltaTime();
        this.map = map;
        this.physX = physX;
        body = physX.addObject((RectangleMapObject) map.getLayers().get("hero").getObjects().get("hero"));
    }

    public float getPositionX() {
        return body.getPosition().x;
    }

    public float getPositionY() {
        return body.getPosition().y;
    }



    private void setTime() {
        time += Gdx.graphics.getDeltaTime();
    }

    public void dispose() {
        for (Anim a : actions.values()) {
            a.dispose();
        }
    }


}
