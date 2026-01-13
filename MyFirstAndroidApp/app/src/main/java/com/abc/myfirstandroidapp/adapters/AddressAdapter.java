package com.abc.myfirstandroidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.entity.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    public interface onAddressClick{
        void onLongClick(Address address);
    }

    private Context context;
    private List<Address> addressList;
    private onAddressClick listener;


    public AddressAdapter(Context context, List<Address> addressList, onAddressClick listener) {
        this.context = context;
        this.addressList = addressList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_address, parent, false);
        return new AddressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address =addressList.get(position);

        holder.tvName.setText(address.getName());
        holder.tvCity.setText(address.getCity());
        holder.tvPo.setText(address.getPo());

        holder.itemView.setOnLongClickListener(v -> {
            if(listener != null) listener.onLongClick(address);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvCity, tvPo;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName =itemView.findViewById(R.id.tvName);
            tvCity =itemView.findViewById(R.id.tvCity);
            tvPo =itemView.findViewById(R.id.tvPo);

        }
    }
}
