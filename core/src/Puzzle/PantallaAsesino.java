package Puzzle;

import Objetos.Puntuacion;
import Puzzle.Asesino.NombreAsesino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.GestorImagen;
import com.mygdx.game.TheHouseOfCrimes;
import com.mygdx.game.OrganizadorEstados;

/**
 * Esta clase representa la pantalla de selección de asesino. Esta pantalla aparece
 * justo después de obtener la última pista. El jugador tiene que escoger de entre los
 * personajes que se muestran en esta pantalla quien, basándose en las pistas que tiene, es 
 * el asesino.
 * @author Francisco Madueño Chulián
 *
 */

public class PantallaAsesino implements Screen{
	private Array<Asesino> asesinos;
	private Stage stage;
	private Texture textura;
	private Music musica;
	private int nFallos = 0;
	private NombreAsesino asesino;
	
	// Camaras
	protected OrthographicCamera camara;
	public SpriteBatch batch;
	protected FillViewport viewport; // se usa para adaptar la pantalla
	
	// Puntuacion
	protected static Puntuacion puntuacion = Puntuacion.getInstancia();
	
	/**
	 * Constructor de la clase, además de inicializar los atributos, elige quien
	 * es el asesino.
	 */
	public PantallaAsesino(){
		stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		camara = new OrthographicCamera();
		batch = new SpriteBatch();
		
		// instanciamos la camara
		camara.position.set(TheHouseOfCrimes.WIDTH / 2f, TheHouseOfCrimes.HEIGHT / 2f, 0);
		viewport = new FillViewport(TheHouseOfCrimes.WIDTH, TheHouseOfCrimes.HEIGHT, camara);
		
		Gdx.input.setInputProcessor(stage);
		
		asesinos = new Array<Asesino>();
		
		asesinos.add(new Asesino(new Texture(Gdx.files.internal("Imagenes/Personajes/chica.png")), NombreAsesino.NIÑA));
		asesinos.add(new Asesino(new Texture(Gdx.files.internal("Imagenes/Personajes/hombre.png")), NombreAsesino.HOMBRE));
		asesinos.add(new Asesino(new Texture(Gdx.files.internal("Imagenes/Personajes/joven.png")), NombreAsesino.JOVEN));
		asesinos.add(new Asesino(new Texture(Gdx.files.internal("Imagenes/Personajes/MujerMayor.png")), NombreAsesino.ANCIANA));
		asesinos.add(new Asesino(new Texture(Gdx.files.internal("Imagenes/Personajes/mujer.png")), NombreAsesino.MUJER));
		
		if(OrganizadorEstados.getAsesino().equals(NombreAsesino.NIÑA)){ //es la niña
			asesinos.get(0).setCulpable(true);
			asesino = NombreAsesino.NIÑA;
		}else if(OrganizadorEstados.getAsesino().equals(NombreAsesino.HOMBRE)){ //es el hombre
			asesinos.get(1).setCulpable(true);
			asesino = NombreAsesino.HOMBRE;
		}else if(OrganizadorEstados.getAsesino().equals(NombreAsesino.JOVEN)){ //es el joven
			asesinos.get(2).setCulpable(true);
			asesino = NombreAsesino.JOVEN;
		}else if(OrganizadorEstados.getAsesino().equals(NombreAsesino.ANCIANA)){ //es la anciana
			asesinos.get(3).setCulpable(true);
			asesino = NombreAsesino.ANCIANA;
		}else if(OrganizadorEstados.getAsesino().equals(NombreAsesino.MUJER)){ //es la mujer
			asesinos.get(4).setCulpable(true);
			asesino = NombreAsesino.MUJER;
		}
		
		textura = new Texture(Gdx.files.internal(GestorImagen.URL_PANTALLA_ASESINO));
		musica = Gdx.audio.newMusic(Gdx.files.internal("Musica/Tension.mp3"));
		
		musica.setLooping(true);
		musica.play();
		
		for(int i = 0; i < asesinos.size; ++i){
			asesinos.get(i).setTouchable(Touchable.enabled);
			stage.addActor(asesinos.get(i));
		}
	}
	
	/**
	 * Este es el bucle que se ejecutará durante todo el tiempo que permanezcamos en la
	 * pantalla. Se muestran los asesinos y la puntuación conseguida por el jugador.
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camara.update();
		batch.setProjectionMatrix(camara.combined);
		batch.begin();
		batch.draw(textura, 0, 0, TheHouseOfCrimes.WIDTH, TheHouseOfCrimes.HEIGHT);
		batch.end();
		
		Gdx.input.setInputProcessor(stage);
		
		int coordenadaAux = -60; //a partir de esta coordenada se empezarán a dibujar los personajes
		for(int i = 0; i < asesinos.size; ++i){
			asesinos.get(i).setCoordenadas(coordenadaAux, 0);
			coordenadaAux += 250; // con 250 de separación entre ellos
		}
		
		//vamos comprobando si alguno de ellos ha sido escogido (pulsado por el jugador)
		for(int i = 0; i < asesinos.size; ++i){
			asesinos.get(i).update();
		}
		
		// Posicion de la puntuacion
		stage.addActor(puntuacion);
		puntuacion.setCoordenadas(30, 750);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.setViewport(viewport);
		
	}
	
	/**
	 * Aumenta el número de fallos
	 */
	public void sumaFallo(){
		nFallos++;
	}
	
	/**
	 * Devuelve el número de fallos
	 * @return
	 */
	public int getNFallos(){
		return nFallos;
	}
	
	/**
	 * Devuelve el nombre del asesino
	 * @return
	 */
	public NombreAsesino getAsesino(){
		return asesino;
	}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {
		batch.dispose();
		stage.dispose();
		textura.dispose();
		musica.dispose();	
	}
}
