package ru.com.notnull.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImgTexture {
    private Texture texture;
    private float width;
    private float height;

    public ImgTexture(String filename, int size) {
        this.texture = new Texture(filename);
        width = Gdx.graphics.getWidth()/size;
        height = Gdx.graphics.getHeight()/size;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void dispose(){
        texture.dispose();
    }

}
