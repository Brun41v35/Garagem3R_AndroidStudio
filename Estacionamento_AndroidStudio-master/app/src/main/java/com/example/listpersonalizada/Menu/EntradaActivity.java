package com.example.listpersonalizada.Menu;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listpersonalizada.BancoDeDados.Estacionamento;
import com.example.listpersonalizada.BancoDeDados.EstacionamentoDao;
import com.example.listpersonalizada.R;

public class EntradaActivity extends AppCompatActivity {

    private EstacionamentoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        final Estacionamento estacionamento = new Estacionamento();
        final EditText placa = findViewById(R.id.idPlacaTexto);
        dao = new EstacionamentoDao(this);

// ============================================== DATA =============================================
        TextView dataTexto = findViewById(R.id.idDataTexto);

        SimpleDateFormat dateFormat_hor = new SimpleDateFormat("dd-MM-yyyy");
        final Date dat = new Date();
        Calendar calendarioB = Calendar.getInstance();

        calendarioB.setTime(dat);
        Date data_atual = calendarioB.getTime();
        final String dta = dateFormat_hor.format(data_atual);
        dataTexto.setText(dta);

// ============================================== HORA =============================================
        TextView t = findViewById(R.id.idDataHora);

        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");
        Date data = new Date();
        Calendar calendario = Calendar.getInstance();

        calendario.setTime(data);
        Date hora_atual = calendario.getTime();
        final String dataFor = dateFormat_hora.format(hora_atual);
        t.setText(dataFor);

// ============================================== Botão Salvar ======================================
        Button salvar = findViewById(R.id.idSalvarData);
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placa.getText().toString().trim().isEmpty()) {
                    placa.setError("Coloque um valor");
                } else {
                    estacionamento.setHoraEntrada(dataFor);
                    estacionamento.setDataEntrada(dta);
                    estacionamento.setPlacaCarro(placa.getText().toString());
                    dao.inserirPlaca(estacionamento);

                    Context contexto = getApplicationContext();
                    String texto = "Salvo com sucesso!";
                    int duracao = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(contexto, texto,duracao);
                    toast.show();
                }
            }
        });
    }
}