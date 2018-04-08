/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package dk.sdu.mmmi.cbse.player;

import dk.sdu.mmmi.cbse.api.IInputService;
import dk.sdu.mmmi.cbse.api.IInputService.Key;
import dk.sdu.mmmi.cbse.api.IProcessor;
import dk.sdu.mmmi.cbse.api.IWorld;
import java.util.function.Consumer;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 * @author Emil
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IProcessor.class)
})
public class PlayerProcessor implements IProcessor {

    private final IWorld world;
    private final IInputService inputService;

    public PlayerProcessor() {
        world = Lookup.getDefault().lookup(IWorld.class);
        inputService = Lookup.getDefault().lookup(IInputService.class);
    }

    @Override
    public void process(float dt) {
        world.getEntities(Player.class).forEach((Player p) -> {
            p.getMoveAbility().setMoveForward(inputService.keyDown(Key.UP));
            p.getMoveAbility().setTurnLeft(inputService.keyDown(Key.LEFT));
            p.getMoveAbility().setTurnRight(inputService.keyDown(Key.RIGHT));

            p.getCollisionAbility().getCollisions().forEach(c -> p.setHealthPoints(
                    p.getHealthPoints() - c.getCollisionAbility().getDamage()));
            if (p.getHealthPoints() <= 0) {
                world.removeEntity(p);
            }
        });
    }
}
