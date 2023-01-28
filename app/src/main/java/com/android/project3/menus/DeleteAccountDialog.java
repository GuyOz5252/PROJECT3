package com.android.project3.menus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.project3.R;
import com.android.project3.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteAccountDialog extends Dialog implements View.OnTouchListener {

    private Context context;

    EditText edtUsernameConfirm;
    Button btnDeleteAccountDialog;

    public DeleteAccountDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.dialog_delete_account);
        getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        setTitle("delete account");

        edtUsernameConfirm = findViewById(R.id.edtUsernameConfirm_deleteAccountDialog);
        btnDeleteAccountDialog = findViewById(R.id.btnDeleteAccount_deleteAccountDialog);

        btnDeleteAccountDialog.setOnTouchListener(this);
    }

    public void deleteUser(Context context) {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("data_segment")
                .document("save_data")
                .delete().addOnCompleteListener(task -> FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("data_segment")
                        .document("player_stats")
                        .delete().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                FirebaseFirestore.getInstance().collection("users")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .delete().addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task3 -> {
                                                    if (task3.isSuccessful()) {
                                                        Toast.makeText(context, "user deleted", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, task3.getException().getMessage() + " from user delete", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(context, task2.getException().getMessage() + " from userDoc delete", Toast.LENGTH_LONG).show();
                                            }
                                        });

                            } else {
                                Toast.makeText(context, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }));
    }

    public void clickDeleteAccount() {
        if (edtUsernameConfirm.getText().toString().isEmpty()) {
            edtUsernameConfirm.setError("enter username to confirm");
            edtUsernameConfirm.requestFocus();
            return;
        }
        if (edtUsernameConfirm.getText().toString().equals(User.getInstance().getUsername())) {
            deleteUser(context);
            context.startActivity(new Intent(context, Login.class));
            ((Activity)context).finish();
        } else {
            edtUsernameConfirm.setError("incorrect username");
            edtUsernameConfirm.requestFocus();
        }
    }

    public void clickDeleteAccount(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickDeleteAccount();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.btnDeleteAccount_deleteAccountDialog) {
            clickDeleteAccount(view, motionEvent);
        }
        return true;
    }
}
