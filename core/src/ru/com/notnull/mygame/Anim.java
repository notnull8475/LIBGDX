package ru.com.notnull.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {
    private TextureAtlas atlas;
    private Animation<TextureRegion> anm;
    private float time;
    private boolean lookForward;

    public Anim(String filename, String atlasName, Animation.PlayMode playMode, float animSpeed) {
        atlas = new TextureAtlas(filename);
        anm = new Animation<TextureRegion>(animSpeed,atlas.findRegions(atlasName));
        anm.setPlayMode(playMode);
        time += Gdx.graphics.getDeltaTime();
    }

    public TextureRegion getFrame(){
        return anm.getKeyFrame(time);
    }
    public void setTime(float time){
        this.time += time;
    }
    public int getWidth() {
        return this.getFrame().getRegionWidth();
    }
    public int getHeight() {
        return this.getFrame().getRegionHeight();
    }
    public void setLookForward(boolean lookForward) {
        this.lookForward = lookForward;
    }
    public boolean isLookForward() {
        return lookForward;
    }
    public void dispose(){
//        img.dispose();
        atlas.dispose();
    }
}
