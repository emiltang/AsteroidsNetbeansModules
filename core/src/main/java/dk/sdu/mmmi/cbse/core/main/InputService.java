/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package dk.sdu.mmmi.cbse.core.main;

import static com.badlogic.gdx.Gdx.input;
import static com.badlogic.gdx.Input.Keys;
import dk.sdu.mmmi.cbse.api.IInputService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IInputService.class)
})
public class InputService implements IInputService {

    @Override
    public boolean keyDown(Key key) {
        switch (key) {
            case RIGHT:
                return input.isKeyPressed(Keys.RIGHT);
            case LEFT:
                return input.isKeyPressed(Keys.LEFT);
            case UP:
                return input.isKeyPressed(Keys.UP);
            default:
                return false;
        }
    }
}
