package com.upv.olcra.sportfuture;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class FragmentHome extends Fragment {
    private final Handler mHandler = new Handler();
    private Runnable mTimer, mTimer2, mTimer3;
    private LineGraphSeries<DataPoint> mSeries;
    private static LineGraphSeries<DataPoint> mSeries2;
    private static LineGraphSeries<DataPoint> mSeries3;
    private static double graphLastXValue = 5d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_home, container, false);

       /* GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        mSeries = new LineGraphSeries<>();
        mSeries.setColor(Color.BLACK);
        mSeries2 = new LineGraphSeries<>();
        mSeries2.setColor(Color.BLUE);
        mSeries3 = new LineGraphSeries<>();
        mSeries3.setColor(Color.GREEN);
        graph.addSeries(mSeries);
        graph.addSeries(mSeries2);
        graph.addSeries(mSeries3);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        graph.setTitle("Random Numbers");*/

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*mTimer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 1d;
                mSeries.appendData(new DataPoint(graphLastXValue, getRandom()), true, 40);
                mHandler.postDelayed(this, 200);
            }
        };
        mHandler.postDelayed(mTimer, 300);*/
    }

    @Override
    public void onPause() {
        mHandler.removeCallbacks(mTimer);
        super.onPause();
    }

    /*private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }
*/
    public interface OnFragmentInteractionListener {
        public void updateData(String data);
    }
}
