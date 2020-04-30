package com.example.todo.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.example.todo.R;
import com.example.todo.db.DatabaseHelper;
import com.example.todo.db.model.Grupa;

import com.example.todo.db.model.Todo;
import com.example.todo.dialog.AboutDialog;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DetailActivity extends AppCompatActivity {

    private int position=0;
    private TextView tvNaziv;
    private TextView tvTagovi;

    private TextView tvDatum;
    private TextView tvVreme;

    private DatabaseHelper databaseHelper;
    private Grupa grupa;
    private Toolbar toolbar;
    private SharedPreferences prefs;
    private ArrayList<String> sPrioritet=new ArrayList<>();
    private ArrayList<String> sStatus=new ArrayList<>();
    public static final String DATE_FORMAT_1 = "dd.MM.yyyy.";
    public static final String DATE_FORMAT_2 = "HH:mm";
    private List<String> drawerItems;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private RelativeLayout drawerPane;
    private ActionBarDrawerToggle drawerToggle;
    private AlertDialog dijalog;
    public static final String NOTIF_CHANNEL_ID = "notif_channel_007";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_detail);

        setupToolbar();
        fillData();
        setupDrawer();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        position=getIntent().getExtras().getInt( MainActivity.GRUPA_KEY);

        showGrupaDetails();

    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.addGrupa:
                addTodo();
                break;
            case R.id.deleteGrupa:
                new AlertDialog.Builder(DetailActivity.this).setTitle("Potvrdite brisanje grupe")
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteGrupa();

                                String tekstNotifikacije = "Obrisana grupa";

                                boolean toast = prefs.getBoolean( getString( R.string.toast_key ), false );
                                boolean notif = prefs.getBoolean( getString( R.string.notif_key ), false );

                                if (toast) {
                                    Toast.makeText( DetailActivity.this, tekstNotifikacije, Toast.LENGTH_LONG ).show();

                                }

                                if (notif) {
                                    NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder( DetailActivity.this, NOTIF_CHANNEL_ID );
                                    builder.setSmallIcon( android.R.drawable.ic_menu_delete );
                                    builder.setContentTitle( "Brisanje grupe" );
                                    builder.setContentText( tekstNotifikacije );

                                    Bitmap bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.dialog_frame );


                                    builder.setLargeIcon( bitmap );
                                    notificationManager.notify( 1, builder.build() );

                                    refresh();

                                }
                            }
                        }).setNegativeButton("Ne",null).show();
                break;
            case R.id.editGrupa:
                editGrupa();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private ArrayList<String> getSPrioritet(){
        sPrioritet.add("nizak");
        sPrioritet.add("normalan");
        sPrioritet.add("visok");
        return sPrioritet;
    }

    private ArrayList<String> getSStatus(){
        sStatus.add("aktivan");
        sStatus.add("uradjen");
        return sStatus;
    }

    private void editGrupa(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_grupa_layout);
        dialog.setTitle("Unesite izmene");
        dialog.setCanceledOnTouchOutside(false);

        final EditText etGrupaNaziv = dialog.findViewById(R.id.etGrupaNaziv);
        final EditText etTagovi = dialog.findViewById(R.id.etTagovi);

        Button add = dialog.findViewById(R.id.bAddGrupa);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                grupa.setDbNaziv(etGrupaNaziv.getText().toString());
                grupa.setDbListaOznakal(etTagovi.getText().toString());

                try {
                    getDatabaseHelper().getGrupaDao().update(grupa);

                    String tekstNotifikacije = "Grupa je izmenjena";

                    boolean toast = prefs.getBoolean(getString(R.string.toast_key), false);
                    boolean notif = prefs.getBoolean(getString(R.string.notif_key), false);

                    if (toast) {
                        Toast.makeText(DetailActivity.this, tekstNotifikacije, Toast.LENGTH_LONG).show();

                    }

                    if (notif) {
                        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                        NotificationCompat.Builder builder = new NotificationCompat.Builder( DetailActivity.this, NOTIF_CHANNEL_ID );
                        builder.setSmallIcon( android.R.drawable.ic_menu_edit );
                        builder.setContentTitle( "Izmena grupe" );
                        builder.setContentText( tekstNotifikacije );

                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.dialog_frame );


                        builder.setLargeIcon( bitmap );
                        notificationManager.notify( 1, builder.build() );
                    }

                    refreshGrupa();

                } catch (NumberFormatException e) {
                    Toast.makeText(DetailActivity.this, "Rating mora biti broj", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();


            }

        });

        Button cancel = dialog.findViewById(R.id.cancel_grupa);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addTodo() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_todo_layout);
        dialog.setTitle("Unesite podatke");
        dialog.setCanceledOnTouchOutside(false);

        sPrioritet.clear();
        sStatus.clear();

        final EditText etTodoNaziv = dialog.findViewById(R.id.etTodoNaziv);
        final EditText etTodoOpis = dialog.findViewById(R.id.etTodoOpis);
        final Spinner sPrioritet=dialog.findViewById(R.id.sPrioritet);
        ArrayAdapter<String> adapter2=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,getSPrioritet());
        sPrioritet.setAdapter(adapter2);
        final DatePicker dpDatumZ=dialog.findViewById(R.id.dpDatumZ);
        final TimePicker tpVremeZ=dialog.findViewById(R.id.tpVremeZ);
        tpVremeZ.setIs24HourView(true);
        final Spinner sStatus=dialog.findViewById(R.id.sStatus);
        ArrayAdapter<String> adapter3=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,getSStatus());
        sStatus.setAdapter(adapter3);


        Button add = dialog.findViewById(R.id.bAddTodo);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Todo todo = new Todo();
                todo.setDbNaziv(etTodoNaziv.getText().toString());
                todo.setDbOpis(etTodoOpis.getText().toString());
                todo.setDbPrioritet((String) sPrioritet.getSelectedItem());
                todo.setDbDatumZavrsetka(String.valueOf(dpDatumZ.getDayOfMonth())+"."+String.valueOf(dpDatumZ.getMonth())+"."+dpDatumZ.getYear());
                todo.setDbVremeZavrsetka(tpVremeZ.getCurrentHour().toString()+":"+tpVremeZ.getCurrentMinute().toString());
                todo.setDbStatus((String) sStatus.getSelectedItem());
                todo.setDbDatumKreiranja(getCurrentDate());
                todo.setDbVremeKreiranja(getCurrentTime());
                todo.setDbGrupa(grupa);

                try {
                    getDatabaseHelper().getTodoDao().create(todo);

                    String tekstNotifikacije = "Unet je novi TODO";


                    boolean toast = prefs.getBoolean(getString(R.string.toast_key), false);
                    boolean notif = prefs.getBoolean(getString(R.string.notif_key), false);

                    if (toast) {
                        Toast.makeText(DetailActivity.this, tekstNotifikacije, Toast.LENGTH_LONG).show();

                    }
                    if (notif) {
                        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                        NotificationCompat.Builder builder = new NotificationCompat.Builder( DetailActivity.this, NOTIF_CHANNEL_ID );
                        builder.setSmallIcon( android.R.drawable.ic_input_add );
                        builder.setContentTitle( "Novi TODO" );
                        builder.setContentText( tekstNotifikacije );

                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), android.R.drawable.dialog_frame );


                        builder.setLargeIcon( bitmap );
                        notificationManager.notify( 1, builder.build() );
                    }


                    refresh();

                } catch (NumberFormatException e) {
                    Toast.makeText(DetailActivity.this, "Rating mora biti broj", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();


            }

        });

        Button cancel = dialog.findViewById(R.id.bCancelTodo);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void refreshGrupa(){

        try {
            grupa=getDatabaseHelper().getGrupaDao().queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvNaziv=findViewById(R.id.etNaziv);
        tvTagovi=findViewById(R.id.etTags);
        tvDatum=findViewById(R.id.tvDatum);
        tvVreme=findViewById(R.id.tvVreme);

        tvNaziv.setText(grupa.getDbNaziv());
        tvTagovi.setText(grupa.getDbListaOznakal());
        tvDatum.setText(grupa.getDbDatum());
        tvVreme.setText(grupa.getDbVreme());
    }

    private void refresh() {
        ListView listview = findViewById(R.id.lvTODO);

        if (listview != null) {
            ArrayAdapter<Todo> adapter = (ArrayAdapter<Todo>) listview.getAdapter();

            if (adapter != null) {
                try {
                    adapter.clear();
                    List<Todo> list = getDatabaseHelper().getTodoDao().queryBuilder()
                            .where()
                            .eq(Todo.FIELD_NAME_GRUPA, grupa.getDbId())
                            .query();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showGrupaDetails(){

        try {
            grupa=getDatabaseHelper().getGrupaDao().queryForId(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvNaziv=findViewById(R.id.etNaziv);
        tvTagovi=findViewById(R.id.etTags);
        tvDatum=findViewById(R.id.tvDatum);
        tvVreme=findViewById(R.id.tvVreme);

        tvNaziv.setText(grupa.getDbNaziv());
        tvTagovi.setText(grupa.getDbListaOznakal());
        tvDatum.setText(grupa.getDbDatum());
        tvVreme.setText(grupa.getDbVreme());

        final ListView listView = findViewById(R.id.lvTODO);

        try {
            final List<Todo> list = getDatabaseHelper().getTodoDao().queryBuilder()
                    .where()
                    .eq(Todo.FIELD_NAME_GRUPA, grupa.getDbId())
                    .query();



            ListAdapter adapter = new ArrayAdapter<Todo>(getApplicationContext(), android.R.layout.simple_list_item_1, list){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    if(list.get(position).getDbPrioritet().equals("nizak")&&list.get(position).getDbStatus().equals("aktivan")){
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    }else if(list.get(position).getDbStatus().equals("uradjen") ){
                        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }else if(list.get(position).getDbPrioritet().equals("normalan")){
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    }else if(list.get(position).getDbStatus().equals("uradjen") ){
                        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }else if(list.get(position).getDbPrioritet().equals("visok")){
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }else if(list.get(position).getDbStatus().equals("uradjen") ){
                        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    }
                    return view;
                }
            };
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Todo todo = (Todo) listView.getItemAtPosition(position);
                    Intent intent=new Intent(DetailActivity.this, DetailTodoActivity.class);
                    intent.putExtra("position", todo.getDbId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_2);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void deleteGrupa() {

        try {
            getDatabaseHelper().getGrupaDao().deleteById(position);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupDrawer() {
        drawerList = findViewById(R.id.left_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerPane = findViewById(R.id.drawerPane);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = "Unknown";
                switch (i) {
                    case 0:
                        title = "Grupe";
                        showGrupa();
                        break;
                    case 1:
                        title = "Settings";
                        Intent settings = new Intent(DetailActivity.this, SettingActivity.class);
                        startActivity(settings);
                        break;
                    case 2:
                        title = "About";
                        showDialog();
                        break;

                }
                drawerList.setItemChecked(i, true);
                setTitle(title);
                drawerLayout.closeDrawer(drawerPane);
            }
        });
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerItems));


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
    }

    private void fillData() {
        drawerItems = new ArrayList<>();
        drawerItems.add("Grupe");
        drawerItems.add("Settings");
        drawerItems.add("About");

    }

    private void showGrupa(){
        Intent intent=new Intent(DetailActivity.this,MainActivity.class);
        startActivity(intent);
    }

    private void showDialog() {
        if (dijalog == null) {
            dijalog = new AboutDialog(DetailActivity.this).prepareDialog();
        } else {
            if (dijalog.isShowing()) {
                dijalog.dismiss();
            }
        }
        dijalog.show();
    }

}
