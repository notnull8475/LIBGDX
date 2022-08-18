package ru.com.notnull.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Anim anim;
    private int clk;
    private boolean lookRight = true;
    private int animPositionX = 0;
    private int speed = 3;
    private int windowWidth;
    private float animWidth;
    private float animHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();
        anim = new Anim("spritesheetvolt_run.png", 5, 2, Animation.PlayMode.LOOP);
        windowWidth = Gdx.graphics.getWidth();
        animWidth = anim.getFrame().getRegionWidth() >> 2;
        animHeight = anim.getFrame().getRegionHeight() >> 2;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
//        float x = Gdx.input.getX() - (anim.getFrame().getRegionWidth() >> 1);
//        float y = Gdx.graphics.getHeight() - Gdx.input.getY() - (anim.getFrame().getRegionHeight() >> 1);
//        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) clk++;
//        Gdx.graphics.setTitle("Clicked " + clk + "times!");


        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) lookRight = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) lookRight = true;
        if (animPositionX + animWidth >= windowWidth) lookRight = false;
        if (animPositionX <= 0) lookRight = true;
        if (anim.getFrame().isFlipX() && lookRight) anim.getFrame().flip(true, false);
        if (!anim.getFrame().isFlipX() && !lookRight) anim.getFrame().flip(true, false);

        if (lookRight) {
            animPositionX += speed;
        } else {
            animPositionX -= speed;
        }


        batch.begin();

        anim.setTime(Gdx.graphics.getDeltaTime());
        batch.draw(anim.getFrame(), animPositionX, 0, animWidth, animHeight);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        anim.dispose();
    }
}
