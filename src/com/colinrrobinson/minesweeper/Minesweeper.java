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
    int[][] playerA;
    int[][] playerB;
    int[][] temporary;
    int[][] propsGrid;   //地圖每一格的真實狀態

    // 寶藏跟大白菜跟道具
    Texture coveredTexture, blankTexture, propsTexture;

    // 玩家跟背景圖
    Texture infoBackgroundTexture, playerATexture, playerBTexture;

    // 課程書跟必修
    Texture orangeTexture, redTexture, whiteTexture, blueTexture, javaTexture, ecTexture;

    // 網路行銷課程
    Texture c11Texture, c12Texture, c13Texture;

    // idic課程
    Texture c21Texture, c22Texture, c23Texture;

    // erp課程
    Texture c31Texture, c32Texture, c33Texture;

    // 互動設計課程
    Texture c41Texture, c42Texture, c43Texture;

    // 分數
    Texture n0Texture, n1Texture, n2Texture, n3Texture;

    // 必修卷軸*2
    Texture ecInMapTexture, javaInMapTexture;

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
        ecInMapTexture = new Texture(Gdx.files.internal("ecInMap.png"));
        javaInMapTexture = new Texture(Gdx.files.internal("javaInMap.png"));

        // market //
        blueTexture = new Texture(Gdx.files.internal("blue.png"));

        // idic //
        redTexture = new Texture(Gdx.files.internal("red.png"));

        // ERP //
        whiteTexture = new Texture(Gdx.files.internal("white.png"));

        // Design //
        orangeTexture = new Texture(Gdx.files.internal("orange.png"));

        //道具
        propsTexture = new Texture(Gdx.files.internal("prop.png"));

        coveredTexture = new Texture(Gdx.files.internal("treasure.png"));
        blankTexture = new Texture(Gdx.files.internal("vegetable.png"));

        playerATexture = new Texture(Gdx.files.internal("A.png"));
        playerBTexture = new Texture(Gdx.files.internal("B.png"));

        ecTexture = new Texture(Gdx.files.internal("ec.png"));
        javaTexture = new Texture(Gdx.files.internal("java.png"));

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

        infoBackgroundTexture = new Texture(Gdx.files.internal("infoBackgroundTexture.png"));

        shapeRenderer = new ShapeRenderer();

        tiles = new HashMap<Integer, Rectangle>();
        tileGrid = new int[24][12];

        // 左邊背景
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                initTiles(i, j, 0, 64, 64);
            }
        }

        // 右邊背景
        for (int i = 12; i < 24; i++) {
            for (int j = 0; j < 12; j++) {
                initTiles(i, j, -1, 64, 64);
            }
        }

        // 隨機產生地圖物件
        propsGrid = generate(12, 12);

        playerA = new int[5][3];
        playerB = new int[5][3];
        temporary = new int[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                playerA[i][j] = 0;
                playerB[i][j] = 0;
                temporary[i][j] = 0;
            }
        }

        // 玩家連線的
        MyGestureListener mgl = new MyGestureListener();
        mgl.setGame(this);
        GestureDetector gd = new GestureDetector(mgl);
        Gdx.input.setInputProcessor(gd);
    }

    /**
     * 初始化地圖
     */
    private void initTiles(int i, int j, int a, int in, int jn) {
        Rectangle tile = new Rectangle();
        // 藍色框框大小
        tile.x = 0 + i * in;
        tile.y = 0 + j * jn;
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
                texture = ecInMapTexture;
            } else if (state == 12) {
                texture = javaInMapTexture;
            } else if (state == 21 || state == 22 || state == 23) {
                texture = blueTexture;
            } else if (state == 31 || state == 32 || state == 33) {
                texture = redTexture;
            } else if (state == 41 || state == 42 || state == 43) {
                texture = whiteTexture;
            } else if (state == 51 || state == 52 || state == 53) {
                texture = orangeTexture;
            } else if (state == 61 || state == 62 || state == 63 || state == 64 || state == 65 || state == 66 || state == 67 || state == 68) {
                texture = propsTexture;
            } else if (state == -1) {
                texture = infoBackgroundTexture;
            }

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
        batch.draw(n0Texture, 1182, 384, 64, 64);

        batch.draw(ecInMapTexture, 1246, 576, 64, 64);
        batch.draw(ecTexture, 1310, 576, 64, 64);
        batch.draw(n0Texture, 1374, 576, 64, 64);

        batch.draw(javaInMapTexture, 1246, 512, 64, 64);
        batch.draw(javaTexture, 1310, 512, 64, 64);
        batch.draw(n0Texture, 1374, 512, 64, 64);

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
        batch.draw(n0Texture, 1182, 0, 64, 64);

        batch.draw(ecInMapTexture, 1246, 192, 64, 64);
        batch.draw(ecTexture, 1310, 192, 64, 64);
        batch.draw(n0Texture, 1374, 192, 64, 64);

        batch.draw(javaInMapTexture, 1246, 128, 64, 64);
        batch.draw(javaTexture, 1310, 128, 64, 64);
        batch.draw(n0Texture, 1374, 128, 64, 64);

        batch.draw(propsTexture, 1246, 64, 64, 64);
        batch.draw(n0Texture, 1310, 64, 64, 64);

        Texture texture1 = n0Texture;
        int xline = 0, yline = 0;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                if (x == 0 && y == 0) {
                    xline = 1374;
                    yline = 576;
                } else if (x == 0 && y == 1) {
                    xline = 1374;
                    yline = 512;
                } else {
                    if (x == 1) {
                        yline = 576;
                    }
                    if (x == 2) {
                        yline = 512;
                    }
                    if (x == 3) {
                        yline = 448;
                    }
                    if (x == 4) {
                        yline = 384;
                    }
                    if (y == 0) {
                        xline = 926;
                    }
                    if (y == 1) {
                        xline = 1054;
                    }
                    if (y == 2) {
                        xline = 1182;
                    }

                }

                if (playerA[x][y] == 0) {
                    texture1 = n0Texture;
                } else if (playerA[x][y] == 1) {
                    texture1 = n1Texture;
                } else if (playerA[x][y] == 2) {
                    texture1 = n2Texture;
                } else if (playerA[x][y] == 3) {
                    texture1 = n3Texture;
                }
                batch.draw(texture1, xline, yline, 64, 64);
            }

        }

        batch.end();

        drawGrid();
    }

    /**
     * 亂數產生地圖物件
     */
    public int[][] generate(int grid_x, int grid_y) {
        int[][] grid = new int[24][12];
        int db_count = 0, c_num = 0;
        for (int n = 0; n < grid_x; n++) {
            for (int m = 0; m < grid_y; m++) {
                grid[n][m] = 0;
            }
        }
        //printGrid(grid, 12, 12);
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
        // 右邊背景
        for (int n = 12; n < 24; n++) {
            for (int m = 0; m < grid_y; m++) {
                grid[n][m] = -1;
            }
        }
        printGrid(grid, 12, 12);
        return grid;
    }

    /**
     * 畫框線
     */
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
        shapeRenderer.line(768, 384, 1438, 384);    // 右邊中間那條
        shapeRenderer.end();
    }

    /**
     * 畫面點擊跟平手判斷
     */
    public void processTouch() {
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        int col = (int) Math.floor(touchPos.x / 64);
        int row = (int) Math.floor(touchPos.y / 64);
        showTile(col, row);
        judgePlayer(col, row);
        VictoryFactor();
        if (isNoWinning()) {
            int a = JOptionPane.showConfirmDialog(null, "這局無輸贏，是否重來", "遊戲提示", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (a == 0) {
                create();
            }
        }
    }

    /**
     * 點擊觸發後更改實際畫面
     */
    private void showTile(int x, int y) {
        int props = propsGrid[x][y];

        if (props == 0) {
            tileGrid[x][y] = 1;
        } else {
            tileGrid[x][y] = props;
        }

        //printGrid(tileGrid, 24, 12);
    }

    /**
     * 判斷玩家按的座標值並且記錄
     */
    private void judgePlayer(int x, int y) {
        int playerchoose = propsGrid[x][y];
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        switch (playerchoose) {
            case 11:
                playerA[0][0]++;
                break;
            case 12:
                playerA[0][1]++;
                break;
            case 21:
                playerA[1][0]++;
                break;
            case 22:
                playerA[1][1]++;
                break;
            case 23:
                playerA[1][2]++;
                break;
            case 31:
                playerA[2][0]++;
                break;
            case 32:
                playerA[2][1]++;
                break;
            case 33:
                playerA[2][2]++;
                break;
            case 41:
                playerA[3][0]++;
                break;
            case 42:
                playerA[3][1]++;
                break;
            case 43:
                playerA[3][2]++;
                break;
            case 51:
                playerA[4][0]++;
                break;
            case 52:
                playerA[4][1]++;
                break;
            case 53:
                playerA[4][2]++;
                break;
            case 61:
                propsEffect(1);
                break;
            case 62:
                propsEffect(2);
                break;
            case 63:
                propsEffect(3);
                break;
            case 64:
                propsEffect(4);
                break;
            case 65:
                propsEffect(5);
                break;
            case 66:
                propsEffect(6);
                break;
            case 67:
                propsEffect(7);
                break;
            case 68:
                propsEffect(8);
                break;

        }
        printGrid(playerA, 5, 3);
        batch.end();

    }

    /**
     * 道具功能判斷
     */
    private void propsEffect(int x) {
        int xSite, ySite;
        switch (x) {
//            case 1:
//                for (int k = 0; k < 1; k++) {
//                    xSite = (int) Math.floor(Math.random() * 5);
//                    ySite = (int) Math.floor(Math.random() * 3);
//                    if (playerB[xSite][ySite] != 0 && xSite != 0 && ySite != 2) {
//                        playerA[xSite][ySite]++;
//                        playerB[xSite][ySite]--;
//                    } else {
//                        k--;
//                    }
//                }
//                break;
            case 2:
                for (int k = 0; k < 1; k++) {
                    xSite = (int) Math.floor(Math.random() * 5);
                    ySite = (int) Math.floor(Math.random() * 3);
                    if (playerA[xSite][ySite] != 0 && xSite != 0 && ySite != 2) {
                        playerA[xSite][ySite]--;
                    } else {
                        k--;
                    }
                }
                break;
            case 3:
                temporary = playerA;
                playerA = playerB;
                playerB = temporary;
                break;
            case 4:
                for (int k = 0; k < 1; k++) {
                    xSite = (int) Math.floor(Math.random() * 5);
                    ySite = (int) Math.floor(Math.random() * 3);
                    if (playerA[xSite][ySite] != 0 && xSite != 0 && ySite != 2) {
                        playerA[0][1]++;
                        playerA[xSite][ySite]--;
                    } else {
                        k--;
                    }
                }
                break;
            case 5:

                break;
            case 6:
                playerA[0][0] = 1;
                break;
            case 7:
                playerA[0][0] = 0;
                break;
            case 8:
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 3; j++) {
                        playerA[i][j] = playerA[i][j] / 2;
                    }
                }
                break;

        }

    }

    /**
     * 判斷是否按完了
     */
    public boolean isHidden(int x, int y) {
        return (0 == tileGrid[x][y]);
    }

    /**
     * 沒輸沒贏的判斷
     */
    public boolean isNoWinning() {
        int hiddenTiles = 0;

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 12; y++) {
                if (isHidden(x, y)) {
                    hiddenTiles++;
                }
            }
        }
        //沒有數字全踩完嚕
        return (0 == hiddenTiles);
    }
    
    /**
     * 勝利條件判斷
     */
    private void VictoryFactor() {
        if (playerA[0][0] > 0 && playerA[0][1] > 0) {
            if (playerA[1][0] > 0 && playerA[1][1] > 0 && playerA[1][2] > 0) {
                System.out.println("恭喜您已修完網路行銷學程可以畢業囉!!!");
            } else if (playerA[2][0] > 0 && playerA[2][1] > 0 && playerA[2][2] > 0) {
                System.out.println("恭喜您已修完資通訊學程可以畢業囉!!!");
            } else if (playerA[3][0] > 0 && playerA[3][1] > 0 && playerA[3][2] > 0) {
                System.out.println("恭喜您已修完ERP學程可以畢業囉!!!");
            } else if (playerA[4][0] > 0 && playerA[4][1] > 0 && playerA[4][2] > 0) {
                System.out.println("恭喜您已修完互動設計學程可以畢業囉!!!");
            }
        }

    }

    /**
     * 測試用印出陣列
     */
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
