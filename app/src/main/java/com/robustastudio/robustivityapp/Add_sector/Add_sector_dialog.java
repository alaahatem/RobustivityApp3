package com.robustastudio.robustivityapp.Add_sector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.robustastudio.robustivityapp.R;

import java.util.concurrent.CancellationException;

/**
 * Created by MALAK SHAKER on 4/13/2018.
 */

public class Add_sector_dialog extends AppCompatDialogFragment {

    public sectorDialog listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_pop_add_sector,null);
        final EditText mEdit =  view.findViewById(R.id.sectorName);

        builder.setView(view).setTitle("Add new sector").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String name = mEdit.getText().toString();
                    listener.applyname(name);
                }catch (NullPointerException x){
                   // Toast.makeText(getContext(),"Enter a name")
                  listener.nameNotValid();
                }


            }
        });

        return builder.create();

    }

    public void onAttach(Context ctx){
        super.onAttach(ctx);

        try{
            listener =(sectorDialog)ctx;

        }catch(ClassCastException x){
            throw new ClassCastException(ctx.toString()+"must implement listener");
        }

    }
    public interface sectorDialog{
        void applyname(String name);
        void nameNotValid();
    }
}
