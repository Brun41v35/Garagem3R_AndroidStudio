package com.example.listpersonalizada.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listpersonalizada.BancoDeDados.Estacionamento;
import com.example.listpersonalizada.BancoDeDados.EstacionamentoDao;
import com.example.listpersonalizada.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaidaActivity extends AppCompatActivity {

    static String horaSaida;
    static String dataSaida;
    static String tempoEstacionado;
    static EditText placaSaida;
    static EditText total;
    static TextView tempo;
    static String horarioEntrada;
    static long diferencaHoras;
    static long minutos;
    static long horas;
    private EstacionamentoDao dao;
    private ListView listView;
    private ArrayList<Estacionamento> estacionamentos;
    private ArrayList<Estacionamento> estacionamentosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saida);
        dao = new EstacionamentoDao(this);
        estacionamentos = dao.obterTodos();
        estacionamentosFiltrados.addAll(estacionamentos);
        tempo = findViewById(R.id.idTextoTempo);
    }

    public void procuraPlaca(String placa) {

        for (Estacionamento e : estacionamentosFiltrados) {

            if ((e.getPlacaCarro().toLowerCase().contains(placa.toLowerCase()))) {

                TextView dataEntradaSaida = findViewById(R.id.idDataEntradaSaida);
                dataEntradaSaida.setText(e.getDataEntrada());

                TextView horaEntradaSaida = findViewById(R.id.idHoraEntradaSaida);
                horaEntradaSaida.setText(e.getHoraEntrada());

                horarioEntrada = e.getHoraEntrada();

// ============================================== DATA =============================================
                TextView dataTexto = findViewById(R.id.idDataSaida);

                SimpleDateFormat dateFormat_hor = new SimpleDateFormat("dd-MM-yyyy");
                final Date dat = new Date();
                Calendar calendarioB = Calendar.getInstance();

                calendarioB.setTime(dat);
                Date data_atual = calendarioB.getTime();
                dataSaida = dateFormat_hor.format(data_atual);
                dataTexto.setText(dataSaida);

// ============================================== HORA =============================================
                TextView t = findViewById(R.id.idHoraSaida);

                SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");
                Date dataHora = new Date();
                Calendar calendario = Calendar.getInstance();

                calendario.setTime(dataHora);
                Date hora_atual = calendario.getTime();
                horaSaida = dateFormat_hora.format(hora_atual);
                t.setText(horaSaida);
            }
        }
    }

    public void btnCalcular(View view) {

        placaSaida = findViewById(R.id.idPlacaSaida);

        if (placaSaida.getText().toString().trim().isEmpty()) {
            placaSaida.setError("Coloque um valor");
        } else {
            String conteudo = placaSaida.getText().toString();
            procuraPlaca(conteudo);
            calcularHora(horarioEntrada, horaSaida);
            total = findViewById(R.id.idTotalValorSaida);
            total.setText(valorEstacionamento());
        }
    }

    public void btnFinalizar(View view) {

    }

    public static void calcularHora(String horaEntrada, String horaSaida) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {

            Date horaIni = sdf.parse(horaEntrada);
            Date horaFim = sdf.parse(horaSaida);

            long horaI = horaIni.getTime();
            long horaF = horaFim.getTime();

            diferencaHoras = horaF - horaI;

            //segundos = (diferencaHoras / 1000) % 60;      // se não precisar de segundos, basta remover esta linha.
            minutos = (diferencaHoras / 60000) % 60;     // 60000   = 60 * 1000
            horas = diferencaHoras / 3600000;            // 3600000 = 60 * 60 * 1000

            tempoEstacionado = String.format("%02d:%02d", horas, minutos);
            tempo.setText(tempoEstacionado);

        } catch (Exception ex) {

            System.out.println("Erro");
        }
    }

    public String valorEstacionamento() {

        String retornoValor = "0";

        if (tempoEstacionado.compareTo("00:00") > 0 && tempoEstacionado.compareTo("00:30") < 0  ) {
            retornoValor = "R$ 4,00";

        } else {

            if (tempoEstacionado.compareTo("00:30") > 0 && tempoEstacionado.compareTo("04:00") < 0) {
                retornoValor = "R$ 7,00";

            } else {

                if (tempoEstacionado.compareTo("04:00") > 0) {
                    retornoValor = "R$ 15,00";
                }
            }
        }

        return retornoValor;
    }
}