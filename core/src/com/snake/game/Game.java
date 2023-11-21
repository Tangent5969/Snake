package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
	int timer, score, highScore = 0;
	int appleX, appleY;
	boolean add = false;
	public static ArrayList<Snake> snake = new ArrayList<>();
	Head head;

	public void apple() {
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

	public void lose() {
		ScreenUtils.clear(Color.SCARLET);
		score = 0;
		snake.clear();
		setBoard();
	}

	public void setBoard() {
		head = new Head(2, 20, 1);
		snake.add(new Snake(1, 20, 1));
		snake.add(new Snake(0, 20, 1));
		apple();
	}

	public void logic() {
		head.getMove();
		if (timer == 0) {
			for (int i = snake.size() - 1; i >= 0; i--) {
				Snake current = snake.get(i);

				if (add) {
					snake.add(new Snake(current.x, current.y, current.direction));
					score ++;
					if (score > highScore) highScore++;
					apple();
					add = false;
				}

				if (current.x == head.x && current.y == head.y) {
					lose();
					return;
				}

				current.move();
				if (i > 0) current.direction = snake.get(i - 1).direction;
				else current.direction = head.direction;
			}
			head.move();
			if (head.x == 40 || head.x == -1 || head.y == 40 || head.y == -1) {
				lose();
				return;
			}
			else if (head.x == appleX && head.y == appleY) {
				add = true;
			}

			// controls tick speed
			timer = 10;
		}
		timer --;
	}


	@Override
	public void create () {
		sr = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		batch = new SpriteBatch();
		setBoard();
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		logic();

		// draws snake + apple
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(appleX * 20 + 2, appleY * 20 + 2, 18, 18);
		sr.setColor(Color.GREEN);
		sr.rect(head.x * 20 + 2, head.y * 20 + 2, 18, 18);
		for (Snake i : snake) {
			sr.rect(i.x * 20 + 2, i.y * 20 + 2, 18, 18);
		}

		// draws top bar
		sr.setColor(Color.DARK_GRAY);
		sr.rect(0, 802, 802, 38);
		sr.end();
		batch.begin();
		font.draw(batch, "Score : " + score, 5, 833);
		font.draw(batch, "HighScore : " + highScore, 580, 833);
		batch.end();

	}
	
	@Override
	public void dispose () {
		sr.dispose();
		batch.dispose();
	}
}