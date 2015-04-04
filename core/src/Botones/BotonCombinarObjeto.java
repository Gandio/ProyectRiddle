package Botones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools;

public class BotonCombinarObjeto extends Boton{

	public BotonCombinarObjeto(MyGdxGame game) {
		super(game);
		boton = new Texture(Gdx.files.internal("Imagenes/botonCombinar.png"));
		coordenadas = new Vector2(Tools.centrarAncho(game, boton), Tools.centrarAlto(game, boton));
	}
	
	public void update(){
		setBounds(coordenadas.x, coordenadas.y, boton.getWidth(), boton.getHeight());
		
		addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((BotonCombinarObjeto)event.getTarget()).pulsado = true;
                
                return true;
            }
		});
		
		/*if(pulsado && !((Inventario) game.getScreen()).getCombinar()){
			((Inventario) game.getScreen()).setCombinar(true);
		}*/
		
		pulsado = false;
	}

}