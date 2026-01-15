package com.abc.myemployeeapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.myemployeeapp.R;
import com.abc.myemployeeapp.entity.Employee;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    public interface OnEmployeeClick {
        void onLongClick(Employee employee);
    }

    private Context context;
    private List<Employee> employeeList;
    private OnEmployeeClick listener;

    public EmployeeAdapter(Context context, List<Employee> employeeList, OnEmployeeClick listener) {
        this.context = context;
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee e = employeeList.get(position);

        holder.tvName.setText(e.getName());
        holder.tvEmail.setText("Email: " + e.getEmail());
        holder.tvDepartment.setText("Department: " + e.getDepartment());
        holder.tvSalary.setText("Salary: " + e.getSalary());

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        holder.tvJoiningDate.setText(
                "Joining Date: " + sdf.format(new Date(e.getJoiningDate()))
        );

        // 🔹 Load image safely
        if (e.getImagePath() != null && !e.getImagePath().isEmpty()) {
            Glide.with(context)
                    .load(Uri.parse(e.getImagePath()))
                    .placeholder(R.drawable.image_24)
                    .error(R.drawable.image_24)
                    .into(holder.ivProfile);
        } else {
            holder.ivProfile.setImageResource(R.drawable.image_24);
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onLongClick(e);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return employeeList == null ? 0 : employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvName, tvEmail, tvDepartment, tvSalary, tvJoiningDate;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.tvImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvJoiningDate = itemView.findViewById(R.id.tvJoiningDate);
        }
    }
}
