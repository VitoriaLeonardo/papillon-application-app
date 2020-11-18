package com.cursoapp.papillonapplication.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoapp.papillonapplication.CadClienteFragment;
import com.cursoapp.papillonapplication.Helpers.ClienteHelper;
import com.cursoapp.papillonapplication.Models.Cliente;
import com.cursoapp.papillonapplication.R;

import java.util.List;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.MyViewHolder>{

    private List<Cliente> clientes;
    private AlertDialog alerta;
    private FragmentActivity activityAtual;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNome;
        TextView txtNumero;
        ImageButton btnLigar;

        public MyViewHolder(View item) {
            super(item);
            cv = (CardView) item.findViewById(R.id.crd_item_contato);
            txtNome = (TextView) item.findViewById(R.id.txt_item_nome_contato);
            txtNumero = (TextView) item.findViewById(R.id.txt_item_numero_contato);

            btnLigar = (ImageButton) item.findViewById(R.id.btn_ligar);
        }
    }

    public ContatoAdapter(List<Cliente> clientes, FragmentActivity activityAtual) {
        this.activityAtual = activityAtual;
        this.clientes = clientes;
    }

    @Override
    public ContatoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contatos, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtNome.setText(clientes.get(position).nome);
        holder.txtNumero.setText(clientes.get(position).telefone);
        holder.btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }
    @Override
    public int getItemCount() {
        return clientes.size();
    }
}
