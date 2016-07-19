package com.softdesign.devintensive.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.softdesign.devintensive.R;

/**
 * Created by skwmium on 09.07.16.
 */
public class DialogChooseProfilePhoto extends DialogFragment {
    public interface OnChooseItemListener {
        enum Type {
            CAMERA,
            GALLERY
        }

        void onChoosed(@NonNull Type type);
    }

    private OnChooseItemListener mListener;

    public DialogChooseProfilePhoto setListener(OnChooseItemListener listener) {
        mListener = listener;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.profile_choose_photo_dialog_title)
                .setItems(R.array.profile_choose_photo_dialog_array,
                        (dialogInterface, i) -> {
                            if (mListener == null) return;
                            switch (i) {
                                case 0:
                                    mListener.onChoosed(OnChooseItemListener.Type.CAMERA);
                                    break;
                                case 1:
                                    mListener.onChoosed(OnChooseItemListener.Type.GALLERY);
                                    break;
                                case 2:
                                    dialogInterface.cancel();
                                    break;
                            }
                        })
                .create();
    }
}
