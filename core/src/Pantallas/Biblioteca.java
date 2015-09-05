package Pantallas;

import java.util.Iterator;

import Items.Calavera;
import Items.Objeto;
import Items.Serpiente;
import Objetos.Cursor;
import Personajes.Mujer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

/**
 * Clase que representa al objeto Biblioteca
 * @author Francisco Madueño Chulián
 */

public final class Biblioteca extends Habitacion {
	
	private static MyGdxGame game;
	private static Biblioteca unicaInstancia;
	
	private static Objeto serpiente = new Serpiente(game);
	private static Objeto calavera = new Calavera(game);
	
	/**
	 * Contructor de la clase Biblioteca
	 * @param game
	 * @param c
	 */
	
	private Biblioteca(MyGdxGame game, Cursor c) {
		super(Pasillo.game, c);
		objetos = new Array<Objeto>();
		
		//Objetos
		Iterator<Objeto> iter = objetos.iterator();
		
		//Añadimos objetos a la habitacion
		objetos.add(calavera);
		objetos.add(serpiente);
		
		calavera.setCoordenadas(530, 220);
		serpiente.setCoordenadas(10, 50);
		
		while(iter.hasNext()){
			iter.next().setTouchable(Touchable.enabled);
		}
		
		iter = objetos.iterator();
		
		while(iter.hasNext()){
			stage.addActor(iter.next());
		}
		
		if(MyGdxGame.SUSPENSE_AMBIENTE){
			//Actores
			personaje = Mujer.getInstancia();
			personaje.setCoordenadas(450, 0);
		
			//añadimos los actores
			stage.addActor(personaje);
		}
	}
	
	/**
	 * Este método se ejecuta durante todo el tiempo que el jugador esté en la bilbioteca
	 */
	public void render(float delta) {
		super.render(delta);
		
		Iterator<Objeto> iterObjetos = objetos.iterator();
		while(iterObjetos.hasNext()){
			iterObjetos.next().seSelecciona();
		}
		
		Gdx.input.setInputProcessor(stage);
		stage.draw();
	}
	
	/**
	 * Muestra la textura de la biblioteca
	 */
	public void show() {
		pantalla = new Texture(Gdx.files.internal("Imagenes/Escenarios/Biblioteca.png"));
		
	}

	public void pause() {}

	public void resume() {}
	
	/**
	 * Para que solo haya un único objeto en el juego se debe hacer que el contructor sea privado.
	 * Si la habitación está creada no hace nada, si no se llama al contructor.
	 * @return unicaInstancia
	 */
	public static Biblioteca getInstancia(){
		if(unicaInstancia == null){
			unicaInstancia = new Biblioteca(game, c);
		}
		
		return unicaInstancia;
	}
}