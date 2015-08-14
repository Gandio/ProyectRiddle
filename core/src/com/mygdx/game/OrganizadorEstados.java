package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.io.IOException;
import java.lang.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import Items.Objeto;
import Puzzle.Inventario;
import Pantallas.Atico;
import Pantallas.Baño;
import Pantallas.Biblioteca;
import Pantallas.Cocina;
import Pantallas.Dormitorio;
import Pantallas.Estudio;
import Pantallas.Habitacion;
import Pantallas.Inicio;
import Pantallas.Salon;
import Pantallas.Sotano;
import Pantallas.Habitacion.EstadoHabitacion;

public class OrganizadorEstados {
	private static MyGdxGame game = Inicio.game;
	private static ArrayList<Estado> estados;
	private static OrganizadorEstados unicaInstancia;
	private static int estadoActual = 0;
	private static int nEstados = 5; //numero total de estados del juego
	private static Estado e;
	
	protected XmlReader reader = new XmlReader();
	protected Element raiz;
	protected Array<Element> pistasAsesino;
	protected Array<Element> pistasArma;
	protected Array<String> pistas;
	
	private OrganizadorEstados(MyGdxGame game){
		//this.game = game;
		estados = new ArrayList<Estado>(4);
		pistas = new Array<String>(nEstados);
		
		//Vamos a seleccionar al asesino y almacenar las pistas para descubrirlo
		
		Random rm = new Random();
		/*
		 * 0 - chica
		 * 1 - hombre
		 * 2 - joven
		 * 3 - anciana
		 * 4 - mujer
		 */
		int asesino = rm.nextInt((4 - 0) + 1) + 0;
		
		try{
			if(asesino == 0)
				raiz = reader.parse(Gdx.files.internal("xml/pistasChica.xml"));
			else if(asesino == 1)
				raiz = reader.parse(Gdx.files.internal("xml/pistasHombre.xml"));
			else if(asesino == 2)
				raiz = reader.parse(Gdx.files.internal("xml/pistasJoven.xml"));
			else if(asesino == 3)
				raiz = reader.parse(Gdx.files.internal("xml/pistasMujerMayor.xml"));
			else
				raiz = reader.parse(Gdx.files.internal("xml/pistasMujer.xml"));
				
		}catch(IOException e){}
		
		pistasAsesino = raiz.getChildrenByName("pista");
		
		for(int i = 0; i < pistasAsesino.size; ++i){
			pistas.add(raiz.getChild(i).getAttribute("texto"));
		}
		
		//Vamos a escoger el arma y las pistas para descubrirla
		
		int arma = rm.nextInt((3 - 0) + 1) + 0;
		
		/*
		 * 0 - daga
		 * 1 - pistola
		 * 2 - rifle
		 * 3 - serpiente
		 */
		
		try{
			if(arma == 0)
				raiz = reader.parse(Gdx.files.internal("xml/dagaPistas.xml"));
			else if(arma == 1)
				raiz = reader.parse(Gdx.files.internal("xml/pistolaPistas.xml"));
			else if(arma == 2)
				raiz = reader.parse(Gdx.files.internal("xml/riflePistas.xml"));
			else
				raiz = reader.parse(Gdx.files.internal("xml/serpientePistas.xml"));
				
		}catch(IOException e){}
		
		pistasArma= raiz.getChildrenByName("pista");
		
		for(int i = 0; i < pistasArma.size; ++i){
			pistas.add(raiz.getChild(i).getAttribute("texto"));
		}
		
		System.out.println("Asesino " + asesino);
		System.out.println("Arma " + arma);
		System.out.println(pistas.size);
		
		//llenamos el array de estados aleatorios y le pasamos una pista al azar
		//String siguienteHabitacion = null;
		for(int i = 1; i <= nEstados; ++i){
			int j = rm.nextInt((pistas.size - 0)) + 0;
			
			estados.add(new Estado(i, pistas.get(j)));
			/*
			System.out.println(i);
			
			if(i > 1){
				siguienteHabitacion = estados.get(i-1).getHabitacionInicio();
				estados.get(i-2).crearPista(siguienteHabitacion);
			}
			*/
			pistas.removeIndex(j);
			
		}
	}
	
	public void actualizarEstado(){
		System.out.println("estoy en el estado " + estadoActual);
		e = estados.get(estadoActual);
		if(estadoActual == 0 || estadoActual == 2){
			Habitacion habitacionInicio = conversorStringHabitacion(estados.get(estadoActual).getHabitacionInicio());
			
			//Actualizamos el objetivo
			Inventario.getCuadroEstado().setTexto(e.getObjetivo());
			
			//Se termina de inicializar los cuadros eleccion
			for(int i = 0; i < 4; ++i){ //Siempre hay 4 cuadros de eleccion, lo valores no cambian
				habitacionInicio.getCuadrosEleccion().get(i).setTexto(e.getTextoEleccion(i));
				if(e.getEleccion(i).equals("si"))
					habitacionInicio.getCuadrosEleccion().get(i).setEleccion(1);
				else
					habitacionInicio.getCuadrosEleccion().get(i).setEleccion(0);
			}
			
			if(e.estadoMision()){
				//Aparecería un cuadro de texto, diciendo "Ya lo sabes?"
				if(e.getEleccionCorrecta() == -1){
					habitacionInicio.getCuadroDialogo().setTexto("¿Ya lo sabes?");
				}
				
				//seguidamente aparecerían los 4 cuadros eleccion, esto pasa cuando se pulsa el botón de final de conversacion
				//si pulsas uno incorrecto aparecería un cuadro de texto "vuelve a intentarlo" y se terminaria la secuencia
				else if(e.getEleccionCorrecta() == 0){
					habitacionInicio.getCuadroDialogo().setTexto("Vuelve a intentarlo");
				}else{ //si pulsas el correcto aparece el cuadro de texto con la pista y continua el juego
					habitacionInicio.getCuadroDialogo().setTexto(e.getPistaPersonaje());
				}
			}else{
				habitacionInicio.getCuadroDialogo().setTexto(e.getTextoPersonaje());
			}
			
			
		}else if(estadoActual == 1 || estadoActual == 4){
			Habitacion habitacionInicio = conversorStringHabitacion(estados.get(estadoActual).getHabitacionInicio());
			Habitacion habitacionDestino = conversorStringHabitacion(estados.get(estadoActual).getHabitacionDestino());
			String objeto = estados.get(estadoActual).getObjeto();
			String personaje = estados.get(estadoActual).getPersonaje();
		
			//Actualizamos el objetivo
			Inventario.getCuadroEstado().setTexto(e.getObjetivo());
		
			//Ya has aceptado la misión, no se vuelve a repetir la conversación
			if(e.estadoMision()){
				habitacionInicio.getCuadroDialogo().setTexto("Was ist los? Zackibacki! Geh!!!!");
			
				//Se habilita el objeto para que se pueda coger
				e.permitirCogerObjeto(habitacionDestino, objeto);
			}else{
				habitacionInicio.getCuadroDialogo().setTexto(e.getTextoPersonaje());
			}
		
			if(inventarioContieneObjeto(e.getItem())){
				habitacionInicio.getCuadroDialogo().setTexto(e.getPistaPersonaje());
			}
		}else if(estadoActual == 3){
			Habitacion habitacionInicio = conversorStringHabitacion(estados.get(estadoActual).getHabitacionInicio());
			Habitacion habitacionDestino1 = conversorStringHabitacion(estados.get(estadoActual).getHabitacionDestino());
			Habitacion habitacionDestino2 = conversorStringHabitacion(estados.get(estadoActual).getHabitacionDestino());
			String objeto = estados.get(estadoActual).getObjeto(); //este es el objeto final
			String objetoCombinacion1 = estados.get(estadoActual).getObjetoCombinacion1();
			String objetoCombinacion2 = estados.get(estadoActual).getObjetoCombinacion2();
			String personaje = estados.get(estadoActual).getPersonaje();
			Inventario.getCuadroEstado().setTexto(e.getObjetivo());
			
			//Actualizamos el objetivo
			Inventario.getCuadroEstado().setTexto(e.getObjetivo());
		
			//Ya has aceptado la misión, no se vuelve a repetir la conversación
			if(e.estadoMision()){
				habitacionInicio.getCuadroDialogo().setTexto("Was ist los? Zackibacki! Geh!!!!");
			
				//Se habilita el objeto para que se pueda coger
				e.permitirCogerObjeto(habitacionDestino1, objetoCombinacion1);
				e.permitirCogerObjeto(habitacionDestino2, objetoCombinacion2);
			}else{
				habitacionInicio.getCuadroDialogo().setTexto(e.getTextoPersonaje());
			}
		
			if(inventarioContieneObjeto(e.getItem())){
				habitacionInicio.getCuadroDialogo().setTexto(e.getPistaPersonaje());
			}			
			
		}
		//Si se supera el puzzle, pasamos al nuevo estado
		if(e.estadoPuzzle()){
			estadoActual++;
		}
	}
	
	public static OrganizadorEstados getInstancia(){
		if(unicaInstancia == null)
			unicaInstancia = new OrganizadorEstados(game);
		
		return unicaInstancia; 
	}
	
	public static Estado getEstadoActual(){
		return e;
	}
	
	private Habitacion conversorStringHabitacion(String s){
		if(s.equals("Atico"))
			return Atico.getInstancia();
		else if(s.equals("Baño"))
			return Baño.getInstancia();
		else if(s.equals("Biblioteca"))
			return Biblioteca.getInstancia();
		else if(s.equals("Cocina"))
			return Cocina.getInstancia();
		else if(s.equals("Dormitorio"))
			return Dormitorio.getInstancia();
		else if(s.equals("Estudio"))
			return Estudio.getInstancia();
		else if(s.equals("Salon"))
			return Salon.getInstancia();
		else if(s.equals("Sotano"))
			return Sotano.getInstancia();
		else
			return null;
	}
	
	private boolean inventarioContieneObjeto(Objeto o){
		System.out.println(e.getItem());
		if(Inventario.getContenido().contains(e.getItem(), false)){ 
			e.seCogeObjeto(true);
			return true;
		}else{
			return false;
		}
	}
}
