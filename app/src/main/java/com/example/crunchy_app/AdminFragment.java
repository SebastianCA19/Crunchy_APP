package com.example.crunchy_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFragment extends Fragment {

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        // Buscar el botón dentro de la vista inflada
        Button btnHistorial = view.findViewById(R.id.btHistorial);
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHistory();
            }
        });

        return view;
    }

    public void goToHistory() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }
}
