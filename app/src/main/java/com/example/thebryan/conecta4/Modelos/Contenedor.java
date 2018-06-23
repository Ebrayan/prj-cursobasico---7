package com.example.thebryan.conecta4.Modelos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Contenedor extends android.support.v7.widget.AppCompatButton {
    TextView titleText;
    int idContainer;
    int Columna;
    private int Color = Celdas.COLOR_BLANCO;

    // SETTER

    public Contenedor(@NonNull Context context, int id) {
        super(context);
        idContainer = id;
        titleText = new TextView(context);
        // this.addView(titleText);
    }

    // GETTER
    public int getIdContainer() {
        return idContainer;
    }

    public void setColumna(int columna, int valorFila, int i) {
        Columna = columna;
        Aplicacion.cell[i].setValorFila(valorFila);
        titleText.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    public void Color(int color) {
        this.Color = color;
    }

    public void setTitleText(String value) {
        titleText.setText(value);
    }
}