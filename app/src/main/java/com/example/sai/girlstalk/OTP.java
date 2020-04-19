package com.example.sai.girlstalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.loadingview.LoadingDialog;
import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


public class OTP extends AppCompatActivity {

    private static final String TAG = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private TextView waitTxt;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private TextView t1;
    private ImageView i1,i2;
    private LoadingDialog dialog;
    private Animation bounceanime;
    private EditText e1;
    private Button b1;
    private boolean isFirstRun=true;
    private TextView exampleTxt;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        // getting first run boolean value(false) after 1st run
        SharedPreferences getpref = getSharedPreferences("FIRST_RUN",MODE_PRIVATE);
        isFirstRun = getpref.getBoolean("checkrunstatus",true);

        if(!isFirstRun){  // if its not the 1st run
            Intent intent = new Intent(OTP.this,login1.class);
            startActivity(intent);
            finish();
        }
        exampleTxt = findViewById(R.id.exampletxt);
        e1 =  findViewById(R.id.Phonenoedittext);
        b1 = findViewById(R.id.PhoneVerify);
        t1 = findViewById(R.id.textView2Phone);
        i1 = findViewById(R.id.imageView2Phone);
        i2 = findViewById(R.id.logo);
        waitTxt = findViewById(R.id.waittxt);
        bounceanime = AnimationUtils.loadAnimation(this,R.anim.bounce);
        BounceInterpolar interpolator = new BounceInterpolar(0.2, 20);
        bounceanime.setInterpolator(interpolator);
        i2.startAnimation(bounceanime);
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2.startAnimation(bounceanime);
            }
        });

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                Toast.makeText(OTP.this,"Verification Complete",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTP.this,"Verification Failed! Try again..",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(OTP.this,"InValid Phone Number",Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTP.this, "Something went wong...please try again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(OTP.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                e1.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);  // making some elements as invisible and some visible
                i1.setVisibility(View.GONE);
                i2.setVisibility(View.GONE);
                exampleTxt.setVisibility(View.GONE);
                waitTxt.setVisibility(View.VISIBLE);
                dialog= LoadingDialog.Companion.get(OTP.this).show();

            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(e1.getText().toString().isEmpty()){
                    Toast.makeText(OTP.this, "Enter phone number!", Toast.LENGTH_SHORT).show();
                }

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            e1.getText().toString(),
                            60,
                            java.util.concurrent.TimeUnit.SECONDS,
                            OTP.this,
                            mCallbacks);   // this wil verify phone number


            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(OTP.this,login1.class));
                            Toast.makeText(OTP.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            dialog.hide();


                            // saving status of first run as false if the task is successful.
                            preferences = getSharedPreferences("FIRST_RUN",MODE_PRIVATE);
                            isFirstRun = false;
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("checkrunstatus",isFirstRun);
                            editor.apply();

                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(OTP.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                                dialog.hide();
                                e1.setVisibility(View.VISIBLE);
                                b1.setVisibility(View.VISIBLE);
                                t1.setVisibility(View.VISIBLE);
                                i1.setVisibility(View.VISIBLE);
                                i2.setVisibility(View.VISIBLE);
                                waitTxt.setVisibility(View.GONE);

                            }
                        }
                    }
                });
    }
}
