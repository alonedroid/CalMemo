package alonedroid.com.calmemo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

@EFragment
public class CmDialogFragment extends DialogFragment {

    @StringRes
    String dialogMessage;

    @StringRes
    String dialogPositive;

    @StringRes
    String dialogNegative;

    View.OnClickListener positive;

    View.OnClickListener negative;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.dialogMessage);
        builder.setPositiveButton(this.dialogPositive, (dialog, which) -> this.positive.onClick(null));
        builder.setNegativeButton(this.dialogNegative, (dialog, which) -> this.negative.onClick(null));
        return builder.create();
    }

    public static CmDialogFragment showFragment(FragmentManager manager, View.OnClickListener positive, View.OnClickListener negative) {
        CmDialogFragment_.FragmentBuilder_ builder_ = CmDialogFragment_.builder();
        CmDialogFragment fragment = builder_.build();
        fragment.positive = positive;
        fragment.negative = negative;
        fragment.show(manager, "");
        return fragment;
    }
}