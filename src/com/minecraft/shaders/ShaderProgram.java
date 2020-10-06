package com.minecraft.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16); // 4x4 matrix
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		this.vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		this.fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		this.programId = GL20.glCreateProgram();
		GL20.glAttachShader(this.programId, this.vertexShaderId);
		GL20.glAttachShader(this.programId, this.fragmentShaderId);
		
		this.bindAttributes();
		
		GL20.glLinkProgram(this.programId);
		GL20.glValidateProgram(this.programId);
		
		this.getAllUniformLocations();
	}
	
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(this.programId, uniformName);
	}
	
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadBoolean(int location, boolean value) {
		float load = (value) ? 1 : 0;
		GL20.glUniform1f(location, load);
	}
	
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	public void start() {
		GL20.glUseProgram(this.programId);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanUp() {
		this.stop();
		
		GL20.glDetachShader(this.programId, this.vertexShaderId);
		GL20.glDetachShader(this.programId, this.fragmentShaderId);
		GL20.glDeleteShader(this.fragmentShaderId);
		GL20.glDeleteShader(this.vertexShaderId);
		
		GL20.glDeleteProgram(this.programId);
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(this.programId, attribute, variableName);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try{
		  BufferedReader reader = new BufferedReader(new FileReader(file));
		  String line;
		  while((line = reader.readLine()) != null) {
		    shaderSource.append(line).append("//\n");
		  }
		  reader.close();
		} catch(IOException e) {
		  e.printStackTrace();
		  System.exit(-1);
		}

		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);

		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS ) == GL11.GL_FALSE){
		  System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
		  System.err.println("Could not compile shader!");
		  System.exit(-1);
		}
		return shaderID;
	 }
}
