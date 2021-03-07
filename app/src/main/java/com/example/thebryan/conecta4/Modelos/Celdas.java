package com.example.thebryan.conecta4.Modelos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
 import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebryan.conecta4.Game;
import com.example.thebryan.conecta4.R;


public class Celdas extends FrameLayout {
    public static final int COLOR_ROJO = 1;
    public static final int COLOR_VERDE = 2;
    public static int COLOR_BLANCO = 0;
    static boolean inicio_el_game = false;
    static int celdaAnterior;
    Contenedor Contenedor;
    int ID;
    GradientDrawable shapeVerde = new GradientDrawable();
    GradientDrawable shapeVerdeBorde = new GradientDrawable();
    GradientDrawable shapeRojo = new GradientDrawable();
    GradientDrawable shapeRojoBorde = new GradientDrawable();
    int valorFila = 1;
    int columnaAleatoria;
    MediaPlayer mp;
    int Colour = COLOR_BLANCO;
    TextView txtWinner;
    Button btn = new Button(getContext());
    int ValorDiagonal;
    int DiagonalInversa;
    String ganador = "";
    private int columna;
    private int fila;

    @SuppressLint("ClickableViewAccessibility")
    public Celdas( Context context, Contenedor contenedor, int id) {
        super(context);
        Contenedor = contenedor;
        ID = id;
        this.addView(contenedor);
        mp = MediaPlayer.create(getContext(), R.raw.s2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                columnaAleatoria = (int) (Math.random() * 7)  + 1;
                int i = 0;
                while (llenaContenedorCorrespondiente(columnaAleatoria)) {
                    columnaAleatoria = (int) (Math.random() * 7) +  1;
                    i ++ ;
                }
            }
        });


        OnTouchListener touchE = new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mp.isPlaying()) {
                    mp.start();
                }
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        for (int i = 0; i < 42; i ++ ) {
                            if (Aplicacion.cell[i].getColumna() == getColumna()) {
                                Aplicacion.cell[i].cambiaBackground(Color.RED);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        for (int i = 0; i < 42; i++  ) {
                            if (Aplicacion.cell[i].getColumna() == getColumna()) {
                                Aplicacion.cell[i].setBackgroundResource(0);
                            }
                        }
                        llenaContenedorCorrespondiente(getColumna());
                        inicio_el_game = true;

                        if (Aplicacion.Modo == Aplicacion.SINGLE) {
                            btn.performClick();
                        }
                        break;
                }
                return true;
            }
        };


        this.setOnTouchListener(touchE);
        shapeVerde.setShape(GradientDrawable.RECTANGLE);
        shapeVerde.setColor(Color.GREEN);
        shapeVerde.setCornerRadius(500.0f);
        shapeVerde.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);

        shapeRojo.setShape(GradientDrawable.RECTANGLE);
        shapeRojo.setColor(Color.RED);
        shapeRojo.setCornerRadius(500.0f);
        shapeRojo.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);

        shapeRojoBorde.setShape(GradientDrawable.RECTANGLE);
        shapeRojoBorde.setColor(Color.RED);
        shapeRojoBorde.setStroke(5, Color.YELLOW);
        shapeRojoBorde.setCornerRadius(500.0f);
        shapeRojoBorde.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);

        shapeVerdeBorde.setShape(GradientDrawable.RECTANGLE);
        shapeVerdeBorde.setColor(Color.GREEN);
        shapeVerdeBorde.setStroke(5, Color.YELLOW);
        shapeVerdeBorde.setCornerRadius(500.0f);
        shapeVerdeBorde.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);

    }

    // ---------------------------------------------------------------------------------------------------------//
    private void cambiaBackground(int color) {

        this.setBackgroundColor(color);

    }

    // -------------------------------------------------------------------------------------------------------------//
    public void llenaContainer(int columnna, int fila, int Turno) {
        int idActual = 0;
        int valor_de_Fila = 0;
        int diagonal_inversa = 0;
        int diagonal = 0;

        for (int i = 0; i < Aplicacion.cell.length; ) {
            if (Aplicacion.cell[i].getColumna() == columnna && Aplicacion.cell[i].getFila() == fila) {
                idActual = i;
                diagonal = Aplicacion.cell[idActual].getValorDiagonal();
                valor_de_Fila = Aplicacion.cell[i].getValorFila();
                diagonal_inversa = Aplicacion.cell[i].getDiagonalInversa();
                if (Aplicacion.turno == COLOR_VERDE) {
                    Aplicacion.cell[i].getContenedor().setBackground(shapeVerdeBorde);
                    Aplicacion.cell[i].setColourCelda(COLOR_VERDE);
                    Aplicacion.turno = COLOR_ROJO;
                 //   Game.imageTurno.setImageResource(R.drawable.rojo);
                } else {
                    Aplicacion.cell[i].getContenedor().setBackground(shapeRojoBorde);
                    Aplicacion.cell[i].setColourCelda(COLOR_ROJO);
                    Aplicacion.turno = COLOR_VERDE;
               //     Game.imageTurno.setImageResource(R.drawable.verde);
                }
            }
            i  ++;
        }
        switch (Aplicacion.turno) {
            case COLOR_VERDE:
                if (inicio_el_game) {
                    revieteCeldaAnterior(celdaAnterior, 2);
                }
                break;
            case COLOR_ROJO:
                if (inicio_el_game) {
                    revieteCeldaAnterior(celdaAnterior, 1);
                }

        }
        celdaAnterior = idActual;


        if (fila > 3) verificaFila(Turno, valor_de_Fila);
        else {
            verificaColumna(Turno, columnna);
            verificaFila(Turno, valor_de_Fila);
            if (diagonal_inversa != 0) {
                verificaDiagonalInversa(Turno, diagonal_inversa);
            }

            if (diagonal != 0 && diagonal != 1 && diagonal != 2 && diagonal != 9 && diagonal != 10 && diagonal != 11) {
                verificaDiagonal(Turno, diagonal);
            }
        }
    }

    private void revieteCeldaAnterior(int ca, int color) {
        //        if(color==Celdas.COLOR_ROJO) {
        //            Aplicacion.cell[ca].getContenedor().setBackground(shapeRojo);
        //        }else {
        //            Aplicacion.cell[ca].getContenedor().setBackground(shapeVerde);
        //        }
    }

    // ------------------------------------------------------------------------------------------------------------------//
    // -------------------------metodos para saber si uno  gana ---------------------------------------------------------//
    private void verificaColumna(int Turno, int noColumnas) {
        int suma = 0;
        for (int i = noColumnas; i < 43; ) {
            if (Aplicacion.cell[i - 1].getColourCelda() == Turno) {
                suma ++ ;
                if (suma == 4) {
                    // 2 = verde    1 =rojo
                    if (Turno == 1) {
                        Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded2));
                        txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                        ganador = "El ganador es" +  Aplicacion.jugador1;
                        txtWinner.setText(ganador);
                        Game.miDialogo.show();
                    } else {
                        {
                            Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded3));
                            txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                            ganador = "El ganador es"  + Aplicacion.jugador2;
                            txtWinner.setText(ganador);
                            Game.miDialogo.show();
                        }
                    }

                }
            } else {
                suma = 0;
            }
            i = i  +7;
        }

    }

    private void verificaFila(int Turno, int valorfila) {
        int suma = 0;
        for (int i = valorfila; i < valorfila + 7; i++  ) {
            if (Aplicacion.cell[i - 1].getColourCelda() == Turno) {
                suma  ++;
                if (suma == 4) {
                    // 2 = verde    1 =rojo
                    if (Turno == 1) {
                        Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded2));
                        txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                        ganador = "El ganador es "  + Aplicacion.jugador1;
                        txtWinner.setText(ganador);
                        Game.miDialogo.show();
                    } else {
                        Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded3));
                        txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                        ganador = "El ganador es "   +Aplicacion.jugador2;
                        txtWinner.setText(ganador);
                        Game.miDialogo.show();
                    }
                }
            } else {
                suma = 0;
            }
        }

    }

    private void verificaDiagonal(int Turno, int valorDiagonal) {
        int suma = 0;
        for (int i = 0; i < Aplicacion.cell.length; i++  ) {

            if (Aplicacion.cell[i].getValorDiagonal() == valorDiagonal) {

                if (Aplicacion.cell[i].getColourCelda() == Turno) {
                    suma ++ ;
                    if (suma == 4) {
                        // 2 = verde    1 =rojo
                        if (Turno == 1) {
                            Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded2));
                            txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                            ganador = "El ganador es "  + Aplicacion.jugador1;
                            txtWinner.setText(ganador);
                            Game.miDialogo.show();
                        } else {
                            Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded3));
                            txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                            ganador = "El ganador es " +  Aplicacion.jugador2;
                            txtWinner.setText(ganador);
                            Game.miDialogo.show();
                        }
                    }
                } else {
                    suma = 0;
                }
            }

        }
    }

    private void verificaDiagonalInversa(int Turno, int valorDiagonalInversa) {
        int suma = 0;
        for (int i = 0; i < Aplicacion.cell.length; i++  ) {
            if (Aplicacion.cell[i].getDiagonalInversa() == valorDiagonalInversa) {
                if (Aplicacion.cell[i].getColourCelda() == Turno) {
                    suma ++ ;
                    if (suma == 4) {
                        // 2 = verde    1 =rojo
                        if (Turno == 1) {
                            Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded2));
                            txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                            ganador = "El ganador es "   +Aplicacion.jugador1;
                            txtWinner.setText(ganador);
                            Game.miDialogo.show();
                        } else {
                            Game.miDialogo.findViewById(R.id.layoutback).setBackground(getResources().getDrawable(R.drawable.borde_rounded3));
                            txtWinner = Game.miDialogo.findViewById(R.id.txtGanador);
                            ganador = "El ganador es "  + Aplicacion.jugador2;
                            txtWinner.setText(ganador);
                            Game.miDialogo.show();
                        }
                    }
                } else {
                    suma = 0;
                }
            }
        }

    }

    //-----------------------------------------------------------------------------------------------------------------//
    private boolean llenaContenedorCorrespondiente(int ColumanaClicked) {
        boolean anadida;
        if (Aplicacion.Columnas[ColumanaClicked - 1] > 0) {
            anadida = false;
            switch (ColumanaClicked) {
                case 1:
                    llenaContainer(1, Aplicacion.Columnas[0], Aplicacion.turno);
                    if (Aplicacion.Columnas[0] > 0) Aplicacion.Columnas[0]--;
                    break;
                case 2:
                    llenaContainer(2, Aplicacion.Columnas[1], Aplicacion.turno);
                    if (Aplicacion.Columnas[1] > 0) Aplicacion.Columnas[1]--;
                    break;
                case 3:
                    llenaContainer(3, Aplicacion.Columnas[2], Aplicacion.turno);
                    if (Aplicacion.Columnas[2] > 0) Aplicacion.Columnas[2]--;
                    break;
                case 4:
                    llenaContainer(4, Aplicacion.Columnas[3], Aplicacion.turno);
                    if (Aplicacion.Columnas[3] > 0) Aplicacion.Columnas[3]--;
                    break;
                case 5:
                    llenaContainer(5, Aplicacion.Columnas[4], Aplicacion.turno);
                    if (Aplicacion.Columnas[4] > 0) Aplicacion.Columnas[4]--;
                    break;
                case 6:
                    llenaContainer(6, Aplicacion.Columnas[5], Aplicacion.turno);
                    if (Aplicacion.Columnas[5] > 0) Aplicacion.Columnas[5]--;
                    break;
                case 7:
                    llenaContainer(7, Aplicacion.Columnas[6], Aplicacion.turno);
                    if (Aplicacion.Columnas[6] > 0) Aplicacion.Columnas[6]--;
                    break;
            }
        } else {
            for (int Columna : Aplicacion.Columnas) {
                int d = 0;
                if (Aplicacion.Columnas[ColumanaClicked - 1] > 0) {
                    d++;
                }
                if (d == 7) {
                    Toast.makeText(getContext(), "Tablero lleno, Reincie", Toast.LENGTH_LONG).show();
                }
            }
            anadida = true;
        }
        return anadida;
    }
    // --------------------------------------------------------------------------------------------------------------------------------//

    public void setValorDiagonalInversa(int valorDiagonalInversa) {
        this.DiagonalInversa = valorDiagonalInversa;
    }

    public void setValorDiagonal(int r, int c) {
        ValorDiagonal = r  + c;
    }

    //GETTER-------------------------------------------------------------------------------------------------------------------
    public int getColumna() {
        return columna;
    }

    // SETERS------------------------------------------------------------------------------------------------------------------------//
    public void setColumna(int columna) {
        this.columna = columna;
    }

    public com.example.thebryan.conecta4.Modelos.Contenedor getContenedor() {
        return Contenedor;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColourCelda() {
        return Colour;
    }

    private void setColourCelda(int colour) {
        this.Colour = colour;
        this.Contenedor.Color(colour);
    }

    public int getValorFila() {
        return valorFila;
    }

    public void setValorFila(int valorFila) {
        this.valorFila = valorFila;
        getContenedor().setText("F"+valorFila+"C"+getColumna());
    }

    public int getID() {
        return ID;
    }

    public int getDiagonalInversa() {
        return DiagonalInversa;
    }

    public int getValorDiagonal() {
        return ValorDiagonal;
    }
}