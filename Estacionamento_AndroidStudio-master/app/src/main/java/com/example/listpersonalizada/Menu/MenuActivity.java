package com.example.listpersonalizada.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.listpersonalizada.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void btnEntrada (View view) {
        Intent intencao = new Intent(this, EntradaActivity.class);
        startActivity(intencao);
    }

    public void btnEstacionados (View view) {
        Intent intencao = new Intent(this, EstacionadosActivity.class);
        startActivity(intencao);
    }

    public void btnSaida (View view) {
        Intent intencao = new Intent(this, SaidaActivity.class);
        startActivity(intencao);
    }

    public void btnFinalizados (View view) {
        Intent intencao = new Intent(this, FinalizadosActivity.class);
        startActivity(intencao);
    }
}
