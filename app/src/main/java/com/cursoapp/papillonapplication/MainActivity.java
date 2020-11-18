package com.cursoapp.papillonapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tlb);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Navegacao.hitorico.size() > 0)
                    Navegacao.hitorico.remove(Navegacao.hitorico.size());
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        Fragment homeFragment = HomeFragment.newInstance();
        Navegacao.hitorico.add(homeFragment);
        openFragment(homeFragment);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportActionBar().setTitle("Home");
                        Fragment homeFragment = HomeFragment.newInstance();
                        Navegacao.hitorico.add(homeFragment);
                        openFragment(homeFragment);
                        break;
                    case R.id.navigation_telefone:
                        getSupportActionBar().setTitle("Contatos");
                        Fragment telefoneFragment = TelefoneFragment.newInstance();
                        Navegacao.hitorico.add(telefoneFragment);
                        openFragment(telefoneFragment);
                        break;
                }
                return true;
            }
        });
    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

