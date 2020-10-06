package com.minecraft.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class ShaderProgram {
	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	public ShaderProgram(String vertexFile, String fragmentFile) {
		this.vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		this.fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		this.programId = GL20.glCreateProgram();
		GL20.glAttachShader(this.programId, this.vertexShaderId);
		GL20.glAttachShader(this.programId, this.fragmentShaderId);
		this.bindAttributes();
		GL20.glLinkProgram(this.programId);
		GL20.glValidateProgram(this.programId);
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
