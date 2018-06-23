package com.example.thebryan.conecta4.Modelos;

import android.content.Intent;

public class Aplicacion {
    public static final int MULTI = 2;
    public static String jugador1 = "";
    public static String jugador2 = "";
    public static Celdas[] cell = new Celdas[42];
    public static int Columnas[] = {6, 6, 6, 6, 6, 6, 6};
    public static int Modo;
    public static int SINGLE = 1;
    public static int turno = 1;
    public static Intent GameIntent;
    private static int Ancho = 1;

    public Aplicacion() {

    }

    public static int getAncho() {
        return Ancho;
    }


    public static void guarda_tamano_pantalla(int ancho) {
        Ancho = ancho;
    }
}
