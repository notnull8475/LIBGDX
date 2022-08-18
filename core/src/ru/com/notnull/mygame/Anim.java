package ru.com.notnull.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Anim {
    private Texture img;
    private Animation<TextureRegion> anm;
    private float time;

    public Anim(String filename, int col,int row, Animation.PlayMode playMode) {
        img = new Texture(filename);
        TextureRegion region0 = new TextureRegion(img);

        int xCnt = region0.getRegionWidth()/col;
        int yCnt = region0.getRegionHeight()/row;

        TextureRegion[][] regions0 = region0.split(xCnt,yCnt);
        TextureRegion[] region1 = new TextureRegion[regions0.length*regions0[0].length];
        int cnt = 0;
        for (int i = 0;  i < regions0.length; i++) {
            for (int j = 0; j < regions0[0].length; j++) {
                region1[cnt++] = regions0[i][j];
            }
        }
        anm = new Animation<TextureRegion>(1/30f,region1);
        anm.setPlayMode(playMode);

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

    public void dispose(){
        img.dispose();
    }
}
