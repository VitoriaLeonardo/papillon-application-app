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

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> {
    private List<Cliente> clientes;
    private AlertDialog alerta;
    private FragmentActivity activityAtual;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtNome;
        TextView txtCnpj;
        ImageButton btnDelCli;
        ImageButton btnEdiCli;

        public MyViewHolder(View item) {
            super(item);
            cv = (CardView) item.findViewById(R.id.crd_item_cliente);
            txtNome = (TextView) item.findViewById(R.id.txt_item_nome);
            txtCnpj = (TextView) item.findViewById(R.id.txt_item_cnpj);

            btnDelCli = (ImageButton) item.findViewById(R.id.btn_deletar_cli);
            btnEdiCli = (ImageButton) item.findViewById(R.id.btn_editar_cli);
        }
    }

    public ClienteAdapter(List<Cliente> clientes, FragmentActivity activityAtual) {
        this.activityAtual = activityAtual;
        this.clientes = clientes;
    }

    @Override
    public ClienteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_cliente, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.txtNome.setText(clientes.get(position).nome);
        holder.txtCnpj.setText(clientes.get(position).cnpj);
        holder.btnDelCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensagemAlerta(v.getContext(),holder.getAdapterPosition());
            }
        });
        holder.btnEdiCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadClienteFragment cadCliente = new CadClienteFragment();
                cadCliente.cliente = clientes.get(holder.getAdapterPosition());
                Fragment cadCliFragment = cadCliente;
                openFragment(cadCliFragment);
                Toast.makeText(v.getContext(), "ID DO CLIENTE: " + clientes.get(holder.getAdapterPosition()).id, Toast.LENGTH_LONG).show();
            }
        });

    }
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = activityAtual.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void mensagemAlerta(final Context context, final int itemPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Deletar usuário");
        builder.setMessage("Deletar registro? Esse registro será deletado permanentemente!");

        builder.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ClienteHelper clienteHelper = new ClienteHelper(context);
                if(clienteHelper.deletarCliente(clientes.get(itemPosition).id)){
                    clientes.remove(itemPosition);
                    notifyItemRemoved(itemPosition);
                    Toast.makeText(context, "Usuário deletado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Erro ao deletar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(context, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
}
