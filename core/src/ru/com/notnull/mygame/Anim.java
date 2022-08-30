package ru.com.notnull.mygame.beans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {
    private TextureAtlas atlas;
    private Animation<TextureRegion> anm;
    private float time;
//    private int width;
//    private int height;
    private int positionX;
    private int positionY;
    private int moveSpeed;
    private boolean lookForward;

    public Anim(String filename, String atlasName, Animation.PlayMode playMode, float animSpeed,int startX,int startY,int moveSpeed) {
//        img = new Texture(filename);
//        TextureRegion region0 = new TextureRegion(img);
//
//        int xCnt = region0.getRegionWidth()/col;
//        int yCnt = region0.getRegionHeight()/row;
//
//        TextureRegion[][] regions0 = region0.split(xCnt,yCnt);
//        TextureRegion[] region1 = new TextureRegion[regions0.length*regions0[0].length];
//        int cnt = 0;
//        for (int i = 0;  i < regions0.length; i++) {
//            for (int j = 0; j < regions0[0].length; j++) {
//                region1[cnt++] = regions0[i][j];
//            }
//        }
        atlas = new TextureAtlas(filename);
//        anm = new Animation<TextureRegion>(1/30f,atlas.findRegions("Knight_03__RUN"));
        anm = new Animation<TextureRegion>(animSpeed,atlas.findRegions(atlasName));
        anm.setPlayMode(playMode);
        positionX=startX;
        positionY=startY;
        this.moveSpeed=moveSpeed;
        time += Gdx.graphics.getDeltaTime();
    }

    public TextureRegion getFrame(){
        return anm.getKeyFrame(time);
    }
    public void setTime(float time){
        this.time += time;
    }

    public void zeroTime(){
        time = 0;
    }
    public boolean isAnimation(){
        return anm.isAnimationFinished(time);
    }
    public void setMode(Animation.PlayMode playMode){
        anm.setPlayMode(playMode);
    }

    public int getWidth() {
        return this.getFrame().getRegionWidth();
    }

    public int getHeight() {
        return this.getFrame().getRegionHeight();
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void moveRight(){
        this.positionX+=moveSpeed;
    }
    public void moveLeft() {
        this.positionX-=moveSpeed;
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
