package com.example.thebryan.conecta4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebryan.conecta4.Modelos.Aplicacion;
import com.example.thebryan.conecta4.Modelos.Celdas;
import com.example.thebryan.conecta4.Modelos.Contenedor;

public class Game extends AppCompatActivity {
    public static Dialog miDialogo;
    public static Dialog miDialogoPausa;
    public static ImageView imageTurno;
    public TextView tv;
    Button cerrarDiag;
    Button cerrarDiagPausa;
    Button Salir;
    Button volverMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        saveDisplaySize();
        tv = findViewById(R.id.txtNombreRojo);

        tv.setText(Aplicacion.jugador1);
        TextView tv1 = findViewById(R.id.txtNombreVerde);
        tv1.setText(Aplicacion.jugador2);
        imageTurno = findViewById(R.id.imgTurno);
        if (Aplicacion.turno == 1) {
            imageTurno.setImageResource(R.drawable.rojo);
        } else {
            imageTurno.setImageResource(R.drawable.verde);
        }

        // evento reiniciar //
        findViewById(R.id.btnRestart).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                for (int i = 0; i < Aplicacion.Columnas.length; i++  ) {
                    Aplicacion.Columnas[i] = 6;
                }
                finish();
                Aplicacion.GameIntent = new Intent(Game.this, Game.class);
                startActivity(Aplicacion.GameIntent);
                return false;
            }
        });
        // Evento pausar//
        findViewById(R.id.btnPause).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(getBaseContext(), "Aqui1", Toast.LENGTH_LONG).show();
                miDialogoPausa.show();
                return false;
            }
        });
        creaDialogo();

        // btn Automatico //

        GridLayout grid = findViewById(R.id.myGrid);
        int padding = (Aplicacion.getAncho() / 8) / 2;
        grid.setPadding(padding, padding, padding, padding);
        grid.setColumnCount(7);
        grid.setRowCount(6);
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        startGame(grid);
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < Aplicacion.Columnas.length; i++  ) {
            Aplicacion.Columnas[i] = 6;
        }
        finish();
    }

    //-------------------------------------------------------------------------------------------------------------------//
    public void startGame(GridLayout gridLayout) {
        int column = 7;
        //redondear layout
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setColor(Color.WHITE);
        shape.setCornerRadius(500.0f);
        shape.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);
        GradientDrawable shape2 = new GradientDrawable();
        shape2.setShape(GradientDrawable.RECTANGLE);
        shape2.setSize(Aplicacion.getAncho() / 8, Aplicacion.getAncho() / 8);
        Contenedor contenedors[] = new Contenedor[42];

        //iniciando container
        for (int i = 0; i < contenedors.length; i ++ ) {
            contenedors[i] = new Contenedor(getBaseContext(), i +  1);
            Aplicacion.cell[i] = new Celdas(getBaseContext(), contenedors[i], contenedors[i].getIdContainer());
        }
        int valorFila = 1;
        for (int i = 0; i < Aplicacion.cell.length; i ++ ) {
            switch (Aplicacion.cell[i].getID()) {

                case 4:
                case 12:
                case 20:
                case 28:
                    Aplicacion.cell[i].setValorDiagonalInversa(1);
                    break;
                case 3:
                case 11:
                case 19:
                case 27:
                case 35:
                    Aplicacion.cell[i].setValorDiagonalInversa(2);
                    break;
                case 2:
                case 10:
                case 18:
                case 26:
                case 34:
                case 42:
                    Aplicacion.cell[i].setValorDiagonalInversa(3);
                    break;
                case 1:
                case 9:
                case 17:
                case 25:
                case 33:
                case 41:
                    Aplicacion.cell[i].setValorDiagonalInversa(4);
                    break;
                case 8:
                case 16:
                case 24:
                case 32:
                case 40:
                    Aplicacion.cell[i].setValorDiagonalInversa(5);
                    break;
                case 15:
                case 23:
                case 31:
                case 39:
                    Aplicacion.cell[i].setValorDiagonalInversa(6);
                    break;
                default:
                    Aplicacion.cell[i].setValorDiagonalInversa(0);
                    break;
            }
        }
        // anadiendo containers
        for (int i = 0, c = 0, r = 0; i < contenedors.length; i++  , c++  ) {
            if (c == column) {
                valorFila = valorFila  + 7;
                c = 0;
                r ++ ;
            }
            Aplicacion.cell[i].setValorFila(valorFila);
            contenedors[i].setColumna(c   +1, valorFila, i);
            contenedors[i].setBackground(shape);
            Aplicacion.cell[i].setColumna(c +  1);
            Aplicacion.cell[i].setValorDiagonal(r, c);
            Aplicacion.cell[i].setFila(r   +1);
            Aplicacion.cell[i].setBackground(shape2);
            gridLayout.addView(Aplicacion.cell[i], i);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------//
    public void saveDisplaySize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        Aplicacion.guarda_tamano_pantalla(width);
    }

    //  ----------------------------------------------------------------------------------------//
    public void creaDialogo() {
        miDialogo = new Dialog(Game.this);
        miDialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        miDialogo.setContentView(R.layout.win_dialog);
        miDialogo.setCanceledOnTouchOutside(false);
        miDialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        cerrarDiag = miDialogo.findViewById(R.id.btnSalirDialogo);
        cerrarDiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miDialogo.cancel();
                Aplicacion.turno = Celdas.COLOR_ROJO;
                Game.imageTurno.setImageResource(R.drawable.rojo);
                finish();
                startActivity(Aplicacion.GameIntent);
                for (int i = 0; i < Aplicacion.Columnas.length; i++  ) {
                    Aplicacion.Columnas[i] = 6;
                }
            }
        });
        miDialogoPausa = new Dialog(Game.this);
        miDialogoPausa.requestWindowFeature(Window.FEATURE_NO_TITLE);
        miDialogoPausa.setContentView(R.layout.pausa);
        miDialogoPausa.setCanceledOnTouchOutside(true);
        miDialogoPausa.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = getWindow().getDecorView();
        view.setBackgroundResource(android.R.color.transparent);
        cerrarDiagPausa = miDialogoPausa.findViewById(R.id.btnVolverP);
        cerrarDiagPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miDialogoPausa.cancel();

            }
        });
        volverMenu = miDialogoPausa.findViewById(R.id.btnVolverMenuP);
        volverMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < Aplicacion.Columnas.length; i++  ) {
                    Aplicacion.Columnas[i] = 6;
                }
                finish();
                Aplicacion.turno = Celdas.COLOR_ROJO;
            }
        });
        Salir = miDialogoPausa.findViewById(R.id.btnSalirJuegoP);
        Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
                finish();

            }
        });
    }


}