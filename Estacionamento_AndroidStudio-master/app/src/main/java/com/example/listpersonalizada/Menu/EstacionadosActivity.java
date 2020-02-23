package com.example.listpersonalizada.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.listpersonalizada.BancoDeDados.Estacionamento;
import com.example.listpersonalizada.BancoDeDados.EstacionamentoDao;
import com.example.listpersonalizada.EstacionamentoAdapter;
import com.example.listpersonalizada.R;

import java.util.ArrayList;
import java.util.List;

public class EstacionadosActivity extends AppCompatActivity {

    private ListView listView;
    private EstacionamentoDao dao;
    private ArrayList<Estacionamento> estacionamentos;
    private ArrayList<Estacionamento> estacionamentosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listaCarros);
        dao = new EstacionamentoDao(this);
        estacionamentos = dao.obterTodos();
        estacionamentosFiltrados.addAll(estacionamentos);
        ArrayAdapter adapter = new EstacionamentoAdapter(this,estacionamentosFiltrados);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                procuraPlaca(newText);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);
    }

    public void procuraPlaca(String placa) {
        estacionamentosFiltrados.clear();
        for(Estacionamento e : estacionamentos) {
            if(e.getPlacaCarro().toLowerCase().contains(placa.toLowerCase())){
                estacionamentosFiltrados.add(e);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Estacionamento estacionamentoExcluir = estacionamentosFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente quer excluir a placa selecionada ?")
                .setNegativeButton("NÃO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        estacionamentosFiltrados.remove(estacionamentoExcluir);
                        estacionamentos.remove(estacionamentoExcluir);
                        dao.excluir(estacionamentoExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        estacionamentos = dao.obterTodos();
        estacionamentosFiltrados.clear();
        estacionamentosFiltrados.addAll(estacionamentos);
        listView.invalidateViews();
    }
}
