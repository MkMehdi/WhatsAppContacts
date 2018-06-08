package ma.mkmehdi.whatsappcontacts.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ma.mkmehdi.whatsappcontacts.model.Contact;

/**
 * Created by Elmehdi Mellouk on 06/06/18.
 * mellouk.elmehdi@gmail.com
 */
public class ContactListViewModel extends AndroidViewModel {

    private ContentResolver mContentResolver;
    // MediatorLiveData can observe other LiveData objects and react on their emissions.

    public ContactListViewModel(@NonNull Application application, ContentResolver contactResolver) {
        super(application);
        mContentResolver = contactResolver;
    }

    private Cursor getRawContacts() {
        //RowContacts for filter Account Types
        return mContentResolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID,
                        ContactsContract.RawContacts.CONTACT_ID},
                ContactsContract.RawContacts.ACCOUNT_TYPE + "= ?",
                new String[]{"com.whatsapp"},
                null);
    }

    public List<Contact> collectContacts() {
        List<Contact> contactList = new ArrayList<>();

        Cursor contactCursor = getRawContacts();

        if (contactCursor != null) {
            if (contactCursor.getCount() > 0) {
                if (contactCursor.moveToFirst()) {
                    do {
                        //whatsappContactId for get Number,Name,Id ect... from  ContactsContract.CommonDataKinds.Phone
                        String whatsappContactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID));

                        if (whatsappContactId != null) {
                            //Get Data from ContactsContract.CommonDataKinds.Phone of Specific CONTACT_ID

                            Cursor whatsAppContactCursor = getWhatsAppCursor(whatsappContactId);

                            if (whatsAppContactCursor != null) {
                                whatsAppContactCursor.moveToFirst();
                                String id = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                                String name = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                String number = whatsAppContactCursor.getString(whatsAppContactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                whatsAppContactCursor.close();

                                //Add Number to ArrayList
                                contactList.add(new Contact(id, name, number));


                               /* showLogI(TAG, " WhatsApp contact id  :  " + id);
                                showLogI(TAG, " WhatsApp contact name :  " + name);
                                showLogI(TAG, " WhatsApp contact number :  " + number);*/
                            }
                        }
                    } while (contactCursor.moveToNext());

                    contactCursor.close();
                }
            }
        }

        return contactList;
    }

    private Cursor getWhatsAppCursor(String whatsappContactId) {
        return mContentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{whatsappContactId}, null);
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private ContentResolver mContentResolver;


        public Factory(@NonNull Application application) {
            mApplication = application;
            //This class provides applications access to the content model.
            mContentResolver = mApplication.getContentResolver();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ContactListViewModel(mApplication, mContentResolver);
        }
    }
}
