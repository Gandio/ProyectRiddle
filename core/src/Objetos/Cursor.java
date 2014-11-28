package Objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MyGdxGame;

/**
 * Esta clase representa la situación al protagonista mientras se mueve por los pasillos de 
 * la casa.
 * @author Francisco Madueño Chulián
 */
public class Cursor extends Actor{
	private MyGdxGame game;
	private Texture cursor;
	private TextureRegion[] arriba, abajo, derecha, izquierda;
	private TextureRegion[][] tmp;
	private Animation moverArriba, moverAbajo, moverDerecha, moverIzquierda;
	private TextureRegion frameActual;
	private float stateTime;
	private Vector2 velocity;
	private Rectangle limites;
	
	/**
	 * Recibe un objeto MyGdxGame que hereda de la clase Game
	 * @param game
	 */
	public Cursor(MyGdxGame game) {
		this.game = game;
		
		//Creamos el grafico del cursor
		cursor = new Texture(Gdx.files.internal("Imagenes/personaje.png"));
		tmp = TextureRegion.split(cursor, cursor.getWidth() / 4, cursor.getHeight());
		arriba = new TextureRegion[3];
		arriba[0] = arriba[1] = arriba[2] = tmp[0][0];
		abajo = new TextureRegion[3];
		abajo[0] = abajo[1] = abajo[2] = tmp[0][2];
		derecha = new TextureRegion[3];
		derecha[0] = derecha[1] = derecha[2] = tmp[0][3];
		izquierda = new TextureRegion[3];
		izquierda[0] = izquierda[1] = izquierda[2] = tmp[0][1];
		
		//Al principio del juego el personaje mirará hacia arriba y se situará en la entrada
		frameActual = new TextureRegion(arriba[0]);
		
		stateTime = 0f;
		setPosition(500, 300);
		setWidth(cursor.getWidth() / 4);
		setHeight(cursor.getHeight());
		
		//Creamos la animación
		moverArriba = new Animation(0.3f, arriba);
		moverAbajo = new Animation(0.3f, abajo);
		moverIzquierda = new Animation(0.3f, izquierda);
		moverDerecha = new Animation(0.3f, derecha);
		
		velocity = new Vector2(0, 0);
		
		//Creamos un rectangulo que envuelva al personaje, nos ayudará con las colisiones
		limites = new Rectangle();
		limites.setSize(cursor.getWidth() / 4, cursor.getHeight());
	}
	
	/**
	 * Este método dibujará al actor
	 */
	public void draw(Batch batch, float parentAlpha){  
		batch.draw(frameActual, getX(), getY());
	}
	
	/**
	 * Usamos este método para modificar el stateTime del cursor
	 * @param time
	 */
	public void setStateTime(float time){
		stateTime += time;
	}
	
	/**
	 * Este método devuelve el stateTime del cursor
	 * @return stateTime
	 */
	public float getStateTime(){
		return stateTime;
	}
	
	/**
	 * Este método devuelve la dirección en la que se mueve el cursor
	 * @return velocity
	 */
	public Vector2 getVelocity(){
		return velocity;
	}
	
	/**
	 * Modifica la dirección X del cursor
	 * @param x
	 */
	public void setVelocityX(float x){
		velocity.x = x;
	}
	
	/**
	 * Modifica la dirección Y del cursor
	 * @param y
	 */
	public void setVelocityY(float y){
		velocity.y = y;
	}
	
	/**
	 * Cambia el frame del cursor, hace que mire hacia arriba
	 */
	public void MirarArriba(){
		frameActual = moverArriba.getKeyFrame(stateTime,true);
	}
	
	/**
	 * Cambia el frame del cursor, hace que mire hacia abajo
	 */
	public void MirarAbajo(){
		frameActual = moverAbajo.getKeyFrame(stateTime,true);
	}
	
	/**
	 * Cambia el frame del cursor, hace que mire hacia la derecha
	 */
	public void MirarDerecha(){
		frameActual = moverDerecha.getKeyFrame(stateTime,true);
	}
	
	/**
	 * Cambia el frame del cursor, hace que mire hacia la izquierda
	 */
	public void MirarIzquierda(){
		frameActual = moverIzquierda.getKeyFrame(stateTime,true);
	}
	
	/**
	 * Devuelve el rectangulo que envuelve al cursor
	 * @return limites
	 */
	public Rectangle getLimites(){
		return limites;
	}
}