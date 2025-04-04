package com.example.crunchy_app.secciones.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crunchy_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtrosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtrosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtrosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtrosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtrosFragment newInstance(String param1, String param2) {
        OtrosFragment fragment = new OtrosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otros, container, false);
        Button btnConvert = view.findViewById(R.id.btn_convert);
        btnConvert.setOnClickListener(this::convertValues);
        return view;
    }

    public void convertValues(View view) {
        // Obtener la vista raíz del fragmento
        View rootView = getView();
        if (rootView == null) return;

        // Obtener los valores de los campos de entrada
        EditText etGramos = rootView.findViewById(R.id.inGramos);
        EditText etDinero = rootView.findViewById(R.id.inDinero);

        String gramos = etGramos.getText().toString();
        String dinero = etDinero.getText().toString();

        if (gramos.isEmpty() && dinero.isEmpty()) {
            return;
        }

        try {
            if (!gramos.isEmpty() && dinero.isEmpty()) {
                double gramosNum = Double.parseDouble(gramos);
                etDinero.setText("$" + (gramosNum * 80));
            } else if (!dinero.isEmpty() && gramos.isEmpty()) {
                double dineroNum = Double.parseDouble(dinero);
                etGramos.setText((dineroNum / 80) + "gr");
            }
        } catch (NumberFormatException e) {
            // Manejar un posible error de conversión
            e.printStackTrace();
        }
    }


}