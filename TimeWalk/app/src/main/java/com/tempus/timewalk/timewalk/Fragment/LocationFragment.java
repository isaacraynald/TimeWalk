package com.tempus.timewalk.timewalk.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tempus.timewalk.timewalk.AppConfig;
import com.tempus.timewalk.timewalk.Classes.ImageListener;
import com.tempus.timewalk.timewalk.Classes.ImagesOperations;
import com.tempus.timewalk.timewalk.GalleryView.SliderAdapter;
import com.tempus.timewalk.timewalk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Fragment} subclass that display the landmark details screen.
 */
public class LocationFragment extends Fragment implements ImageListener{

    /**
     * Variables
     */
    SliderAdapter sAdapter;
    ViewPager viewPager;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Empty Constructor
     */
    public LocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationFragment newInstance(String param1, String param2) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the
     *                 fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to.
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     * @return A new instance of fragment HomeFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.getActivity();
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        //locationImg = {R.drawable.location_image01, R.drawable.location_image02, R.drawable.location_image03};


        String id = getArguments().getString("ID");
        String name = getArguments().getString("Name");
        String description = getArguments().getString("Description");

        TextView txtName = (TextView)view.findViewById(R.id.location_name);
        txtName.setText(name);

        TextView txtDesc = (TextView)view.findViewById(R.id.location_desc);
        txtDesc.setText(description);

        new ImagesOperations(getActivity().getApplicationContext(), this).execute(AppConfig.URL_SERVER + "getImages.php",id);

        String[] locationImage = {"TEST"};
        String[] captionList = {"Sea Breeze, Taken on January 12, 1961", "Taken on January 29, 1963", "Taken in August 1960"};

        //setImages(str);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        //sAdapter = new SliderAdapter(this.getActivity(), locationImage, captionList);

        //viewPager.setAdapter(sAdapter);
        return view;
    }

    @Override
    public void setImages(String str) {
        List<String> listLocation = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        List<String> listCaption = new ArrayList<>();
        try {
            JSONObject jsonData = new JSONObject(str);
            if (jsonData.getInt("success") == 1) {
                JSONArray jsonArray = jsonData.getJSONArray("DataImages");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listLocation.add(jsonObject.getString("Images"));
                    listName.add(jsonObject.getString("Name"));
                    listCaption.add(jsonObject.getString("Description"));

                }
                String[] listLink  = new String[listLocation.size()];
                String[] listNames  = new String[listName.size()];
                String[] listDesc = new String[listCaption.size()];
                listLocation.toArray(listLink);
                listName.toArray(listNames);
                listCaption.toArray(listDesc);
                sAdapter = new SliderAdapter(this.getActivity(), listLink, listNames, listDesc);
                viewPager.setAdapter(sAdapter);

            }




            } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
