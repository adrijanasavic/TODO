package com.example.todo.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = Grupa.TABLE_NAME_GRUPA)
public class Grupa {

    public static final String TABLE_NAME_GRUPA = "Grupa";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAZIV = "naziv";
    public static final String FIELD_NAME_DATUM = "datum";
    public static final String FIELD_NAME_VREME = "vreme";
    public static final String FIELD_NAME_OZNAKA = "oznaka";
    public static final String FIELD_NAME_TODO = "todo";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int dbId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String dbNaziv;

    @DatabaseField(columnName = FIELD_NAME_DATUM)
    private String dbDatum;

    @DatabaseField(columnName = FIELD_NAME_VREME)
    private String dbVreme;

    @DatabaseField(columnName = FIELD_NAME_OZNAKA)
    private String dbListaOznakal;

    @ForeignCollectionField(columnName = Grupa.FIELD_NAME_TODO,eager = true)
    private ForeignCollection<Todo> dbTodo;

    public Grupa() {
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getDbNaziv() {
        return dbNaziv;
    }

    public void setDbNaziv(String dbNaziv) {
        this.dbNaziv = dbNaziv;
    }

    public String getDbDatum() {
        return dbDatum;
    }

    public void setDbDatum(String dbDatum) {
        this.dbDatum = dbDatum;
    }

    public String getDbVreme() {
        return dbVreme;
    }

    public void setDbVreme(String dbVreme) {
        this.dbVreme = dbVreme;
    }

    public String getDbListaOznakal() {
        return dbListaOznakal;
    }

    public void setDbListaOznakal(String dbListaOznakal) {
        this.dbListaOznakal = dbListaOznakal;
    }

    public ForeignCollection<Todo> getDbTodo() {
        return dbTodo;
    }

    public void setDbTodo(ForeignCollection<Todo> dbTodo) {
        this.dbTodo = dbTodo;
    }


}
