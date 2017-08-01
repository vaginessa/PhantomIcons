package highridas.phantom.icons.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import highridas.phantom.icons.R;
import highridas.phantom.icons.adapters.WallAdapter;
import highridas.phantom.icons.items.WallpaperItem;
import highridas.phantom.icons.others.SpacesItemDecoration;
import highridas.phantom.icons.tasks.GetWallpapers;
import highridas.phantom.icons.ui.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Patrick Ridge on 2017-07-24.
 */
public class WallpapersFragment extends Fragment implements GetWallpapers.Callbacks {

    ArrayList<WallpaperItem> items = new ArrayList<>();
    private Context context;
    private WallAdapter adapter;
    private View mainView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_wall, container, false);
        context = mainView.getContext();
        Snackbar.make(mainView.findViewById(R.id.coordinating_wall),
                "Loading Themes", Snackbar.LENGTH_SHORT).show();
        new GetWallpapers(context, this).execute();
        toolbar = (Toolbar) mainView.findViewById(R.id.toolbar_wall);
        setActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.primaryColorDark));
        (mainView.findViewById(R.id.button_theme1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getResources().getString(R.string.transparent_theme_url));
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(goToMarket);
            }
        });
        (mainView.findViewById(R.id.button_theme2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getResources().getString(R.string.phantom_theme_link));
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(goToMarket);
            }
        });
        return mainView;
    }

    private void setActionBar(Toolbar toolbar) {
        HomeActivity activity = ((HomeActivity) getActivity());
        activity.setSupportActionBar(toolbar);
        activity.updateToggleButton(toolbar);
    }

    private ActionBar getActionBar() {
        return ((HomeActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onListLoaded(String jsonResult) {
        try {
            if (jsonResult != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(jsonResult);
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("wall");
                    for (int i = 0; i < jsonMainNode.length(); i++) {
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                        items.add(new WallpaperItem(jsonChildNode.optString("name"),
                                jsonChildNode.optString("author"),
                                jsonChildNode.optString("url"),
                                jsonChildNode.optString("thumb")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView gridview = (RecyclerView) mainView.findViewById(R.id.wall_rv);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        (mainView.findViewById(R.id.progressBar_wall)).setVisibility(View.GONE);
        int numOfRows = (int) (size.x / getResources().getDimension(R.dimen.size_of_grid_item));
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        gridview.setLayoutManager(layoutManager);
        gridview.setHasFixedSize(true);
        gridview.addItemDecoration(new SpacesItemDecoration(8, 2));
        adapter = new WallAdapter(context, items);
        gridview.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == WallAdapter.REQUEST_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Storage permission has been granted
                adapter.storageRequestAccepted();
            } else {
                //Storage permission has been denied
            }
        }
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }
}