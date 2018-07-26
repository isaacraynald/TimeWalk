package com.tempus.timewalk.timewalk.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tempus.timewalk.timewalk.Activity.NavigationDrawer;
import com.tempus.timewalk.timewalk.CardView.CardAdapter;
import com.tempus.timewalk.timewalk.Models.Information;
import com.tempus.timewalk.timewalk.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A {@link Fragment} subclass that display the recommended route screen.
 *
 */
public class RecommendedRoutesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Variables
     */
    private OnFragmentInteractionListener mListener;

    private List<Information> cardList;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;

    public RecommendedRoutesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendedRoutesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendedRoutesFragment newInstance(String param1, String param2) {
        RecommendedRoutesFragment fragment = new RecommendedRoutesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get string places upon instantiation from the RecommendedRoutesFragment
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((NavigationDrawer)getActivity()).changeDrawerItem(R.id.nav_recommended);
        //((NavigationDrawer)getActivity()).hideFloatingActionButton();

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
        View view = inflater.inflate(R.layout.fragment_recommended_routes, container, false);

        getActivity().setTitle("Recommended");

        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(getActivity(), cardList);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter);
        prepareContent();
        return view;
    }

    /**
     * Prepare the contents for the tour cards display.
     */
    private void prepareContent() {
        int[] images = new int[]{R.drawable.rfamily,R.drawable.rretro,R.drawable.rsport};
        String[] title = new String[]{"Family Walk", "Retro Tour", "Sports Tour"};
        String[] details = new String[]{"30-40m, 5 Destination", "40-50m, 6 Destination",
                "50-70m,4 Destination"};
        String[] description = new String[]{"Have some quality time with your family on this great, " +
                "family friendly route","Have you ever wondered what Brisbane looked like decades ago? " ,
                "Big fan of sports and cars? This route is the one for you!"};
        Information d;

        for(int i = 0; i < title.length; i++){
            d = new Information(title[i], details[i], description[i], images[i]);
            cardList.add(d);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /**@Override
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
