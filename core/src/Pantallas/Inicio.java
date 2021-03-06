package Pantallas;

import java.io.IOException;

import Botones.BotonCreditos;
import Botones.BotonInicio;
import Botones.BotonSalir;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.GestorImagen;
import com.mygdx.game.TheHouseOfCrimes;

/**
 * Esta clase representa la pantalla de título del juego.
 * @author Francisco Madueño Chulián
 *
 */

public class Inicio implements Screen{
	//Juego
	public static TheHouseOfCrimes game;
	protected Stage stage;
	protected Music musica;
	protected Texture pantalla;
	
	//Camaras
	protected OrthographicCamera camara;
	public SpriteBatch batch;
	protected FillViewport viewport; //se usa para adaptar la pantalla
	
	//Botón que permite iniciar una nueva partida
	private BotonInicio inicio;
	//Boton que permite salir del juego
	private BotonSalir salir;
	//Boton que permite ver los créditos del juego
	private BotonCreditos creditos;
	
	/**
	 * Constructor de la clase Inicio.
	 * @param game
	 */
	
	public Inicio(TheHouseOfCrimes game){
		Inicio.game = game;
		
		stage = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		camara = new OrthographicCamera();
		batch = new SpriteBatch();
		
		//musica
		/*if(MyGdxGame.SUSPENSE_MUSICA)
			musica = Gdx.audio.newMusic(Gdx.files.internal("Musica/titulo.mp3"));
		else*/
		
		musica = Gdx.audio.newMusic(Gdx.files.internal("Musica/TituloSinSuspense.mp3"));
		musica.play();
		
		//instanciamos la cámara
		camara.position.set(TheHouseOfCrimes.WIDTH / 2f, TheHouseOfCrimes.HEIGHT / 2f ,0);
		viewport = new FillViewport(TheHouseOfCrimes.WIDTH, TheHouseOfCrimes.HEIGHT, camara);
		
		//añadimos botones y hacemos que sean tocables
		inicio = new BotonInicio(game);
		inicio.setTouchable(Touchable.enabled);
		
		salir = new BotonSalir(game, false);
		salir.setTouchable(Touchable.enabled);
		
		creditos = new BotonCreditos(game);
		creditos.setTouchable(Touchable.enabled);
		
		Gdx.input.setInputProcessor(stage);
		
		//se añaden los botones
		stage.addActor(inicio);
		stage.addActor(salir);
		stage.addActor(creditos);
	}
	
	/**
	 * Muestra la textura de la pantalla.
	 */

	public void show(){
		//Dependiendo de la variable cargamos una pantalla u otra
		if(TheHouseOfCrimes.SUSPENSE_AMBIENTE)
			pantalla = new Texture(Gdx.files.internal(GestorImagen.URL_PANTALLA_TITULO_SUSPENSE));
		else
			pantalla = new Texture(Gdx.files.internal(GestorImagen.URL_PANTALLA_TITULO));
	}
	
	/**
	 * Este método se ejecuta constantemente, dibuja la pantalla con todos sus actores y comprueba
	 * si alguno de los botones ha sido pulsado.
	 */
	
	public void render(float delta){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camara.update();
		batch.setProjectionMatrix(camara.combined);
		
		batch.begin();
		batch.draw(pantalla, 0, 0, TheHouseOfCrimes.WIDTH, TheHouseOfCrimes.HEIGHT);
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		//Estamos constantemente comprobando el estado de los botones
		try {
			inicio.esPulsado();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		salir.esPulsado();
		creditos.update();
		
		/*
		 * La situación de los botones también dependen de esta variable
		 */
		if(TheHouseOfCrimes.SUSPENSE_AMBIENTE){
			inicio.setCoordenadas(500, 170);
			salir.setCoordenadas(540, 65);
		}else{
			inicio.setCoordenadas(430, 170);
			salir.setCoordenadas(500, 65);
		}
		
		creditos.setCoordenadas(50, 50);
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.setViewport(viewport);
	}

	public void dispose() {
		batch.dispose();
		stage.dispose();
		pantalla.dispose();
		musica.dispose();
	}

	public void hide() {}

	public void pause() {}

	public void resume() {}
}