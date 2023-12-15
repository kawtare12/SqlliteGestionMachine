package com.example.sqllite.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqllite.R;
import com.example.sqllite.beans.Machine;
import com.example.sqllite.beans.Marque;
import com.example.sqllite.services.MachineService;
import com.example.sqllite.services.MarqueService;
import com.example.sqllite.ui.machine.MachineF;
import com.example.sqllite.ui.marque.MarqueF;

import java.util.ArrayList;
import java.util.List;

public class Update_machine extends AppCompatActivity {

    EditText reference_input,prix_input,date_input;
    Button btn1,btn2;
    String id,reference,prix,date,marquee;
    Integer p;
    Spinner marque;
    List<String> libelles=new ArrayList<>();
    MarqueService mss;
    List<Marque> marques;
    MachineService ms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_machine);

        reference_input=findViewById(R.id.reference);
        prix_input=findViewById(R.id.prix);
        date_input=findViewById(R.id.date);
        marque=findViewById(R.id.spe);
        btn1=findViewById(R.id.update);
        btn2=findViewById(R.id.delete);
        getData();

        mss=new MarqueService(this);

        marques=mss.findAll();

        for(Marque marque:marques){
            libelles.add(marque.getLibelle());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, libelles);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        marque.setAdapter(spinnerAdapter);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineService ms = new MachineService(Update_machine.this);
                reference = reference_input.getText().toString().trim();
                prix= prix_input.getText().toString().trim();
                date= date_input.getText().toString().trim();
                marquee= marque.getSelectedItem().toString();

                p=Integer.parseInt(prix);

                Machine machine = new Machine(reference,p,date,marquee);
                ms.update(machine, Integer.valueOf(id));
                startActivity(new Intent(getApplicationContext(), MachineF.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }

    void getData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("Reference") &&
                getIntent().hasExtra("Prix") && getIntent().hasExtra("Date") ) {

            id = getIntent().getStringExtra("id");
            reference = getIntent().getStringExtra("Reference");
            prix = getIntent().getStringExtra("Prix");
            date = getIntent().getStringExtra("Date");
            reference_input.setText(reference);
            prix_input.setText(prix);
            date_input.setText(date);

        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + reference + " ?");
        builder.setMessage("Are you sure you want to delete " + reference + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MachineService ay = new MachineService(Update_machine.this);
                ay.delete(Integer.valueOf(id));
                startActivity(new Intent(getApplicationContext(), MachineF.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), MarqueF.class));
            }
        });
        builder.create().show();
    }
}










//
//package com.example.projetsqllite.activities;
//
//        import androidx.appcompat.app.AlertDialog;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.content.DialogInterface;
//        import android.content.Intent;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.EditText;
//        import android.widget.Toast;
//
//        import com.example.projetsqllite.R;
//        import com.example.projetsqllite.beans.Marque;
//        import com.example.projetsqllite.services.MarqueService;
//        import com.example.projetsqllite.ui.marque.MarqueF;
//
//public class Update_marque extends AppCompatActivity {
//    EditText code,libelle;
//    Button btn1,btn2;
//    String id,codee,libellee;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_marque);
//
//        code=findViewById(R.id.code);
//        libelle=findViewById(R.id.libelle);
//        btn1=findViewById(R.id.update);
//        btn2=findViewById(R.id.delete);
//        getData();
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MarqueService srv = new MarqueService(Update_marque.this);
//                codee = code.getText().toString().trim();
//                libellee = libelle.getText().toString().trim();
//                Marque marque = new Marque(codee, libellee);
//                srv.update(marque, Integer.valueOf(id));
//                startActivity(new Intent(getApplicationContext(), MarqueF.class));
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                confirmDialog();
//            }
//        });
//
//    }
//
//    void getData() {
//        if (getIntent().hasExtra("id") && getIntent().hasExtra("Code") &&
//                getIntent().hasExtra("Libelle")) {
//
//            id = getIntent().getStringExtra("id");
//            codee = getIntent().getStringExtra("Code");
//            libellee = getIntent().getStringExtra("Libelle");
//            code.setText(codee);
//            libelle.setText(libellee);
//
//        } else {
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    void confirmDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete " + codee + " ?");
//        builder.setMessage("Are you sure you want to delete " + codee + " ?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                MarqueService srv = new MarqueService(Update_marque.this);
//                srv.delete(Integer.valueOf(id));
//                startActivity(new Intent(getApplicationContext(), MarqueF.class));
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(getApplicationContext(), MarqueF.class));
//            }
//        });
//        builder.create().show();
//    }
//}