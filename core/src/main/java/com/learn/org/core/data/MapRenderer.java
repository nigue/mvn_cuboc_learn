package com.learn.org.core.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.math.Vector3;

public class MapRenderer {

    private Map map;
    private OrthographicCamera cam;
    private SpriteCache cache;
    private SpriteBatch batch;
    private ImmediateModeRenderer20 renderer;
    private int[][] blocks;
    private TextureRegion tile;
    private Animation bobLeft;
    private Animation bobRight;
    private Animation bobJumpLeft;
    private Animation bobJumpRight;
    private Animation bobIdleLeft;
    private Animation bobIdleRight;
    private Animation bobDead;
    private Animation zap;
    private TextureRegion cube;
    private Animation cubeFixed;
    private TextureRegion cubeControlled;
    private TextureRegion dispenser;
    private Animation spawn;
    private Animation dying;
    private TextureRegion spikes;
    private Animation rocket;
    private Animation rocketExplosion;
    private TextureRegion rocketPad;
    private TextureRegion endDoor;
    private TextureRegion movingSpikes;
    private TextureRegion laser;
    private FPSLogger fps;
    private float stateTime = 0;
    private Vector3 lerpTarget = new Vector3();

    public MapRenderer(Map map) {
        this.batch = new SpriteBatch(5460);
        this.renderer = new ImmediateModeRenderer20(false, true, 0);
        this.fps = new FPSLogger();
        this.stateTime = 0;
        this.lerpTarget = new Vector3();

        this.map = map;
        this.cam = new OrthographicCamera(24, 16);
        this.cam.position.set(map.getBob().getPos().x, map.getBob().getPos().y, 0);
        this.cache = new SpriteCache(this.map.getTiles().length * this.map.getTiles()[0].length, false);
        this.blocks = new int[(int) Math.ceil(this.map.getTiles().length / 24.0f)][(int) Math.ceil(this.map.getTiles()[0].length / 16.0f)];

        createAnimations();
        createBlocks();
    }

    private void createBlocks() {

        int width = getMap().getTiles().length;
        int height = getMap().getTiles()[0].length;
        for (int blockY = 0; blockY < getBlocks()[0].length; blockY++) {

            for (int blockX = 0; blockX < blocks.length; blockX++) {

                getCache().beginCache();

                for (int y = blockY * 16; y < blockY * 16 + 16; y++) {
                    for (int x = blockX * 24; x < blockX * 24 + 24; x++) {
                        if (x > width) {
                            continue;
                        }
                        if (y > height) {
                            continue;
                        }
                        int posX = x;
                        int posY = height - y - 1;
                        if (getMap().match(getMap().getTiles()[x][y], Map.getTILE())) {
                            getCache().add(getTile(), posX, posY, 1, 1);
                        }
                        if (getMap().match(getMap().getTiles()[x][y], Map.getSPIKES())) {
                            getCache().add(getSpikes(), posX, posY, 1, 1);
                        }
                    }

                }

                getBlocks()[blockX][blockY] = getCache().endCache();
            }
        }
        Gdx.app.debug("Cubocy", "blocks created");
    }

    private void createAnimations() {

        setTile(new TextureRegion(new Texture(Gdx.files.internal("tile.png")), 0, 0, 20, 20));

        Texture bobTexture = new Texture(Gdx.files.internal("bob.png"));
        TextureRegion[] split = new TextureRegion(bobTexture).split(20, 20)[0];
        TextureRegion[] mirror = new TextureRegion(bobTexture).split(20, 20)[0];
        for (TextureRegion region : mirror) {
            region.flip(true, false);
        }
        setSpikes(split[5]);
        setBobRight(new Animation(0.1f, split[0], split[1]));
        setBobLeft(new Animation(0.1f, mirror[0], mirror[1]));
        setBobJumpRight(new Animation(0.1f, split[2], split[3]));
        setBobJumpLeft(new Animation(0.1f, mirror[2], mirror[3]));
        setBobIdleRight(new Animation(0.5f, split[0], split[4]));
        setBobIdleLeft(new Animation(0.5f, mirror[0], mirror[4]));
        setBobDead(new Animation(0.2f, split[0]));
        split = new TextureRegion(bobTexture).split(20, 20)[1];
        setCube(split[0]);
        setCubeFixed(new Animation(1, split[1], split[2], split[3], split[4], split[5]));
        split = new TextureRegion(bobTexture).split(20, 20)[2];
        setCubeControlled(split[0]);
        setSpawn(new Animation(0.1f, split[4], split[3], split[2], split[1]));
        setDying(new Animation(0.1f, split[1], split[2], split[3], split[4]));
        setDispenser(split[5]);
        split = new TextureRegion(bobTexture).split(20, 20)[3];
        setRocket(new Animation(0.1f, split[0], split[1], split[2], split[3]));
        setRocketPad(split[4]);
        split = new TextureRegion(bobTexture).split(20, 20)[4];
        setRocketExplosion(new Animation(0.1f, split[0], split[1], split[2], split[3], split[4], split[4]));
        split = new TextureRegion(bobTexture).split(20, 20)[5];
        setEndDoor(split[2]);
        setMovingSpikes(split[0]);
        setLaser(split[1]);
    }

    public void render(float deltaTime) {
        if (getMap().getCube().getState() != Cube.getCONTROLLED()) {
            getCam().position.lerp(getLerpTarget().set(getMap().getBob().getPos().x, getMap().getBob().getPos().y, 0), 2f * deltaTime);
        } else {
            getCam().position.lerp(getLerpTarget().set(getMap().getCube().getPos().x, getMap().getCube().getPos().y, 0), 2f * deltaTime);
        }
        getCam().update();

        renderLaserBeams();
    }

    private void renderLaserBeams() {
        getCam().update(false);
        getRenderer().begin(getCam().combined, GL20.GL_LINES);
        for (int i = 0; i < getMap().getLasers().size; i++) {
            Laser laser = getMap().getLasers().get(i);
            float sx = laser.getStartPoint().x, sy = laser.getStartPoint().y;
            float ex = laser.getCappedEndPoint().x, ey = laser.getCappedEndPoint().y;
            getRenderer().color(0, 1, 0, 1);
            getRenderer().vertex(sx, sy, 0);
            getRenderer().color(0, 1, 0, 1);
            getRenderer().vertex(ex, ey, 0);
        }
        getRenderer().end();
    }

    public void dispose() {
        getCache().dispose();
        getBatch().dispose();
        getTile().getTexture().dispose();
        getCube().getTexture().dispose();
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }

    public SpriteCache getCache() {
        return cache;
    }

    public void setCache(SpriteCache cache) {
        this.cache = cache;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public ImmediateModeRenderer20 getRenderer() {
        return renderer;
    }

    public void setRenderer(ImmediateModeRenderer20 renderer) {
        this.renderer = renderer;
    }

    public int[][] getBlocks() {
        return blocks;
    }

    public TextureRegion getTile() {
        return tile;
    }

    public void setTile(TextureRegion tile) {
        this.tile = tile;
    }

    public Animation getBobLeft() {
        return bobLeft;
    }

    public void setBobLeft(Animation bobLeft) {
        this.bobLeft = bobLeft;
    }

    public Animation getBobRight() {
        return bobRight;
    }

    public void setBobRight(Animation bobRight) {
        this.bobRight = bobRight;
    }

    public Animation getBobJumpLeft() {
        return bobJumpLeft;
    }

    public void setBobJumpLeft(Animation bobJumpLeft) {
        this.bobJumpLeft = bobJumpLeft;
    }

    public Animation getBobJumpRight() {
        return bobJumpRight;
    }

    public void setBobJumpRight(Animation bobJumpRight) {
        this.bobJumpRight = bobJumpRight;
    }

    public Animation getBobIdleLeft() {
        return bobIdleLeft;
    }

    public void setBobIdleLeft(Animation bobIdleLeft) {
        this.bobIdleLeft = bobIdleLeft;
    }

    public Animation getBobIdleRight() {
        return bobIdleRight;
    }

    public void setBobIdleRight(Animation bobIdleRight) {
        this.bobIdleRight = bobIdleRight;
    }

    public Animation getBobDead() {
        return bobDead;
    }

    public void setBobDead(Animation bobDead) {
        this.bobDead = bobDead;
    }

    public Animation getZap() {
        return zap;
    }

    public void setZap(Animation zap) {
        this.zap = zap;
    }

    public TextureRegion getCube() {
        return cube;
    }

    public void setCube(TextureRegion cube) {
        this.cube = cube;
    }

    public Animation getCubeFixed() {
        return cubeFixed;
    }

    public void setCubeFixed(Animation cubeFixed) {
        this.cubeFixed = cubeFixed;
    }

    public TextureRegion getCubeControlled() {
        return cubeControlled;
    }

    public void setCubeControlled(TextureRegion cubeControlled) {
        this.cubeControlled = cubeControlled;
    }

    public TextureRegion getDispenser() {
        return dispenser;
    }

    public void setDispenser(TextureRegion dispenser) {
        this.dispenser = dispenser;
    }

    public Animation getSpawn() {
        return spawn;
    }

    public void setSpawn(Animation spawn) {
        this.spawn = spawn;
    }

    public Animation getDying() {
        return dying;
    }

    public void setDying(Animation dying) {
        this.dying = dying;
    }

    public TextureRegion getSpikes() {
        return spikes;
    }

    public void setSpikes(TextureRegion spikes) {
        this.spikes = spikes;
    }

    public Animation getRocket() {
        return rocket;
    }

    public void setRocket(Animation rocket) {
        this.rocket = rocket;
    }

    public Animation getRocketExplosion() {
        return rocketExplosion;
    }

    public void setRocketExplosion(Animation rocketExplosion) {
        this.rocketExplosion = rocketExplosion;
    }

    public TextureRegion getRocketPad() {
        return rocketPad;
    }

    public void setRocketPad(TextureRegion rocketPad) {
        this.rocketPad = rocketPad;
    }

    public TextureRegion getEndDoor() {
        return endDoor;
    }

    public void setEndDoor(TextureRegion endDoor) {
        this.endDoor = endDoor;
    }

    public TextureRegion getMovingSpikes() {
        return movingSpikes;
    }

    public void setMovingSpikes(TextureRegion movingSpikes) {
        this.movingSpikes = movingSpikes;
    }

    public TextureRegion getLaser() {
        return laser;
    }

    public void setLaser(TextureRegion laser) {
        this.laser = laser;
    }

    public FPSLogger getFps() {
        return fps;
    }

    public void setFps(FPSLogger fps) {
        this.fps = fps;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Vector3 getLerpTarget() {
        return lerpTarget;
    }

    public void setLerpTarget(Vector3 lerpTarget) {
        this.lerpTarget = lerpTarget;
    }
}
