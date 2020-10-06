package com.minecraft.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.minecraft.models.RawModel;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	public RawModel loadToVao(float[] positions, int[] indices, float[] textureCoordinates) {
		int vaoId = createVAO();
		
		this.bindIndicesBuffer(indices);
		this.storeDataInAttributeList(0, positions, 3);
		this.storeDataInAttributeList(1, textureCoordinates, 2);
		this.unbindVAO();
		
		return new RawModel(vaoId, indices.length);
	}

	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+ fileName));
		} catch (FileNotFoundException e) {
			System.out.println("teste");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textureId = texture.getTextureID();
		this.textures.add(textureId);
		return textureId;
	}
		
	
	private int createVAO() {
		int vaoId = GL30.glGenVertexArrays();
		vaos.add(vaoId);
		GL30.glBindVertexArray(vaoId);
		return vaoId;
	}
	
	public void cleanUp() {
		for (int vao:this.vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for (int vbo:this.vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture:this.textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	private void storeDataInAttributeList(int attributeNumber, float[] data, int dataSize) {
		int vboId = GL15.glGenBuffers();
		vbos.add(vboId);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		FloatBuffer floatBuffer = this.storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = this.storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
		floatBuffer.put(data);
		floatBuffer.flip();

		return floatBuffer;
	};
}
