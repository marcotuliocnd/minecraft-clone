package com.minecraft.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private boolean isHolding = false;
	private int framesHolding = 1;
	
	private float speed = 0.07195f;
	private float framesUntilMaxSpeed = 15;
	
	public Camera() {}

	public void move() {
		
		this.isHolding = false;
		float currSpeed = this.speed * this.framesHolding / this.framesUntilMaxSpeed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.isHolding = true;
			this.position.z -= currSpeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.isHolding = true;
			this.position.z += currSpeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.isHolding = true;
			this.position.x -= currSpeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.isHolding = true;
			this.position.x += currSpeed;
		} 
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			this.yaw -= 1;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			this.yaw += 1;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			this.pitch += 1;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			this.pitch -= 1;
		}
		
		if(this.isHolding) {
			if(this.framesHolding < this.framesUntilMaxSpeed)
				this.framesHolding += 1;
		} else {
			this.framesHolding = 1;
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
