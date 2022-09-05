package ru.com.notnull.mygame.common;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;

public class GeometryMath {
    private Body body;
    private Rectangle rectangle;

    public static float getRectangleWithRotate(Body body){
//        Transform b= body.getTransform();

        return body.getAngle();
    }

}
