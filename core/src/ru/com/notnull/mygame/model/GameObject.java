package ru.com.notnull.mygame.model;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import ru.com.notnull.mygame.PhysX;
import ru.com.notnull.mygame.common.Const;
import ru.com.notnull.mygame.common.GeometryMath;

public class GameObject {
    protected TextureRegion texture;
    protected Body body;
    protected SpriteBatch batch;
    protected Rectangle rectangle;
    protected PhysX physX;
    //    TODO сделать поыорот текстур при повороте физической модели
    PolygonRegion shape;
    protected String name;
    private boolean isDestroyed = false;
    protected boolean die = false;

    public GameObject(PhysX physX, SpriteBatch batch, MapObject object) {
        this.batch = batch;
        this.physX = physX;
        this.name = object.getName();
        body = this.physX.addObject((RectangleMapObject) object, this);
        rectangle = ((RectangleMapObject) object).getRectangle();
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public void render() {
        rectangle.x = body.getPosition().x - rectangle.width / 2 * Const.PPM;
        rectangle.y = body.getPosition().y - rectangle.height / 2 * Const.PPM;
        batch.draw(texture,
                rectangle.x, rectangle.y,
                rectangle.width/2*Const.PPM,
                rectangle.height/2*Const.PPM,
                rectangle.width*Const.PPM,
                rectangle.height*Const.PPM,
                1,1,
                body.getAngle()* MathUtils.radiansToDegrees                );
    }

    public String getName() {
        return name;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void setDie() {
        die = true;
    }

    public boolean isDie() {
        return die;
    }
}
