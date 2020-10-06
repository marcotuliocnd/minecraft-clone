package com.minecraft.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	public Camera() {}

	public void move() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.position.z -= 0.02f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.position.z += 0.02f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.position.x -= 0.02f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.position.x += 0.02f;
		}
	}
	
	public Vector3f getPosition() {
		return this.position;
	}

	public float getPitch() {
		return this.pitch;
	}

	public float getYaw() {
		return this.yaw;
	}

	public float getRoll() {
		return this.roll;
	}
	
	
	
}
