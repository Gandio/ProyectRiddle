package com.mygdx.game;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import Items.Objeto;
import Pantallas.Habitacion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class Estado {
	private String habitacionInicio;
	private String habitacionDestino;
	private String objeto;
	private Objeto item;
	private String personaje;
	private String textoPersonaje;
	private String pistaPersonaje;
	private String objetivo;
	
	protected XmlReader reader = new XmlReader();
	protected Element raiz;
	protected Array<Element> estados;
	
	private boolean puzzleSuperado = false;
	private boolean sePermiteCogerObjeto = false;
	private boolean objetoConseguido = false;
	private boolean misionEnCurso = false;
	
	public Estado(int numEstado){
		
		try{
			if(MyGdxGame.SUSPENSE_AMBIENTE)
				raiz = reader.parse(Gdx.files.internal("xml/logicaAlemanSuspense.xml"));
			else
				raiz = reader.parse(Gdx.files.internal("xml/logicaAlemanSinSuspense.xml"));
		}catch(IOException e){}
		
		estados = raiz.getChildrenByName("estado");
		
		//Cogemos el elemento estado actual
		Element child = raiz.getChild(numEstado);
		
		//Dentro del estado actual seleccionamos, al azar el puzzle que se va a ejecutar
		
		Random rm = new Random();
		int idPuzzle = rm.nextInt();
		Element puzzle = child.getChild(0); //Ahora es 0, se cambiará por idPuzzle más adelante
		
		habitacionInicio = puzzle.getChildByName("habitacion").getAttribute("tipoHabitacionInicio");
		habitacionDestino = puzzle.getChildByName("habitacion").getAttribute("tipoHabitacionFinal");
		objeto = puzzle.getChildByName("objeto").getAttribute("tipoObjeto");
		personaje = puzzle.getChildByName("personaje").getAttribute("tipoPersonaje");
		textoPersonaje = puzzle.getChildByName("dialogo").getAttribute("texto");
		pistaPersonaje = puzzle.getChildByName("pista").getAttribute("texto");
		objetivo = puzzle.getChildByName("objetivo").getAttribute("texto");
	}

	public String getHabitacionInicio() {
		return habitacionInicio;
	}

	public String getHabitacionDestino() {
		return habitacionDestino;
	}

	public String getObjeto() {
		return objeto;
	}

	public String getPersonaje() {
		return personaje;
	}

	public String getTextoPersonaje() {
		return textoPersonaje;
	}

	public String getPistaPersonaje() {
		return pistaPersonaje;
	}

	public String getObjetivo() {
		return objetivo;
	}
	
	public boolean estadoPuzzle(){
		return puzzleSuperado;
	}
	
	public boolean estadoMision(){
		return misionEnCurso;
	}
	
	public boolean objetoConseguido(){
		return objetoConseguido;
	}
	
	public void seSuperaPuzzle(boolean b){
		puzzleSuperado = b;
	}
	
	public void seIniciaMision(boolean b){
		misionEnCurso = b;
		
	}
	
	public void seCogeObjeto(boolean b){
		objetoConseguido = b;
	}
	
	public void setItem(Objeto o){
		item = o;
	}
	
	public Objeto getItem(){
		return item;
	}
	
	public void permitirCogerObjeto(Habitacion h, String objeto){
		if(!sePermiteCogerObjeto){
			Array<Objeto> aux = h.getObjetos();
			Iterator<Objeto> iter = aux.iterator();
			Objeto objetoAux = null;
			Objeto o = null;
		
			while(iter.hasNext()){
				objetoAux = iter.next();
				if(objetoAux.getIdentificador().toString().equals(objeto)){
					o = objetoAux;
					o.seCoge(true);
				}
			}
		
			item = o;
			sePermiteCogerObjeto = true;
		}
	}
}
