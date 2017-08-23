package com.upv.olcra.sportfuture;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FragmentSettings extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButtonLow;
    RadioButton radioButtonMed;
    RadioButton radioButtonHigh;
    GlobalVariables myGV;

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButtonLow = (RadioButton) view.findViewById(R.id.radioButtonLow);
        radioButtonMed = (RadioButton) view.findViewById(R.id.radioButtonMed);
        radioButtonHigh = (RadioButton) view.findViewById(R.id.radioButtonHigh);
        switch(GlobalVariables.notificationValue){
            case 0:
                radioButtonLow.setChecked(true);
                break;
            case 1:
                radioButtonMed.setChecked(true);
                break;
            case 2:
                radioButtonHigh.setChecked(true);
                break;
            default:
                radioButtonLow.setChecked(true);
                break;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == radioButtonLow.getId()){
                    GlobalVariables.notificationValue = 0;
                    System.out.println("value " + GlobalVariables.notificationValue);
                }
                if(checkedId == radioButtonMed.getId()){
                    GlobalVariables.notificationValue = 1;
                    System.out.println("value " + GlobalVariables.notificationValue);
                }
                if(checkedId == radioButtonHigh.getId()){
                    GlobalVariables.notificationValue = 2;
                    System.out.println("value " + GlobalVariables.notificationValue);
                }
            }
        });

        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
