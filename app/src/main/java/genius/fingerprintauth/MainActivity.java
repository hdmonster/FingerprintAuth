package genius.fingerprintauth;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtTop, txtBottom;
    private ImageView imageFinger;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTop = findViewById(R.id.txtTop);
        txtBottom = findViewById(R.id.txtBottom);
        imageFinger = findViewById(R.id.imageFinger);

        //Android Version should equal or greater than Marsmallow
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);{

            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        }
        //check if device has Fingerprint Scanner
        if(!fingerprintManager.isHardwareDetected()){

            txtBottom.setText("No Fingerprint Scanner detected!");

        //check permission to use fingerprint sensor
        }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){

            txtBottom.setText("Access denied to use Fingerprint Scanner");

            //check if device is secure
        }else if(!keyguardManager.isKeyguardSecure()){

            txtBottom.setText("Lock your device with security");

            //check if atleast 1 fingerprint registered
        }else if(!fingerprintManager.hasEnrolledFingerprints()){

            txtBottom.setText("Register at least 1 fingerprint to use this feature");
        //if device has all of them
        }else{

            txtBottom.setText("Put your finger on scanner to proceed!");

            FingerprinHandler fingerprinHandler = new FingerprinHandler(this);
            fingerprinHandler.startAuth(fingerprintManager, null);

        }
    }
}
