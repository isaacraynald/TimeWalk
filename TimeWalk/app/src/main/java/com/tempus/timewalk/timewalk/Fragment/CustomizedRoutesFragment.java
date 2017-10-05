package com.tempus.timewalk.timewalk.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.tempus.timewalk.timewalk.Activity.NavigationDrawer;
import com.tempus.timewalk.timewalk.R;

/**
 * A {@link Fragment} subclass that display the customized route screen.
 *
 */
public class CustomizedRoutesFragment extends Fragment {

    ArrayList<String> selectedItems = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CustomizedRoutesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomizedRoutesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomizedRoutesFragment newInstance(String param1, String param2) {
        CustomizedRoutesFragment fragment = new CustomizedRoutesFragment();
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
        ((NavigationDrawer)getActivity()).changeDrawerItem(R.id.nav_customized);
        //((NavigationDrawer)getActivity()).hideFloatingActionButton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_customized_routes, container, false);

        ListView chl = (ListView)view.findViewById(R.id.checkable_list);

        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        String[] items = {"Cavill Avenue","Story Bridge","Overlook Hotel"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getActivity(),R.layout.row_layout, R.id.txt_lan, items);

        chl.setAdapter(adapter);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if(selectedItems.contains(selectedItem)){
                    selectedItems.remove(selectedItem); //uncheck item
                }
                else {
                    selectedItems.add(selectedItem);
                }
            }
        });


        return view;
    }

    public void showSelectedItems (View view){
        String items = "";
        for(String item:selectedItems){
            items += "-" + item + "\n";
        }
        Toast.makeText(this.getActivity(), "You have selected \n" + items,Toast.LENGTH_LONG).show();

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**
     * @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
     */

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
