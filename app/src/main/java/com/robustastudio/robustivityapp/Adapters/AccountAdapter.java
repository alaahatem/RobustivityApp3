package com.robustastudio.robustivityapp.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robustastudio.robustivityapp.Models.Accounts;
import com.robustastudio.robustivityapp.R;
import com.robustastudio.robustivityapp.ViewAccount;

import java.util.List;

/**
 * Created by hp on 20/04/2018.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {
    List<Accounts> Account;


    public AccountAdapter(List<Accounts> account) {
        this.Account = account;
    }
    @Override
    public AccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountAdapter.ViewHolder holder, final int position) {
        holder.account_name.setText(Account.get(position).getName());

        holder.account_email.setText(Account.get(position).getEmail());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ViewAccount.class);
                intent.putExtra("account_name", Account.get(position).getName());
                intent.putExtra("account_email", Account.get(position).getEmail());
                intent.putExtra("account_address", Account.get(position).getAddress());
                intent.putExtra("account_phone", Account.get(position).getPhonenumber());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null!=Account?Account.size():0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView account_name;
        public TextView account_email;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            account_name= itemView.findViewById(R.id.account_name);
            account_email=itemView.findViewById(R.id.account_email);
            linearLayout =itemView.findViewById(R.id.linear_layout_account);
        }
    }

}
