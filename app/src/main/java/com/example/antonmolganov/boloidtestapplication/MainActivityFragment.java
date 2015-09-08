package com.example.antonmolganov.boloidtestapplication;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.yandex.yandexmapkit.MapController;
import ru.yandex.yandexmapkit.MapView;
import ru.yandex.yandexmapkit.OverlayManager;
import ru.yandex.yandexmapkit.overlay.Overlay;
import ru.yandex.yandexmapkit.overlay.OverlayItem;
import ru.yandex.yandexmapkit.overlay.balloon.BalloonItem;
import ru.yandex.yandexmapkit.overlay.balloon.OnBalloonListener;
import ru.yandex.yandexmapkit.utils.GeoPoint;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayList<Task> currentTasks = new ArrayList<>();

    private MapView mMapView;
    private MapController mMapController;
    private OverlayManager mOverlayManager;
    private Overlay mOverlay;
    private Context mContext;
    private Drawable mOverlayItemDrawable;
    private OnBalloonListener mBalloonListener;
    private AlertDialog mDetailsDialog;
    private AlertDialog.Builder mADBuilder;
    private ProgressDialog mProgressDialog;
    private final DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Date mDate = new Date();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        mMapView = (MapView) fragmentView.findViewById(R.id.map);
        mMapController = mMapView.getMapController();
        mMapController.setZoomCurrent(2);
        mOverlayManager = mMapController.getOverlayManager();
        mOverlay = new Overlay(mMapController);
        mOverlayManager.addOverlay(mOverlay);
        mOverlayItemDrawable = getResources().getDrawable(R.drawable.item);


        mBalloonListener = new OnBalloonListener() {
            @Override
            public void onBalloonViewClick(BalloonItem balloonItem, View view) {
                Task task = ((AdvancedBalloonItem)balloonItem).getTask();
                mADBuilder.setTitle(task.getTitle());
                String message = "Short description: " + task.getText() + "\n\n\n" +
                        "Detailed description: " + task.getLongText() + "\n\n\n";
                ArrayList<Task.Price> prices = task.getPrices();
                if (prices != null) {
                    message += "Prices: \n";
                    for (int i = 0; i < prices.size(); i++) {
                        message+=(task.getPrices().get(i).getDescription()) + ": " + task.getPrices().get(i).getPrice() + "\n";
                    }
                    message += "\n\n";
                }
                mDate.setTime(task.getDate());
                message += "Date added: " + mDateFormat.format(mDate) + "\n\n\n";
                message += "Location: " + task.getLocationText();
                mADBuilder.setMessage(message);
                mADBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mDetailsDialog = mADBuilder.create();
                mDetailsDialog.show();
            }

            @Override
            public void onBalloonShow(BalloonItem balloonItem) {
            }

            @Override
            public void onBalloonHide(BalloonItem balloonItem) {
            }

            @Override
            public void onBalloonAnimationStart(BalloonItem balloonItem) {
            }

            @Override
            public void onBalloonAnimationEnd(BalloonItem balloonItem) {
            }
        };
        mADBuilder = new AlertDialog.Builder(mContext);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Updating...");
        new UpdateTask().execute();
        return fragmentView;
    }

    public void refreshView(){
        new UpdateTask().execute();
    }

    private void refreshOverlay(){
        mOverlay.clearOverlayItems();
        for (int i = 0; i < currentTasks.size(); i++){
            GeoPoint geoPoint = new GeoPoint(currentTasks.get(i).getLat(), currentTasks.get(i).getLon());
            OverlayItem overlayItem = new OverlayItem(geoPoint, mOverlayItemDrawable);
            AdvancedBalloonItem balloonItem = new AdvancedBalloonItem(mContext, geoPoint, currentTasks.get(i));
            balloonItem.setText(balloonItem.getTask().getTitle());
            balloonItem.setOnBalloonListener(mBalloonListener);
            overlayItem.setBalloonItem(balloonItem);
            mOverlay.addOverlayItem(overlayItem);
        }
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                currentTasks = TasksProvider.provideNewData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            refreshOverlay();
            mProgressDialog.dismiss();
        }
    }

    private class AdvancedBalloonItem extends BalloonItem{
        private Task task;

        public AdvancedBalloonItem(Context context, GeoPoint geoPoint, Task task) {
            super(context, geoPoint);
            this.task = task;
        }

        public Task getTask(){
            return task;
        }

        @Override
        public int compareTo(Object another) {
            return 0;
        }
    }
}