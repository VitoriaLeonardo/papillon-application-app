package com.cursoapp.papillonapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    private Button cadastrar;
    private Button consultar;
    private Button sair;
    private Button sobre;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

        cadastrar = (Button) view.findViewById(R.id.btn_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cadFragment = CadastrosFragment.newInstance();
                openFragment(cadFragment);
            }
        });

        consultar = (Button) view.findViewById(R.id.btn_consultar);
        consultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Fragment conFragment = ConsultasFragment.newInstance();
                openFragment(conFragment);
            }
        });

        sair = (Button) view.findViewById(R.id.btn_sair);
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        sobre = (Button) view.findViewById(R.id.btn_sobre);
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment sobreFragment = SobreFragment.newInstance();
                openFragment(sobreFragment);
            }
        });
        return view;

    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}