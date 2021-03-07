package com.example.thebryan.conecta4;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.thebryan.conecta4.Modelos.Aplicacion;
import com.example.thebryan.conecta4.Modelos.Celdas;

public class MainActivity extends AppCompatActivity {
    Dialog dialogoInicio;
    Button btnIniciol;
    EditText txt1,txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LottieAnimationView view = findViewById(R.id.animation_view);
        ImageView btnSalir= findViewById(R.id.btnSalir);
        view.setScaleX(view.getScaleX()-0.3f);
        view.setScaleY(view.getScaleY()-0.3f);
        Aplicacion.GameIntent = new Intent(MainActivity.this,Game.class);
        creaDialogo();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoInicio.show();

            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);

            }
        });


    }

    private void creaDialogo() {
        dialogoInicio = new Dialog(MainActivity.this);
        dialogoInicio.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogoInicio.setContentView(R.layout.inicio);
        dialogoInicio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogoInicio.setCanceledOnTouchOutside(false);
        btnIniciol=  dialogoInicio.findViewById(R.id.btnIniciar);
        txt1= dialogoInicio.findViewById(R.id.txtNombre1);
        txt2= dialogoInicio.findViewById(R.id.txtNombre2);
        btnIniciol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt1.getText().toString().matches("")){
                    dialogoInicio.cancel();
                    Aplicacion.turno= Celdas.COLOR_ROJO;
                    Aplicacion.jugador1=txt1.getText().toString();
                    Aplicacion.jugador2=txt2.getText().toString();
                    startActivity(Aplicacion.GameIntent);
                    if(!txt2.isEnabled()){
                        Aplicacion.jugador2="Maquina";
                        Aplicacion.Modo=Aplicacion.SINGLE;
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Escriba un Nombre", Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    public void onRadioButtonClicked( View view){
        if(view.getId()==R.id.btnMulti){
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.VISIBLE);
            btnIniciol.setEnabled(true);
            txt2.setEnabled(true);
        }else if(view.getId()==R.id.btnSolo){
            txt1.setVisibility(View.VISIBLE);
            txt2.setVisibility(View.INVISIBLE);
            txt2.setEnabled(false);
            btnIniciol.setEnabled(true);


        }
    }
}
