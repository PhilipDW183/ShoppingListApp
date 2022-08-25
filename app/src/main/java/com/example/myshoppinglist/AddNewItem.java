package com.example.myshoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.myshoppinglist.Model.ShoppingModel;
import com.example.myshoppinglist.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItem extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newItemText;
    private Button newItemSaveButton;
    private DatabaseHandler db;

    public static AddNewItem newInstance(){
        return new AddNewItem();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_item, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newItemText = getView().findViewById(R.id.newItemText);
        newItemSaveButton = getView().findViewById(R.id.newItemButton);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        // changes whether updating a task or not
        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String item = bundle.getString("item");
            newItemText.setText(item);
            if(item.length()>0){
                newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            }
        }
        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().equals("")) {
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final boolean finalIsUpdate = isUpdate;
        newItemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String text = newItemText.getText().toString();
                if(finalIsUpdate){
                    db.updateItem(bundle.getInt("id"), text);
                }
                else{
                    ShoppingModel item = new ShoppingModel();
                    item.setItem(text);
                    item.setStatus(0);
                    db.insertItem(item);
                }
                dismiss();
            }
        });

    }


    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }

}
