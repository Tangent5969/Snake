package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.Random;

public class Game extends ApplicationAdapter {

	ShapeRenderer sr;
	BitmapFont font;
	SpriteBatch batch;
	Random rand = new Random();
	int timer = 0;
	int appleX, appleY;
	boolean add = false;

	public static ArrayList<Snake> snake = new ArrayList<>();

	void apple() {
		while (true) {
			appleX = rand.nextInt(0, 39);
			appleY = rand.nextInt(0, 39);
			for (Snake i : snake) {
				if (appleX != i.x && appleY != i.y) {
					return;
				}
			}
		}
	}
	void lose() {
		ScreenUtils.clear(Color.SCARLET);
		snake.clear();
		create();
	}
	
	@Override
	public void create () {
		sr = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"),
				Gdx.files.internal("assets/font.png"), false);
		batch = new SpriteBatch();
		snake.add(new Snake(2, 20, 1));
		snake.add(new Snake(1, 20, 1));
		snake.add(new Snake(0, 20, 1));
		apple();
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);

		// input WASD
		if (Gdx.input.isKeyPressed(51)) snake.get(0).direction = 0;
		else if (Gdx.input.isKeyPressed(32)) snake.get(0).direction = 1;
		else if (Gdx.input.isKeyPressed(47)) snake.get(0).direction = 2;
		else if (Gdx.input.isKeyPressed(29)) snake.get(0).direction = 3;
		// input arrows
		if (Gdx.input.isKeyPressed(19)) snake.get(0).direction = 0;
		else if (Gdx.input.isKeyPressed(22)) snake.get(0).direction = 1;
		else if (Gdx.input.isKeyPressed(20)) snake.get(0).direction = 2;
		else if (Gdx.input.isKeyPressed(21)) snake.get(0).direction = 3;

		if (timer == 0) {
			for (int i = snake.size() - 1; i >= 0; i--) {
				Snake current = snake.get(i);

				// generates new segment
				if (add) {
					snake.add(new Snake(current.x, current.y, current.direction));
					apple();
					add = false;
				}

				if (i != 0 && current.x == snake.get(0).x && current.y == snake.get(0).y) {
					lose();
					return;
				}
				// moves snake
				switch (current.direction) {
					case 0:
						current.y ++;
						break;
					case 1:
						current.x ++;
						break;
					case 2:
						current.y --;
						break;
					case 3:
						current.x --;
						break;
				}

				if (i > 0) {
					current.direction = snake.get(i - 1).direction;
				}
				else if (current.x == 40 || current.x == -1 || current.y == 40 || current.y == -1) {
					lose();
					return;
				}
				else if (current.x == appleX && current.y == appleY) {
					add = true;

				}
			}
			timer = 10;
		}

		// draws snake + apple
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(appleX * 20 + 2, appleY * 20 + 2, 18, 18);
		sr.setColor(Color.GREEN);
		for (Snake i : snake) {
			sr.rect(i.x * 20 + 2, i.y * 20 + 2, 18, 18);
		}
		sr.end();
		timer --;

		batch.begin();
		font.draw(batch, "hello world", 100, 100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		sr.dispose();
		batch.dispose();
	}
}