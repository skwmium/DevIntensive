package com.softdesign.devintensive.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.softdesign.devintensive.R;

/**
 * Created by skwmium on 09.07.16.
 */
public class DialogChooseProfilePhoto extends DialogFragment {
    public interface OnChooseItemListener {
        void chooseCamera();

        void chooseGallery();
    }

    private OnChooseItemListener mListener;

    public DialogChooseProfilePhoto setListener(OnChooseItemListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.profile_choose_photo_dialog_title)
                .setItems(R.array.profile_choose_photo_dialog_array,
                        (dialogInterface, i) -> {
                            if (mListener == null) return;
                            switch (i) {
                                case 0:
                                    mListener.chooseCamera();
                                    break;
                                case 1:
                                    mListener.chooseGallery();
                                    break;
                                case 2:
                                    dialogInterface.cancel();
                                    break;
                            }
                        })
                .create();
    }
}
