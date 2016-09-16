package com.rubik.apprecadosp4.app;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rubik.apprecadosp4.R;
import com.rubik.apprecadosp4.app.volley.RequestVolley;
import com.rubik.apprecadosp4.model.Recado;

/**
 * Created by Rubik on 15/9/16.
 */
public class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.MyViewHolder>{

    private static final String TAG = MyRecycleAdapter.class.getSimpleName();


    @Override
    public int getItemCount() {return RequestVolley.listRecados != null ? RequestVolley.listRecados.size() : 0; }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Recado recado = RequestVolley.listRecados.get(position);

            //MM dd, yyyy hh:mm:ss aa
        CharSequence fecha = (DateFormat.format("dd/MM/yyyy HH:mm:ss", recado.getFecha_hora()));  //
        holder.txtFecha.setText(fecha);

        CharSequence fechaMaxima = (DateFormat.format("dd/MM/yyyy HH:mm:ss", recado.getFecha_hora_maxima()));
        holder.txtFechaMaxima.setText(fechaMaxima);

        holder.txtTlf.setText(recado.getTelefono());
        holder.txtCliente.setText(recado.getNombre_cliente());
        holder.txtDescription.setText(recado.getDescripcion());
        holder.txtDirEntrega.setText("Dir Entrega: " + recado.getDireccion_entrega());
        holder.txtDirRecogida.setText("Dir Recogida: " + recado.getDireccion_recogida());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFecha, txtTlf, txtCliente, txtDescription, txtFechaMaxima, txtDirEntrega, txtDirRecogida;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtFecha = (TextView) itemView.findViewById(R.id.txtFecha);
            txtTlf = (TextView) itemView.findViewById(R.id.txtTlf);
            txtCliente = (TextView) itemView.findViewById(R.id.txtCliente);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtFechaMaxima = (TextView) itemView.findViewById(R.id.txtFechaMaxima);
            txtDirEntrega = (TextView) itemView.findViewById(R.id.lblDirEntrega);
            txtDirRecogida = (TextView) itemView.findViewById(R.id.lblDirRecogida);
        }
    }
}
