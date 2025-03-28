package com.example.crunchy_app.secciones.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crunchy_app.R;
import com.example.crunchy_app.productos.bebidas.fragment.BebidasFragment;
import com.example.crunchy_app.productos.comidas.fragment.ComidasFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String input;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ordersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        Button btComidas = view.findViewById(R.id.btComidas);
        Button btBebidas = view.findViewById(R.id.btBebidas);
        Button btOtros = view.findViewById(R.id.btOtros);
        Button btBuscar = view.findViewById(R.id.btBuscar);
        EditText inputText = view.findViewById(R.id.inputText);

        replaceFragment(new ComidasFragment());

        btComidas.setOnClickListener(v -> replaceFragment(new ComidasFragment()));
        btBebidas.setOnClickListener(v -> replaceFragment(new BebidasFragment()));
        btOtros.setOnClickListener(v -> replaceFragment(new OtrosFragment()));
        btBuscar.setOnClickListener(v -> {
            input = inputText.getText().toString();
            Fragment currentFragment = getChildFragmentManager().findFragmentById(R.id.frame_layout);

            if (currentFragment instanceof ComidasFragment) {
                ComidasFragment comidasFragment = new ComidasFragment();;
                comidasFragment.setFilter(input);
                replaceFragment(comidasFragment);
            } else if (currentFragment instanceof BebidasFragment) {
                BebidasFragment bebidasFragment = new BebidasFragment();
                bebidasFragment.setFilter(input);
                replaceFragment(bebidasFragment);
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}