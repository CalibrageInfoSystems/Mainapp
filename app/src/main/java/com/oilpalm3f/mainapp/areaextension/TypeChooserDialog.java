package com.oilpalm3f.mainapp.areaextension;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.oilpalm3f.mainapp.R;

public class TypeChooserDialog extends DialogFragment {

    public typeChooser typeChooser;

    public TypeChooserDialog() {

    }

    public static TypeChooserDialog newInstance(String type) {
        TypeChooserDialog fragment = new TypeChooserDialog();
        Bundle args = new Bundle();
        args.putString("type", "" + type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fertilizer_type_chooser, container);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int)(displayRectangle.width() * 0.7f));

        RelativeLayout newRegRel = (RelativeLayout) view.findViewById(R.id.fertilizerRel);
        newRegRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeChooser.typeChoosed(1);
                getDialog().dismiss();
            }
        });

        RelativeLayout newPlotRegRel = (RelativeLayout) view.findViewById(R.id.nutrientDetailRel);
        newPlotRegRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeChooser.typeChoosed(2);
                getDialog().dismiss();
            }
        });


        return view;
    }

    public void setChooser(typeChooser typeChooser) {
        this.typeChooser = typeChooser;
    }

    public interface typeChooser {
        void typeChoosed(int selectedType);
    }
}

