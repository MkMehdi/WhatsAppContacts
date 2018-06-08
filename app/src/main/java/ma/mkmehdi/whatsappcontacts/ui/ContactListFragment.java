package ma.mkmehdi.whatsappcontacts.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ma.mkmehdi.whatsappcontacts.R;
import ma.mkmehdi.whatsappcontacts.model.Contact;
import ma.mkmehdi.whatsappcontacts.viewmodel.ContactListViewModel;


public class ContactListFragment extends Fragment {
    public static final String TAG = "ContactListFragment";
    private ContactListViewModel model;
    private List<Contact> mWhatsAppContactList = new ArrayList<>();

    private RecyclerView mRecyclerView;

    public ContactListFragment() {
        // Required empty public constructor
    }


    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void init() {
        ContactListViewModel.Factory factory = new ContactListViewModel.Factory(
                getActivity().getApplication());

         model = ViewModelProviders.of(this, factory)
                .get(ContactListViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        mRecyclerView = view.findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        showWhatsAppContacts();
    }

    public void showWhatsAppContacts() {

        mWhatsAppContactList = model.collectContacts();

        ContactsRecyclerViewAdapter adapter = new ContactsRecyclerViewAdapter(mWhatsAppContactList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
