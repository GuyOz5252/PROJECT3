package com.example.project2_rev2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

public class HelperMethods {

    // https://www.geeksforgeeks.org/how-to-convert-a-vector-to-bitmap-in-android/
    public static Bitmap getBitmapFromVectorDrawable(Context context,@DrawableRes int resourceId) {
        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromPicture(Context context,@DrawableRes int resourceId) {
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    public static void deleteUser(Context context) {
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
}
