/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.api.IAssetManager;
import dk.sdu.mmmi.cbse.api.IPlugin;
import dk.sdu.mmmi.cbse.api.IWorld;
import dk.sdu.mmmi.cbse.library.CollisionAbility;
import dk.sdu.mmmi.cbse.library.MoveAbility;
import java.io.File;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.random;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 * @author Emil
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IPlugin.class)
})
public class PlayerPlugin implements IPlugin {

    private static final String ASSET_KEY = "player";
    private static final String ASSET_PATH = "spaceship.png";
    private static final float ACCELERATION = 64;
    private static final float DECELERATION = 8;
    private static final float MAX_SPEED = 512;
    private static final float ROTATION_SPEED = 2;
    private static final int HEALTH_POINTS = 2;
    private static final int DAMAGE = 0;
    private static final int HIT_RADIUS = 100;

    private final IAssetManager assetManager;
    private final IWorld world;

    public PlayerPlugin() {

        world = Lookup.getDefault().lookup(IWorld.class);
        assetManager = Lookup.getDefault().lookup(IAssetManager.class);
    }

    @Override
    public void start() {
        assetManager.loadAsset(ASSET_KEY, new File(getClass()
                .getClassLoader()
                .getResource(ASSET_PATH)
                .getFile()
        ));

        Player player = new Player(
                ASSET_KEY,
                HEALTH_POINTS,
                new MoveAbility(ACCELERATION, DECELERATION, MAX_SPEED, ROTATION_SPEED),
                new CollisionAbility(DAMAGE, HIT_RADIUS)
        );
        player.setX(IWorld.WIDTH / 2);
        player.setY(IWorld.HEIGHT / 2);
        player.setRotation((float) (random() * 2 * PI));
        world.addEntity(player);
    }

    @Override
    public void stop() {
        assetManager.unloadAsset(ASSET_KEY);
        List<Player> entities = world.getEntities(Player.class);
        world.removeEntities(entities);
    }
}
