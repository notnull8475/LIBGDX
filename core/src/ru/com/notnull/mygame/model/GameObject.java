package ru.com.notnull.mygame.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import ru.com.notnull.mygame.PhysX;

public class GameObject {
    TextureRegion texture;
    Body body;
    SpriteBatch batch;
    Rectangle rectangle;
    PolygonRegion shape;

    public GameObject(PhysX physX, SpriteBatch batch, TextureRegion texture, MapObject object){
        this.texture = texture;
        this.batch = batch;
        body = physX.addObject((RectangleMapObject) object);

        rectangle = ((RectangleMapObject) object).getRectangle();
    }

    public void render(){
        rectangle.x = body.getPosition().x - rectangle.width / 2;
        rectangle.y = body.getPosition().y - rectangle.height / 2;
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

}
