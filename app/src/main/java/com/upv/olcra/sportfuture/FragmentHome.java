package com.upv.olcra.sportfuture;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentHome extends Fragment {
    DrawView drawView;

    public FragmentHome(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        drawView = new DrawView(getContext());
        drawView.setBackgroundColor(Color.WHITE);
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);
        return view;
    }



    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

   *//* public interface OnFragmentInteractionListener {
        public void updateData(String data);
    }*//*
*/

}
