package com.example.sqllite.ui.marque;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqllite.Adapter.MarqueAdapter;
import com.example.sqllite.R;

import com.example.sqllite.activities.Add_marque;
import com.example.sqllite.beans.Marque;
import com.example.sqllite.services.MarqueService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;


public class MarqueF extends Fragment {
    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private List<Marque> marques;
    private MarqueAdapter marqueAdapter;
    private MarqueService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // lier avec layout : fragment_marque, definir recyclerview et le bouton add
        View view= inflater.inflate(R.layout.fragment_marque, container, false);
        recyclerView = view.findViewById(R.id.marqueRecyclerView);

        button=view.findViewById(R.id.add_marque_button);
        //au click sur bouton add ca nous mene vers Add_marque activity
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_marque.class);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }
   //lorsque la vue est créer storeDataArrays est appele pour obtenir les donnees de la base de donnees
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storeDataInArrays();
        marqueAdapter = new MarqueAdapter(requireActivity(), requireContext(), marques);
        recyclerView.setAdapter(marqueAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void storeDataInArrays() {
        //marqueservice recupere les donnees de la base de données
        marques = new ArrayList<>();
        service = new MarqueService(getActivity());
        marques = service.findAll();// remplir  la liste marques par les marques du sevice qui commnique avec
        //la bdd
    }




}