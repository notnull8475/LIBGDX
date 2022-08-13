package ru.com.notnull.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int clk;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("admin.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		float x = Gdx.input.getX()- (img.getWidth() >> 1);
		float y = Gdx.graphics.getHeight() - Gdx.input.getY() - (img.getHeight() >> 1);

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			clk++;
		}

		Gdx.graphics.setTitle("Clicked " + clk + " tipes!");

		batch.begin();
		batch.draw(img, x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
