package com.colinrrobinson.minesweeper;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Minesweeper extends Game {

    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    HashMap<Integer, Rectangle> tiles;
    int[][] tileGrid;

    Texture coveredTexture;
    Texture blankTexture;

    // 必修 //
    int[][] a1Grid;
    int[][] a2Grid;
    Texture a1Texture;
    Texture a2Texture;

    // EC //
    int[][] b1Grid;
    int[][] b2Grid;
    int[][] b3Grid;
    Texture b1Texture;
    Texture b2Texture;
    Texture b3Texture;

    // IDIC //
    int[][] c1Grid;
    int[][] c2Grid;
    int[][] c3Grid;
    Texture c1Texture;
    Texture c2Texture;
    Texture c3Texture;

    // tile width and height
    float width = 64;
    float height = 64;
    int grid_x = 12;
    int grid_y = 12;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 768);

        batch = new SpriteBatch();

        // 必修 //
        a1Texture = new Texture(Gdx.files.internal("3_1.png"));
        a2Texture = new Texture(Gdx.files.internal("3_2.png"));

        // market //
        b1Texture = new Texture(Gdx.files.internal("1_1.png"));
        b2Texture = new Texture(Gdx.files.internal("1_2.png"));
        b3Texture = new Texture(Gdx.files.internal("1_3.png"));

        // idic //
        c1Texture = new Texture(Gdx.files.internal("2_1.png"));
        c2Texture = new Texture(Gdx.files.internal("2_2.png"));
        c3Texture = new Texture(Gdx.files.internal("2_3.png"));

        coveredTexture = new Texture(Gdx.files.internal("covered.png"));    // 藍色覆蓋
        blankTexture = new Texture(Gdx.files.internal("blank.png"));    // 灰色底圖

        shapeRenderer = new ShapeRenderer();

        tiles = new HashMap<Integer, Rectangle>();  // 初始化地圖的大小（？
        tileGrid = new int[12][12];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                initTiles(i, j);
            }
        }

        // 必修 //
        a1Grid = generate(10, 10, 2,11);
        a2Grid = generate(10, 10, 2,12);

        // EC //
        b1Grid = generate(10, 10, 3,21);
        b2Grid = generate(10, 10, 3,22);
        b3Grid = generate(10, 10, 3,23);

        // IDIC //
        c1Grid = generate(10, 10, 3,31);
        c2Grid = generate(10, 10, 3,32);
        c3Grid = generate(10, 10, 3,33);

        // 這邊應該是在做玩家連線的
        MyGestureListener mgl = new MyGestureListener();
        mgl.setGame(this);
        GestureDetector gd = new GestureDetector(mgl); // 偵測連線？
        Gdx.input.setInputProcessor(gd);
    }

    /**
     * 應該是初始化地圖大小
     */
    private void initTiles(int i, int j) {
        Rectangle tile = new Rectangle();
        // 藍色框框大小
        tile.x = 0 + i * 64;
        tile.y = 0 + j * 64;
        // 不知道幹嘛用的
        tile.width = 64;
        tile.height = 64;

        Integer size = tiles.size();
        tiles.put(size, tile);
        tileGrid[i][j] = 0;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255,255,255,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (Entry<Integer, Rectangle> entry : tiles.entrySet()) {
            Integer key = entry.getKey();
            Rectangle tile = entry.getValue();

            int row = (int) key % 12;
            int col = (int) Math.floor(key / 12);
            int state = tileGrid[col][row];

            Texture texture = coveredTexture;
            if (state == 0) {
                texture = coveredTexture;
            } else if (state == 1) {
                texture = blankTexture;
            } else if (state == 11) {
                texture = a1Texture;
            } else if (state == 12) {
                texture = a2Texture;
            } else if (state == 21) {
                texture = b1Texture;
            } else if (state == 22) {
                texture = b2Texture;
            } else if (state == 23) {
                texture = b3Texture;
            } else if (state == 31) {
                texture = c1Texture;
            } else if (state == 32) {
                texture = c2Texture;
            } else if (state == 33) {
                texture = c3Texture;
            }
            batch.draw(texture, tile.x, tile.y, 64, 64);
        }
        batch.end();

//        drawGrid();

    }

    /**
     * 畫面生成和判斷
     */
    public void processTouch() {
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        int col = (int) Math.floor(touchPos.x / 64);
        int row = (int) Math.floor(touchPos.y / 64);

        showTile(col, row);
        if (isA1(col, row)) {
            tileGrid[col][row] = 11;
        }
        if (isA2(col, row)) {
            tileGrid[col][row] = 12;
        }
        if (isB1(col, row)) {
            tileGrid[col][row] = 21;
        }
        if (isB2(col, row)) {
            tileGrid[col][row] = 22;
        }
        if (isB3(col, row)) {
            tileGrid[col][row] = 23;
        }
        if (isC1(col, row)) {
            tileGrid[col][row] = 31;
        }
        if (isC2(col, row)) {
            tileGrid[col][row] = 32;
        }
        if (isC3(col, row)) {
            tileGrid[col][row] = 33;
        }
    }

    /**
     * 執行
     */
//    private void drawGrid() {
//        float x;
//        float y;
//        Gdx.gl20.glLineWidth(4 / camera.zoom); // line width
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeType.Line);
//        shapeRenderer.setColor(Color.BLACK);
//        for (int i = 0; i < 12; i++) {
//            for (int j = 0; j <= 12; j++) {
//                x = 0 + i * 64;
//                y = 0 + j * 64;
//                shapeRenderer.line(x, y, x, y + height);
//                shapeRenderer.line(x, y, x + width, y);
//
//            }
//        }
//        shapeRenderer.end();
//    }

    /**
     * 畫面設定
     */
    private void showTile(int x, int y) {
        int a1 = a1Grid[x][y];
        int a2 = a2Grid[x][y];
        int b1 = b1Grid[x][y];
        int b2 = b2Grid[x][y];
        int b3 = b3Grid[x][y];
        int c1 = c1Grid[x][y];
        int c2 = c2Grid[x][y];
        int c3 = c3Grid[x][y];

        if (a1 == 11) {
            tileGrid[x][y] = 11;
        } else if (a2 == 12) {
            tileGrid[x][y] = 12;
        } else if (b1 == 21) {
            tileGrid[x][y] = 21;
        } else if (b2 == 22) {
            tileGrid[x][y] = 22;
        } else if (b3 == 23) {
            tileGrid[x][y] = 23;
        } else if (c1 == 31) {
            tileGrid[x][y] = 31;
        } else if (c2 == 32) {
            tileGrid[x][y] = 32;
        } else if (c3 == 33) {
            tileGrid[x][y] = 33;
        } else {
            tileGrid[x][y] = 1;
        }

    }

    public int[][] generate(int grid_x, int grid_y, int db_count, int c_num) {
        int[][] grid = new int[10][10];
        for (int n = 0; n < grid_x; n++) {
            for (int m = 0; m < grid_y; m++) {
                grid[n][m] = 0;
            }
        }

        int mine_value = c_num, mine_x, mine_y;

        for (int k = 0; k < db_count; k++) {
            mine_x = (int) Math.floor(Math.random() * grid_x);
            mine_y = (int) Math.floor(Math.random() * grid_y);

            switch (c_num) {
                case 11:
                    if (grid[mine_x][mine_y] == 11) {
                        k--;
                    }
                    grid[mine_x][mine_y] = mine_value;
                    break;
                case 12:
                    if (a1Grid[mine_x][mine_y] == 11 || grid[mine_x][mine_y] == 12) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 21:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || grid[mine_x][mine_y] == 21) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 22:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || b1Grid[mine_x][mine_y] == 21 || grid[mine_x][mine_y] == 22) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 23:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || b1Grid[mine_x][mine_y] == 21 || b2Grid[mine_x][mine_y] == 22 || grid[mine_x][mine_y] == 23) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 31:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || b1Grid[mine_x][mine_y] == 21 || b2Grid[mine_x][mine_y] == 22 || b3Grid[mine_x][mine_y] == 23 || grid[mine_x][mine_y] == 31) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 32:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || b1Grid[mine_x][mine_y] == 21 || b2Grid[mine_x][mine_y] == 22 || b3Grid[mine_x][mine_y] == 23 || c1Grid[mine_x][mine_y] == 31 || grid[mine_x][mine_y] == 32) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
                case 33:
                    if (a1Grid[mine_x][mine_y] == 11 || a2Grid[mine_x][mine_y] == 12 || b1Grid[mine_x][mine_y] == 21 || b2Grid[mine_x][mine_y] == 22 || b3Grid[mine_x][mine_y] == 23 || c1Grid[mine_x][mine_y] == 31 || c2Grid[mine_x][mine_y] == 32 || grid[mine_x][mine_y] == 33) {
                        k--;
                    } else {
                        grid[mine_x][mine_y] = mine_value;
                    }
                    break;
            }
        }
        
        return grid;
    }

    //  必修  //
    public boolean isA1(int x, int y) {
        return (0 < a1Grid[x][y]);
    }

    public boolean isA2(int x, int y) {
        return (0 < a2Grid[x][y]);
    }

    //  EC  //
    public boolean isB1(int x, int y) {
        return (0 < b1Grid[x][y]);
    }

    public boolean isB2(int x, int y) {
        return (0 < b2Grid[x][y]);
    }

    public boolean isB3(int x, int y) {
        return (0 < b3Grid[x][y]);
    }

    //  IDIC  //
    public boolean isC1(int x, int y) {
        return (0 < c1Grid[x][y]);
    }

    public boolean isC2(int x, int y) {
        return (0 < c2Grid[x][y]);
    }

    public boolean isC3(int x, int y) {
        return (0 < c3Grid[x][y]);
    }

// public boolean isC3(int x, int y,int aaa[][]) {
//        return (0 < aaa[x][y]);
//    }
//    
    // 不知道幹嘛用的 //
    @Override
    public void dispose() {
        batch.dispose();
        blankTexture.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
