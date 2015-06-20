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
import javax.swing.JOptionPane;

public class Minesweeper extends Game {

    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    HashMap<Integer, Rectangle> tiles;
    int[][] tileGrid;

    Texture coveredTexture;
    Texture blankTexture;
    int[][] player;

    int[][] propsGrid;   //地圖每一格的真實狀態

    Texture testTexture;
    Texture playerATexture;
    Texture playerBTexture;

    Texture orangeTexture;
    Texture redTexture;
    Texture whiteTexture;
    Texture blueTexture;

    Texture javaTexture;
    Texture ecTexture;
    Texture c11Texture;
    Texture c12Texture;
    Texture c13Texture;
    Texture c21Texture;
    Texture c22Texture;
    Texture c23Texture;
    Texture c31Texture;
    Texture c32Texture;
    Texture c33Texture;
    Texture c41Texture;
    Texture c42Texture;
    Texture c43Texture;
    Texture n0Texture;
    Texture n1Texture;
    Texture n2Texture;
    Texture n3Texture;

    // 必修 //
    Texture a1Texture;
    Texture a2Texture;

    // EC //
    Texture b1Texture;
    Texture b2Texture;
    Texture b3Texture;

    // IDIC //
    Texture c1Texture;
    Texture c2Texture;
    Texture c3Texture;

    // ERP //
    Texture d1Texture;
    Texture d2Texture;
    Texture d3Texture;

    // Design //
    Texture e1Texture;
    Texture e2Texture;
    Texture e3Texture;

    //道具
    Texture propsTexture;

    // tile width and height
    float width = 64;
    float height = 0;
    int grid_x = 15;
    int grid_y = 12;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1440, 768);

        batch = new SpriteBatch();

        // 必修 //
        a1Texture = new Texture(Gdx.files.internal("super.png"));
        a2Texture = new Texture(Gdx.files.internal("super2.png"));

        // market //
        b1Texture = new Texture(Gdx.files.internal("blue.png"));
        b2Texture = new Texture(Gdx.files.internal("blue.png"));
        b3Texture = new Texture(Gdx.files.internal("blue.png"));

        // idic //
        c1Texture = new Texture(Gdx.files.internal("red.png"));
        c2Texture = new Texture(Gdx.files.internal("red.png"));
        c3Texture = new Texture(Gdx.files.internal("red.png"));

        // ERP //
        d1Texture = new Texture(Gdx.files.internal("white.png"));
        d2Texture = new Texture(Gdx.files.internal("white.png"));
        d3Texture = new Texture(Gdx.files.internal("white.png"));

        // Design //
        e1Texture = new Texture(Gdx.files.internal("orange.png"));
        e2Texture = new Texture(Gdx.files.internal("orange.png"));
        e3Texture = new Texture(Gdx.files.internal("orange.png"));

        //道具
        propsTexture = new Texture(Gdx.files.internal("prop.png"));

        coveredTexture = new Texture(Gdx.files.internal("flower.png"));    // 藍色覆蓋
        blankTexture = new Texture(Gdx.files.internal("gg.png"));    // 灰色底圖

        testTexture = new Texture(Gdx.files.internal("blank.png"));    // 灰色底圖
        playerATexture = new Texture(Gdx.files.internal("A.png"));
        playerBTexture = new Texture(Gdx.files.internal("B.png"));
        
        javaTexture = new Texture(Gdx.files.internal("java.png"));
        ecTexture = new Texture(Gdx.files.internal("ec.png"));

        c11Texture = new Texture(Gdx.files.internal("1_1.png"));
        c12Texture = new Texture(Gdx.files.internal("1_2.png"));
        c13Texture = new Texture(Gdx.files.internal("1_3.png"));
        c21Texture = new Texture(Gdx.files.internal("2_1.png"));
        c22Texture = new Texture(Gdx.files.internal("2_2.png"));
        c23Texture = new Texture(Gdx.files.internal("2_3.png"));
        c31Texture = new Texture(Gdx.files.internal("3_1.png"));
        c32Texture = new Texture(Gdx.files.internal("3_2.png"));
        c33Texture = new Texture(Gdx.files.internal("3_3.png"));
        c41Texture = new Texture(Gdx.files.internal("4_1.png"));
        c42Texture = new Texture(Gdx.files.internal("4_2.png"));
        c43Texture = new Texture(Gdx.files.internal("4_3.png"));
        n0Texture = new Texture(Gdx.files.internal("00.png"));
        n1Texture = new Texture(Gdx.files.internal("01.png"));
        n2Texture = new Texture(Gdx.files.internal("02.png"));
        n3Texture = new Texture(Gdx.files.internal("03.png"));

        blueTexture = new Texture(Gdx.files.internal("blue.png"));
        redTexture = new Texture(Gdx.files.internal("red.png"));
        whiteTexture = new Texture(Gdx.files.internal("white.png"));
        orangeTexture = new Texture(Gdx.files.internal("orange.png"));

        shapeRenderer = new ShapeRenderer();

        tiles = new HashMap<Integer, Rectangle>();  // 初始化地圖的大小（？
        tileGrid = new int[24][12];
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                initTiles(i, j, 0, 64, 64);
            }
        }
//        for (int i = 12; i < 22; i++) {
//            for (int j = 0; j < 12; j++) {
//                initTiles(i, j, -1, 64, 64);
//            }
//        }

        player = new int[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                player[i][j] = 0;
            }
        }

        // 隨機產生地圖物件
        propsGrid = generate(12, 12);

        // 這邊應該是在做玩家連線的
        MyGestureListener mgl = new MyGestureListener();
        mgl.setGame(this);
        GestureDetector gd = new GestureDetector(mgl); // 偵測連線？
        Gdx.input.setInputProcessor(gd);
    }

    /**
     * 應該是初始化地圖大小
     */
    private void initTiles(int i, int j, int a, int in, int jn) {
        Rectangle tile = new Rectangle();
        // 藍色框框大小
        tile.x = 0 + i * in;
        tile.y = 0 + j * jn;
        // 不知道幹嘛用的
        tile.width = 64;
        tile.height = 64;

        Integer size = tiles.size();
        tiles.put(size, tile);
        tileGrid[i][j] = a;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255, 255, 255, 0);
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
            } else if (state == 41) {
                texture = d1Texture;
            } else if (state == 42) {
                texture = d2Texture;
            } else if (state == 43) {
                texture = d3Texture;
            } else if (state == 51) {
                texture = e1Texture;
            } else if (state == 52) {
                texture = e2Texture;
            } else if (state == 53) {
                texture = e3Texture;
            } else if (state == 61 || state == 62 || state == 63 || state == 64 || state == 65 || state == 66 || state == 67 || state == 68) {
                texture = propsTexture;
            } 
//            else if (state == -1) {
//                texture = testTexture;
//            }
            batch.draw(texture, tile.x, tile.y, 64, 64);
        }
        
        // A //
        batch.draw(playerATexture, 798, 704, 64, 64);
        
        batch.draw(blueTexture, 798, 576, 64, 64);
        batch.draw(c11Texture, 862, 576, 64, 64);
        batch.draw(n0Texture, 926, 576, 64, 64);
        batch.draw(c12Texture, 990, 576, 64, 64);
        batch.draw(n0Texture, 1054, 576, 64, 64);
        batch.draw(c13Texture, 1118, 576, 64, 64);
        batch.draw(n0Texture, 1182, 576, 64, 64);
        
        batch.draw(redTexture, 798, 512, 64, 64);
        batch.draw(c21Texture, 862, 512, 64, 64);
        batch.draw(n0Texture, 926, 512, 64, 64);
        batch.draw(c22Texture, 990, 512, 64, 64);
        batch.draw(n0Texture, 1054, 512, 64, 64);
        batch.draw(c23Texture, 1118, 512, 64, 64);
        batch.draw(n0Texture, 1182, 512, 64, 64);
        
        batch.draw(whiteTexture, 798, 448, 64, 64);
        batch.draw(c31Texture, 862, 448, 64, 64);
        batch.draw(n0Texture, 926, 448, 64, 64);
        batch.draw(c32Texture, 990, 448, 64, 64);
        batch.draw(n0Texture, 1054, 448, 64, 64);
        batch.draw(c33Texture, 1118, 448, 64, 64);
        batch.draw(n0Texture, 1182, 448, 64, 64);
        
        batch.draw(orangeTexture, 798, 384, 64, 64);
        batch.draw(c41Texture, 862, 384, 64, 64);
        batch.draw(n0Texture, 926, 384, 64, 64);
        batch.draw(c42Texture, 990, 384, 64, 64);
        batch.draw(n0Texture, 1054, 384, 64, 64);
        batch.draw(c43Texture, 1118, 384, 64, 64);
        batch.draw(n3Texture, 1182, 384, 64, 64);
        
        batch.draw(a1Texture, 1246, 576, 64, 64);
        batch.draw(javaTexture, 1310, 576, 64, 64);
        batch.draw(n2Texture, 1374, 576, 64, 64);
        
        batch.draw(a2Texture, 1246, 512, 64, 64);
        batch.draw(ecTexture, 1310, 512, 64, 64);
        batch.draw(n1Texture, 1374, 512, 64, 64);
        
        batch.draw(propsTexture, 1246, 448, 64, 64);
        batch.draw(n0Texture, 1310, 448, 64, 64);


        // B //
        batch.draw(playerBTexture, 798, 320, 64, 64);
        
        batch.draw(blueTexture, 798, 192, 64, 64);
        batch.draw(c11Texture, 862, 192, 64, 64);
        batch.draw(n0Texture, 926, 192, 64, 64);
        batch.draw(c12Texture, 990, 192, 64, 64);
        batch.draw(n0Texture, 1054, 192, 64, 64);
        batch.draw(c13Texture, 1118, 192, 64, 64);
        batch.draw(n0Texture, 1182, 192, 64, 64);
        
        batch.draw(redTexture, 798, 128, 64, 64);
        batch.draw(c21Texture, 862, 128, 64, 64);
        batch.draw(n0Texture, 926, 128, 64, 64);
        batch.draw(c22Texture, 990, 128, 64, 64);
        batch.draw(n0Texture, 1054, 128, 64, 64);
        batch.draw(c23Texture, 1118, 128, 64, 64);
        batch.draw(n0Texture, 1182, 128, 64, 64);
        
        batch.draw(whiteTexture, 798, 64, 64, 64);
        batch.draw(c31Texture, 862, 64, 64, 64);
        batch.draw(n0Texture, 926, 64, 64, 64);
        batch.draw(c32Texture, 990, 64, 64, 64);
        batch.draw(n0Texture, 1054, 64, 64, 64);
        batch.draw(c33Texture, 1118, 64, 64, 64);
        batch.draw(n0Texture, 1182, 64, 64, 64);
        
        batch.draw(orangeTexture, 798, 0, 64, 64);
        batch.draw(c41Texture, 862, 0, 64, 64);
        batch.draw(n0Texture, 926, 0, 64, 64);
        batch.draw(c42Texture, 990, 0, 64, 64);
        batch.draw(n0Texture, 1054, 0, 64, 64);
        batch.draw(c43Texture, 1118, 0, 64, 64);
        batch.draw(n3Texture, 1182, 0, 64, 64);
        
        batch.draw(a1Texture, 1246, 192, 64, 64);
        batch.draw(javaTexture, 1310, 192, 64, 64);
        batch.draw(n2Texture, 1374, 192, 64, 64);
        
        batch.draw(a2Texture, 1246, 128, 64, 64);
        batch.draw(ecTexture, 1310, 128, 64, 64);
        batch.draw(n1Texture, 1374, 128, 64, 64);
        
        batch.draw(propsTexture, 1246, 64, 64, 64);
        batch.draw(n0Texture, 1310, 64, 64, 64);
        
        
        
        
        
        
        
        
        batch.end();

        drawGrid();
    }

    private void drawGrid() {
        float x;
        float y;
        Gdx.gl20.glLineWidth(1 / camera.zoom); // line width
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                x = 0 + i * 64;
                y = 0 + j * 64;
                shapeRenderer.line(x, 768, x, y);
                shapeRenderer.line(x, y, 768, y);

            }
        }
        shapeRenderer.line(768, 384, 1438, 384);
        shapeRenderer.end();
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
        judgePlayer(col, row);

        if (isNoWinning()) {
            int a = JOptionPane.showConfirmDialog(null, "這局無輸贏，是否重來", "遊戲提示", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (a == 0) {
                create();
            }
        }
    }

    private void judgePlayer(int x, int y) {
        int playerchoose = propsGrid[x][y];
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        switch (playerchoose) {
            case 11:
                player[0][0]++;
                
                break;
            case 12:
                player[0][1]++;
                break;
            case 21:
                player[1][0]++;
                batch.draw(n1Texture, 926, 576, 64, 64);
                break;
            case 22:
                player[1][1]++;
                break;
            case 23:
                player[1][2]++;
                break;
            case 31:
                player[2][0]++;
                break;
            case 32:
                player[2][1]++;
                break;
            case 33:
                player[2][2]++;
                break;
            case 41:
                player[3][0]++;
                break;
            case 42:
                player[3][1]++;
                break;
            case 43:
                player[3][2]++;
                break;
            case 51:
                player[4][0]++;
                break;
            case 52:
                player[4][1]++;
                break;
            case 53:
                player[4][2]++;
                break;

        }
        printGrid(player, 5, 3);
        batch.end();

    }

    private void VictoryFactor() {
        if (player[0][0] > 0 && player[0][1] > 0) {
            if (player[1][0] > 0 && player[1][1] > 0 && player[1][2] > 0) {
                System.out.println("恭喜您已修完網路行銷學程可以畢業囉!!!");
            } else if (player[2][0] > 0 && player[2][1] > 0 && player[2][2] > 0) {
                System.out.println("恭喜您已修完資通訊學程可以畢業囉!!!");
            } else if (player[3][0] > 0 && player[3][1] > 0 && player[3][2] > 0) {
                System.out.println("恭喜您已修完ERP學程可以畢業囉!!!");
            } else if (player[4][0] > 0 && player[4][1] > 0 && player[4][2] > 0) {
                System.out.println("恭喜您已修完互動設計學程可以畢業囉!!!");
            }
        }

    }

    /**
     * 畫黑線
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
        int props = propsGrid[x][y];
        if (props == 0) {
            tileGrid[x][y] = 1;
        } else {
            tileGrid[x][y] = props;
        }

        printGrid(tileGrid, 20, 12);
    }

    public int[][] generate(int grid_x, int grid_y) {
        int[][] grid = new int[12][12];
        int db_count = 0, c_num = 0;
        for (int n = 0; n < grid_x; n++) {
            for (int m = 0; m < grid_y; m++) {
                grid[n][m] = 0;
            }
        }
        printGrid(grid, 12, 12);
        for (int i = 0; i < 22; i++) {
            switch (i) {
                case 0:
                    db_count = 2;
                    c_num = 11;
                    break;
                case 1:
                    db_count = 2;
                    c_num = 12;
                    break;
                case 2:
                    db_count = 3;
                    c_num = 21;
                    break;
                case 3:
                    db_count = 3;
                    c_num = 22;
                    break;
                case 4:
                    db_count = 3;
                    c_num = 23;
                    break;
                case 5:
                    db_count = 3;
                    c_num = 31;
                    break;
                case 6:
                    db_count = 3;
                    c_num = 32;
                    break;
                case 7:
                    db_count = 3;
                    c_num = 33;
                    break;
                case 8:
                    db_count = 3;
                    c_num = 41;
                    break;
                case 9:
                    db_count = 3;
                    c_num = 42;
                    break;
                case 10:
                    db_count = 3;
                    c_num = 43;
                    break;
                case 11:
                    db_count = 3;
                    c_num = 51;
                    break;
                case 12:
                    db_count = 3;
                    c_num = 52;
                    break;
                case 13:
                    db_count = 3;
                    c_num = 53;
                    break;
                case 14:
                    db_count = 5;
                    c_num = 61;
                    break;
                case 15:
                    db_count = 3;
                    c_num = 62;
                    break;
                case 16:
                    db_count = 2;
                    c_num = 63;
                    break;
                case 17:
                    db_count = 1;
                    c_num = 64;
                    break;
                case 18:
                    db_count = 2;
                    c_num = 65;
                    break;
                case 19:
                    db_count = 1;
                    c_num = 66;
                    break;
                case 20:
                    db_count = 5;
                    c_num = 67;
                    break;
                case 21:
                    db_count = 2;
                    c_num = 68;
                    break;

            }
            int mine_x, mine_y;
            for (int k = 0; k < db_count; k++) {
                mine_x = (int) Math.floor(Math.random() * grid_x);
                mine_y = (int) Math.floor(Math.random() * grid_y);
                if (grid[mine_x][mine_y] != 0) {
                    k--;
                } else {
                    grid[mine_x][mine_y] = c_num;
                }

            }

        }
        printGrid(grid, 12, 12);
        return grid;
    }

    public boolean isHidden(int x, int y) { //判斷是否按完了
        return (0 == tileGrid[x][y]);
    }

    public boolean isNoWinning() {//沒輸沒贏
        int hiddenTiles = 0;

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                if (isHidden(x, y)) {
                    hiddenTiles++;
                }
            }
        }

        return (0 == hiddenTiles);//沒有數字全踩完嚕
    }

    private void printGrid(int[][] grid, int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.printf("%d ", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

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
