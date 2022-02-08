package helloworld;

import java.awt.RenderingHints.Key;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;

public class HelloJME3 extends SimpleApplication implements ActionListener {
	Spatial player;

	public static void main(String[] args) {
		HelloJME3 app = new HelloJME3();
		app.start(); // start the game
	}

	@Override
	public void simpleInitApp() {
		cam.setParallelProjection(true);
		cam.setLocation(new Vector3f(0, 0, 0.5f));
		getFlyByCamera().setEnabled(false);

		// setDisplayStatView(false);
		// setDisplayFps(false);

		player = getSpatial("Player");
		player.setUserData("alive", true);
		player.move(settings.getWidth() / 2, settings.getHeight() / 2, 0);
		guiNode.attachChild(player);

		// Map map = new Map(30, 30, this);

		// map.generate(55);
		// map.update();

		inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
		inputManager.addMapping("return", new KeyTrigger(KeyInput.KEY_RETURN));
		inputManager.addListener(this, "left");
		inputManager.addListener(this, "right");
		inputManager.addListener(this, "up");
		inputManager.addListener(this, "down");
		inputManager.addListener(this, "return");

		player.addControl(new PlayerControl(settings.getWidth(), settings.getHeight()));

	}

	@Override
	public void simpleUpdate(float tpf) {

	}

	@Override
	public void simpleRender(RenderManager rm) {

	}

	private Spatial getSpatial(String name) {
		Node node = new Node(name);
//        load picture
		Picture pic = new Picture(name);
		Texture2D tex = (Texture2D) assetManager.loadTexture("/" + name + ".jpg");
		pic.setTexture(assetManager, tex, true);

//        adjust picture
		float width = tex.getImage().getWidth() / 4;
		float height = tex.getImage().getHeight() / 4;
		pic.setWidth(width);
		pic.setHeight(height);
		pic.move(-width / 2f, -height / 2f, 0);

//        add a material to the picture
		Material picMat = new Material(assetManager, "Common/MatDefs/Gui/Gui.j3md");
		picMat.getAdditionalRenderState().setBlendMode(BlendMode.AlphaAdditive);
		node.setMaterial(picMat);

//        set the radius of the spatial
//        (using width only as a simple approximation)
		node.setUserData("radius", width / 2);

//        attach the picture to the node and return it
		node.attachChild(pic);
		return node;
	}

	@Override
	public void onAction(String name, boolean isPressed, float tpf) {
		if ((Boolean) player.getUserData("alive")) {
			if (name.contentEquals("up")) {
				player.getControl(PlayerControl.class).up = isPressed;
			} else if (name.contentEquals("down")) {
				player.getControl(PlayerControl.class).down = isPressed;
			} else if (name.contentEquals("left")) {
				player.getControl(PlayerControl.class).left = isPressed;
			} else if (name.contentEquals("right")) {
				player.getControl(PlayerControl.class).right = isPressed;
			}
		}

	}
}
