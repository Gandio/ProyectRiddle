package Personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GestorImagen;
import com.mygdx.game.TheHouseOfCrimes;
import com.mygdx.game.Tools;

/**
 * Esta clase representa a los objetos Hombre
 * @author Francisco Madueño Chulián
 */
public class Hombre extends Personaje{
	
	private static TheHouseOfCrimes game;
	private static Hombre unicaInstancia;
	
	/**
	 * Contructor de la clase
	 * @param game
	 */
	private Hombre(TheHouseOfCrimes game) {
		super(game);
		Hombre.game = game;
		
		if(TheHouseOfCrimes.SUSPENSE_AMBIENTE)
			personaje = new Texture(Gdx.files.internal(GestorImagen.URL_HOMBRE_SUSPENSE));
		else
			personaje = new Texture(Gdx.files.internal(GestorImagen.URL_HOMBRE));
		
		coordenadas = new Vector2(Tools.centrarAncho(game, personaje), Tools.centrarAlto(game, personaje));
	}
	
	/**
	 * Solo habrá un objeto Hombre durante cada partida, este método se encarga de ello.
	 * @return
	 */
	public static Hombre getInstancia(){
		if(unicaInstancia == null)
			unicaInstancia = new Hombre(game);
		
		return unicaInstancia;
	}
}