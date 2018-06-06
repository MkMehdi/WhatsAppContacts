package ma.mkmehdi.whatsappcontacts.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import ma.mkmehdi.whatsappcontacts.R;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class WAContactsActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String[] CONTACTS =
            {Manifest.permission.READ_CONTACTS};

    private static final int RC_CONTACTS_PERM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wa_contacts);

        if (hasWifiPermission()) {
            redirectContactList();
        }else{
            EasyPermissions.requestPermissions(
                    this,getString(R.string.rationale_contacts),
                    RC_CONTACTS_PERM,CONTACTS);
        }
    }

    @AfterPermissionGranted(RC_CONTACTS_PERM)
    private void redirectContactList(){
            ContactListFragment fragment = new ContactListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, fragment, ContactListFragment.TAG).commitAllowingStateLoss();
    }


    private boolean hasWifiPermission() {
        return EasyPermissions.hasPermissions(this, CONTACTS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        redirectContactList();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(android.R.string.yes);
            String no = getString(android.R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasWifiPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
