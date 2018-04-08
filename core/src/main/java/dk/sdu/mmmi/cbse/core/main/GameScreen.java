/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.math.MathUtils.radDeg;
import dk.sdu.mmmi.cbse.api.IPlugin;
import dk.sdu.mmmi.cbse.api.IProcessor;
import dk.sdu.mmmi.cbse.api.IWorld;
import org.openide.util.Lookup;

/**
 * @author Emil
 */
public class GameScreen implements Screen {

    private final AssetManager assetManager;
    private IWorld world;
    private final SpriteBatch batch;
    private final Viewport viewport;
    private final Texture bg;
    private final FPSLogger fpsLogger;

    GameScreen(final SpriteBatch batch) {
        this.assetManager = new AssetManager();
        this.viewport = new FitViewport(IWorld.WIDTH, IWorld.HEIGHT);
        this.batch = batch;
        this.bg = new Texture("bg5.jpg");
        this.fpsLogger = new FPSLogger();
    }

    @Override
    public void show() {

        world = Lookup.getDefault().lookup(IWorld.class);

        // Start plugins
        Lookup.getDefault().lookupAll(IPlugin.class).forEach(p -> {
            System.out.println(p);
            p.start();
        });

        viewport.apply(true);
        batch.setProjectionMatrix(viewport.getCamera().combined);

        world.getEntities().forEach(System.out::println);
    }

    @Override
    public void resize(final int x, final int y) {
        viewport.update(x, y, true);
    }

    @Override
    public void render(final float dt) {
        // Update processors
        Lookup.getDefault().lookupAll(IProcessor.class).forEach(p -> p.process(dt));

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0, IWorld.WIDTH, IWorld.HEIGHT);
        world.getEntities().forEach(e -> {
            Texture t = assetManager.getAsset(e.getAsset());
            batch.draw(
                    t,
                    e.getX() - t.getWidth() / 2, e.getY() - t.getHeight() / 2,
                    t.getWidth() / 2, t.getHeight() / 2,
                    t.getWidth(), t.getHeight(),
                    1, 1,
                    e.getRotation() * radDeg,
                    0, 0,
                    t.getWidth(), t.getHeight(),
                    false, false
            );
        });
        batch.end();
        fpsLogger.log();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        Lookup.getDefault().lookupAll(IPlugin.class).forEach(IPlugin::stop);
        bg.dispose();
        assetManager.dispose();
    }
}
