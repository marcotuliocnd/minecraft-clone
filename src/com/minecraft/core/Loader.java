package com.minecraft.core;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();

	public RawModel loadToVao(float[] positions) {
		int vaoId = createVAO();
		
		storeDataInAttributeList(0, positions);
		unbindVAO();
		
		return new RawModel(vaoId, positions.length / 3);
	}
	
	private int createVAO() {
		int vaoId = GL30.glGenVertexArrays();
		vaos.add(vaoId);
		GL30.glBindVertexArray(vaoId);
		return vaoId;
	}
	
	public void cleanUp() {
		for (int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		FloatBuffer floatBuffer = this.storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
		floatBuffer.put(data);
		floatBuffer.flip();

		return floatBuffer;
	};
}
