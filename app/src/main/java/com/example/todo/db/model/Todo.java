package com.example.todo.db.model;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Todo.TABLE_NAME_TODO)
public class Todo {

    public static final String TABLE_NAME_TODO = "Todo";

    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAZIV = "naziv";
    public static final String FIELD_NAME_OPIS = "opis";
    public static final String FIELD_NAME_PRIORITET = "prioritet";
    public static final String FIELD_NAME_DATUM_KREIRANJA = "datum_kreiranja";
    public static final String FIELD_NAME_VREME_KREIRANJA = "vreme_kreiranja";
    public static final String FIELD_NAME_DATUM_ZAVRSETKA = "datum_zavrsetka";
    public static final String FIELD_NAME_VREME_ZAVRSETKA = "vreme_zavrsetka";
    public static final String FIELD_NAME_STATUS = "status";
    public static final String FIELD_NAME_GRUPA = "grupa";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int dbId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String dbNaziv;

    @DatabaseField(columnName = FIELD_NAME_OPIS)
    private String dbOpis;

    @DatabaseField(columnName = FIELD_NAME_PRIORITET)
    private String dbPrioritet;

    @DatabaseField(columnName = FIELD_NAME_DATUM_KREIRANJA)
    private String dbDatumKreiranja;

    @DatabaseField(columnName = FIELD_NAME_VREME_KREIRANJA)
    private String dbVremeKreiranja;

    @DatabaseField(columnName = FIELD_NAME_DATUM_ZAVRSETKA)
    private String dbDatumZavrsetka;

    @DatabaseField(columnName = FIELD_NAME_VREME_ZAVRSETKA)
    private String dbVremeZavrsetka;

    @DatabaseField(columnName = FIELD_NAME_STATUS)
    private String dbStatus;

    @DatabaseField(columnName = FIELD_NAME_GRUPA,foreign = true,foreignAutoRefresh = true)
    private Grupa dbGrupa;


    public Todo() {
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

    public String getDbOpis() {
        return dbOpis;
    }

    public void setDbOpis(String dbOpis) {
        this.dbOpis = dbOpis;
    }

    public String getDbPrioritet() {
        return dbPrioritet;
    }

    public void setDbPrioritet(String dbPrioritet) {
        this.dbPrioritet = dbPrioritet;
    }

    public String getDbDatumKreiranja() {
        return dbDatumKreiranja;
    }

    public void setDbDatumKreiranja(String dbDatumKreiranja) {
        this.dbDatumKreiranja = dbDatumKreiranja;
    }

    public String getDbVremeKreiranja() {
        return dbVremeKreiranja;
    }

    public void setDbVremeKreiranja(String dbVremeKreiranja) {
        this.dbVremeKreiranja = dbVremeKreiranja;
    }

    public String getDbDatumZavrsetka() {
        return dbDatumZavrsetka;
    }

    public void setDbDatumZavrsetka(String dbDatumZavrsetka) {
        this.dbDatumZavrsetka = dbDatumZavrsetka;
    }

    public String getDbVremeZavrsetka() {
        return dbVremeZavrsetka;
    }

    public void setDbVremeZavrsetka(String dbVremeZavrsetka) {
        this.dbVremeZavrsetka = dbVremeZavrsetka;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    public Grupa getDbGrupa() {
        return dbGrupa;
    }

    public void setDbGrupa(Grupa dbGrupa) {
        this.dbGrupa = dbGrupa;
    }

    @NonNull
    @Override
    public String toString() {
        return dbNaziv;
    }
}
