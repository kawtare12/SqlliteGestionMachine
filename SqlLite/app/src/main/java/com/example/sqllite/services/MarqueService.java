package com.example.sqllite.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.sqllite.beans.Marque;

// service qui interagit avec une base de donnes SQlite
// il est necessaire d'importer sqlite Database
public class MarqueService {

// necessaire pour afficher les messages toast / conetxt de lapplication
    private Context context;
// declarer le nom de la table et les colonnes de la base de donnees
    private static final String TABLE_MARQUE = "marque";


    private static final String Id = "id";
    private static final String Code = "code";

    private static final String Libelle = "libelle";



// class qui etend de SqliteOpenHelper gere creation et gerer la connexion avec la bdd Sqlite
    private MyDataBaseHelper helper;

    public MarqueService(Context context) {
        this.helper = new MyDataBaseHelper(context);
        this.context = context;
    }
// methode pour creer une marque
    public void create(Marque e) {
        // creer connexion avec la bdd en mode ecriture pour inserer data
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();// specifier les valeurs a inseerer
        values.put(Code, e.getCode());
        values.put(Libelle, e.getLibelle());
        long result=db.insert(TABLE_MARQUE,
                null,
                values);
        if(result == -1){
            Toast.makeText(context, "Failed to add this marque", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "new marque Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    public List<Marque> findAll(){
        List<Marque> eds = new ArrayList<>();
        String req ="select * from "+TABLE_MARQUE;
        // en mode lecture car on veut afficher toutes les marques
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Marque e = null;
        if(c.moveToFirst()){
            do{
                e = new Marque();
                e.setId(c.getInt(0));
                e.setCode(c.getString(1));
                e.setLibelle(c.getString(2));
                eds.add(e);
            }while(c.moveToNext());
        }
        return eds;
    }
    public void update(Marque e,int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, id);
        values.put(Code, e.getCode());
        values.put(Libelle, e.getLibelle());

        long result=db.update(TABLE_MARQUE,
                values,
                "id = ?",
                new String[]{String.valueOf(id)});
        if(result == -1){
            Toast.makeText(context, "Failed to update this marque", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "marque Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_MARQUE,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
