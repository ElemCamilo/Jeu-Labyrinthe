package helloworld;

import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {
	private int screenWidth, screenHeight;
	public boolean up, down, left, right;
	private float speed = 200f;
	private float lastRotation;

	public PlayerControl(int width, int height) {
		this.screenHeight = height;
		this.screenWidth = width;
	}

	@Override
	protected void controlUpdate(float tpf) {
		if (up) {
			if (spatial.getLocalTranslation().y < screenHeight - (Float) spatial.getUserData("radius")) {
				spatial.move(0, tpf * speed, 0);
			}
			spatial.rotate(0, 0, -lastRotation);
			lastRotation = 0;
		}
		if (down) {
			if (spatial.getLocalTranslation().y > (Float) spatial.getUserData("radius")) {
				spatial.move(0, tpf * -speed, 0);
			}
			spatial.rotate(0, 0, -lastRotation + FastMath.PI);
			lastRotation = FastMath.PI;
		}
		if (left) {
			if (spatial.getLocalTranslation().x > (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * -speed, 0, 0);
			}
			spatial.rotate(0, 0, -lastRotation + FastMath.PI / 2);
			lastRotation = FastMath.PI / 2;
		}
		if (right) {
			if (spatial.getLocalTranslation().x < screenWidth - (Float) spatial.getUserData("radius")) {
				spatial.move(tpf * speed, 0, 0);
			}
			spatial.rotate(0, 0, -lastRotation + FastMath.PI * 1.5f);
			lastRotation = FastMath.PI * 1.5f;
		}

	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		up = false;
		down = false;
		left = false;
		right = false;
	}

}
