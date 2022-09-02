package ru.com.notnull.mygame;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import ru.com.notnull.mygame.common.Const;
import ru.com.notnull.mygame.listeners.SimpleContactListener;
import ru.com.notnull.mygame.model.GameObject;

public class PhysX {
    private World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public PhysX() {
        world = new World(new Vector2(0, -9.81f), true);
        world.setContactListener(new SimpleContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject rectangleMapObject, GameObject gameObject) {
        Rectangle rectangle = rectangleMapObject.getRectangle();
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        String bodyType = (String) rectangleMapObject.getProperties().get("BodyType");
        if ("DynamicBody".equals(bodyType)) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else if ("KinematicBody".equals(bodyType)) {
            bodyDef.type = BodyDef.BodyType.KinematicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }


        bodyDef.position.set((rectangle.x + rectangle.width / 2)*Const.PPM, (rectangle.y + rectangle.height / 2)*Const.PPM);
        bodyDef.gravityScale = 2;


        polygonShape.setAsBox(rectangle.width / 2 * Const.PPM, rectangle.height / 2 * Const.PPM);
        fixtureDef.shape = polygonShape;

        Object o = rectangleMapObject.getProperties().get("friction");
        if (o != null) fixtureDef.friction = Float.parseFloat((String) o);
        else fixtureDef.friction = 1f;//трение объекта

        o = rectangleMapObject.getProperties().get("density");
        if (o != null) fixtureDef.density = Float.parseFloat((String) o);
        else fixtureDef.density = 1f;  // плотность

        o = rectangleMapObject.getProperties().get("restitution");
        if (o != null) fixtureDef.restitution = Float.parseFloat((String) o);
        else fixtureDef.restitution = 1f; //упругость


        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(gameObject);
        if (rectangleMapObject.getName() != null && rectangleMapObject.getName().equals("hero")){
            polygonShape.setAsBox(rectangle.width / 3 * Const.PPM, rectangle.height / 3 * Const.PPM, new Vector2(0, -rectangle.width/2*Const.PPM),0);
            body.createFixture(fixtureDef).setSensor(true);
        }


        polygonShape.dispose();
        return body;
    }

    public void step() {
        world.step(1 / 60f, 3, 3);
    }

    public void debugDraw(OrthographicCamera camera) {
        box2DDebugRenderer.render(world, camera.combined);
    }

    public void destroyBody(Body body){
        world.destroyBody(body);
    }

    public void dispose() {
        world.dispose();
        box2DDebugRenderer.dispose();
    }

}
