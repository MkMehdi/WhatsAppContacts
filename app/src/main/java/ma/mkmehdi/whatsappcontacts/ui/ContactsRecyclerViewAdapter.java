package ma.mkmehdi.whatsappcontacts.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ma.mkmehdi.whatsappcontacts.R;
import ma.mkmehdi.whatsappcontacts.model.Contact;

/**
 * Created by Elmehdi Mellouk on 06/06/18.
 * mellouk.elmehdi@gmail.com
 */
public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    // private Context mContext;

    public ContactsRecyclerViewAdapter(List<Contact> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_contact, parent, false);
        //  mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textName.setText(holder.mItem.getName());
        holder.textPhoneNumber.setText(holder.mItem.getNumberPhone());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textPhoneNumber;

        private Contact mItem;

        private ViewHolder(View view) {
            super(view);

            textName = view.findViewById(R.id.textName);
            textPhoneNumber = view.findViewById(R.id.textPhoneNumber);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textName.getText() + "'";
        }
    }
}
