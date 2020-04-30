package com.example.todo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.todo.db.DatabaseHelper;
import com.example.todo.db.model.Todo;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;


public class DetailTodoActivity extends AppCompatActivity {

    TextView tvNaziv,tvOpis,tvPrioritet,tvDatumK,tvVremeK,tvDatumZ,tvVremeZ,tvStatus;
    int position=0;
    Todo todo;
    DatabaseHelper databaseHelper;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_todo);

        setupToolbar();

        position=getIntent().getExtras().getInt("position",0);

        try {
            todo=getDatabaseHelper().getTodoDao().queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvNaziv=findViewById(R.id.tvNaziv);
        tvOpis=findViewById(R.id.tvOpis);
        tvPrioritet=findViewById(R.id.tvPrioritet);
        tvDatumK=findViewById(R.id.tvDatumK);
        tvVremeK=findViewById(R.id.tvVremeK);
        tvDatumZ=findViewById(R.id.tvDatumZ);
        tvVremeZ=findViewById(R.id.tvVremeZ);
        tvStatus=findViewById(R.id.tvStatus);

        tvNaziv.setText(todo.getDbNaziv());
        tvOpis.setText(todo.getDbOpis());
        tvPrioritet.setText(todo.getDbPrioritet());
        tvDatumK.setText(todo.getDbDatumKreiranja());
        tvVremeK.setText(todo.getDbVremeKreiranja());
        tvDatumZ.setText(todo.getDbDatumZavrsetka());
        tvVremeZ.setText(todo.getDbVremeZavrsetka());
        tvStatus.setText(todo.getDbStatus());
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.deleteTodo:
                deleteTodo();
                Toast.makeText(getApplicationContext(),"Todo je obrisan", Toast.LENGTH_SHORT).show();
                break;
            case R.id.closeTodo:
                closeTodo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTodo() {
        try {
            getDatabaseHelper().getTodoDao().deleteById(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeTodo(){
        try {
            todo =getDatabaseHelper().getTodoDao().queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        todo.setDbStatus("uradjen");
        tvStatus.setText("uradjen");
        Toast.makeText(getApplicationContext(),"Todo je cekiran",Toast.LENGTH_SHORT).show();
        try {
            getDatabaseHelper().getTodoDao().update(todo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setHomeButtonEnabled(true);
            actionBar.show();
        }
    }

}
