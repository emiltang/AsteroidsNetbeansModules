/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import static com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration.getDesktopDisplayMode;
import org.openide.modules.ModuleInstall;

/**
 * @author Emil
 */
public class Installer extends ModuleInstall {

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void restored() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "my-gdx-game";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;
        new LwjglApplication(new Asteroids(), cfg);
    }
}
