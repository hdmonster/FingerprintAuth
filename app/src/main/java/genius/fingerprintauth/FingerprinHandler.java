package genius.fingerprintauth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Haydar Dzaky S on 3/3/2018.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprinHandler extends FingerprintManager.AuthenticationCallback{

    private Context context;

    public  FingerprinHandler(Context context){

        this.context = context;

    }

    //Authenticate fingerprint
    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0,this,null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There wan an auth error." + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth failed.", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error:" + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("Access Granted", true);

    }

    private void update(String s, boolean b) {

        TextView txtBottom = (TextView) ((Activity)context).findViewById(R.id.txtBottom);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.imageFinger);

        txtBottom.setText(s);

        if(b == false){

            txtBottom.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));

        }else{
            txtBottom.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            imageView.setImageResource(R.mipmap.action_done);

            Intent intent = new Intent(context,ActivityWelcome.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }

    }
}
