package com.payzout.business.profile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.payzout.business.R;
import com.payzout.business.apis.APIClient;
import com.payzout.business.apis.KYCInterface;
import com.payzout.business.auth.PhoneActivity;
import com.payzout.business.common.MainActivity;
import com.payzout.business.utils.DatePickerFragment;
import com.payzout.business.utils.FirestoreConstant;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KycActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private MaterialCardView cardKYC;

    private TextInputLayout tiFullName;
    private TextInputEditText etFullName;

    private TextInputLayout tiEmail;
    private TextInputEditText etEmail;

    private TextInputLayout tiPanNumber;
    private TextInputEditText etPanNumber;

    private TextInputLayout tiAadhaarNumber;
    private TextInputEditText etAadhaarNumber;

    private TextInputLayout tiDateOfBirth;
    private TextInputEditText etDateOfBirth;

    private TextInputLayout tiGender;
    private AutoCompleteTextView etGender;
    private String[] GENDER = {"Male", "Female", "Other"};

    private TextInputLayout tiCompleteAddress;
    private TextInputEditText etCompleteAddress;

    private TextView tvUpdateDetails;
    private TextView tvName;
    private TextView tvMobile;
    private ImageView ivKycStatus;
    private ImageView ivGoBack;
    private ImageView ivUserPhoto;
    private TextView tvChangePhoto;
    private ProgressBar progressPhoto;
    private ImageView ivLogout;

    private boolean fullNameStatus = false;
    private boolean emailStatus = false;
    private boolean panNumberStatus = false;
    private boolean aadhaarStatus = false;
    private boolean dateOfBirthStatus = false;
    private boolean genderStatus = false;
    private boolean completeAddressStatus = false;

    private boolean kycEmailStatus = false;
    private boolean kycPANStatus = false;
    private boolean kycAadhaarStatus = false;
    private boolean kycDOBStatus = false;
    private boolean kycADDRESSStatus = false;


    private static final String TAG = "KycActivity";

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        initView();
    }

    private void initView() {

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ivUserPhoto = findViewById(R.id.ivUserPhoto);
        progressPhoto = findViewById(R.id.progressPhoto);

        ivLogout = findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(this);

        cardKYC = findViewById(R.id.cardKYC);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        tvChangePhoto.setOnClickListener(this);
        cardKYC.setOnClickListener(this);

        ivKycStatus = findViewById(R.id.ivKycStatus);
        ivGoBack = findViewById(R.id.ivGoBack);
        ivGoBack.setOnClickListener(this);

        tiFullName = findViewById(R.id.tiFullName);
        etFullName = findViewById(R.id.etFullName);
        etFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = etFullName.getText().toString();
                if (name.isEmpty()) {
                    fullNameStatus = false;
                } else {
                    fullNameStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiEmail = findViewById(R.id.tiEmail);
        etEmail = findViewById(R.id.etEmail);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = etEmail.getText().toString();
                if (!email.isEmpty()) {
                    if (emailValidation(email)) {
                        emailStatus = true;
                    } else {
                        emailStatus = false;
                    }
                    checkForValidation();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiPanNumber = findViewById(R.id.tiPanNumber);
        etPanNumber = findViewById(R.id.etPanNumber);
        etPanNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pancard = etPanNumber.getText().toString();
                if (pancard.length() == 10) {
                    if (panValidation(pancard)) {
                        panNumberStatus = true;
                    } else {
                        panNumberStatus = false;
                    }
                    checkForValidation();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiAadhaarNumber = findViewById(R.id.tiAadhaarNumber);
        etAadhaarNumber = findViewById(R.id.etAadhaarNumber);
        etAadhaarNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String aadhaar = etAadhaarNumber.getText().toString();
                if (aadhaar.length() == 12) {
                    if (aadhaarValidation(aadhaar)) {
                        aadhaarStatus = true;
                    } else {
                        aadhaarStatus = false;
                    }
                    checkForValidation();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiDateOfBirth = findViewById(R.id.tiDateOfBirth);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etDateOfBirth.setOnClickListener(this);
        etDateOfBirth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String dob = etDateOfBirth.getText().toString();
                if (dob.isEmpty()) {
                    dateOfBirthStatus = false;
                } else {
                    dateOfBirthStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiGender = findViewById(R.id.tiGender);
        etGender = findViewById(R.id.etGender);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(KycActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                GENDER);
        etGender.setAdapter(adapter);
        etGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String gender = etGender.getText().toString();
                if (gender.isEmpty()) {
                    genderStatus = false;
                } else {
                    genderStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tiCompleteAddress = findViewById(R.id.tiCompleteAddress);
        etCompleteAddress = findViewById(R.id.etCompleteAddress);
        etCompleteAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String address = etCompleteAddress.getText().toString();
                if (address.isEmpty()) {
                    completeAddressStatus = false;
                } else {
                    completeAddressStatus = true;
                }
                checkForValidation();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tvUpdateDetails = findViewById(R.id.tvUpdateDetails);
        tvUpdateDetails.setOnClickListener(this);

        tvName = findViewById(R.id.tvName);
        tvMobile = findViewById(R.id.tvMobile);

        setProfile();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == cardKYC) {
            showDialog();
        }

        if (view == tvUpdateDetails) {
            doShowConfirmationDialog();
        }

        if (view == ivGoBack) {
            onBackPressed();
        }

        if (view == tvChangePhoto) {
            showGalleryAndSelectImage();
        }

        if (view == ivLogout) {
            showLogoutDialog();
        }

        if (view == etDateOfBirth) {
            showDOBDialog();
        }
    }

    private void showDOBDialog() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    private void showLogoutDialog() {
        new MaterialAlertDialogBuilder(KycActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure, want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doLogout();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void doLogout() {
        SharedPreferences preferences = getSharedPreferences("profile", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        Intent intent = new Intent(KycActivity.this, PhoneActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showGalleryAndSelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Photo"),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                progressPhoto.setVisibility(View.VISIBLE);
                uploadPhoto();
            } catch (IOException e) {
                e.printStackTrace();
                progressPhoto.setVisibility(View.GONE);
            }
        }

    }

    private void uploadPhoto() {
        if (filePath != null) {
            final StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            imageURL = task.getResult().toString();
                                            Glide.with(KycActivity.this).load(task.getResult().toString()).into(ivUserPhoto);
                                            progressPhoto.setVisibility(View.GONE);
                                            HashMap hashMap = new HashMap();
                                            hashMap.put("photo", imageURL);
                                            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                            String uid = firebaseAuth.getCurrentUser().getUid();
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                                                    .document(uid)
                                                    .update(hashMap)
                                                    .addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()) {
                                                                SharedPreferences preferences = getSharedPreferences("profile", 0);
                                                                SharedPreferences.Editor editor = preferences.edit();
                                                                editor.putString("photo", imageURL);
                                                                editor.apply();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressPhoto.setVisibility(View.GONE);
                    Toast.makeText(KycActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void doShowConfirmationDialog() {
        new MaterialAlertDialogBuilder(KycActivity.this)
                .setTitle("Update Confirmation")
                .setMessage("Are you sure, want to update KYC details, you cannot modify these details once you update.")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        doUpdateProfile();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void showDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        View deleteDialogView = factory.inflate(R.layout.kyc_alert_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.tvScanQR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KycActivity.this, QRActivity.class);
                startActivity(intent);
                deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    private boolean emailValidation(String email) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    private boolean panValidation(String pan) {
        String panCard = etPanNumber.getText().toString();
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(panCard);
        if (!matcher.matches() || panCard.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean aadhaarValidation(String aadhaar) {
        String aadhaarNo = etAadhaarNumber.getText().toString();
        String regex = "^[2-9]{1}[0-9]{3}[0-9]{4}[0-9]{4}$";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(aadhaarNo);
        if (!matcher.matches() || aadhaarNo.isEmpty()) {
            return false;
        }
        return true;
    }

    private void checkForValidation() {
        Log.e(TAG, "checkForValidation: " + fullNameStatus + " " + emailStatus + " " + panNumberStatus + " " + aadhaarStatus + " " + dateOfBirthStatus + " " + genderStatus + " " + completeAddressStatus);
        if (fullNameStatus && emailStatus && panNumberStatus && aadhaarStatus && dateOfBirthStatus && genderStatus && completeAddressStatus) {
            tvUpdateDetails.setEnabled(true);
            tvUpdateDetails.setBackground(getResources().getDrawable(R.drawable.bg_button));
            tvUpdateDetails.setTextColor(getResources().getColor(R.color.colorTextH3));
        } else {
            tvUpdateDetails.setEnabled(false);
            tvUpdateDetails.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
            tvUpdateDetails.setTextColor(getResources().getColor(R.color.colorTextH2));
        }
    }

    private void checkKYCValidation() {
        Log.e(TAG, "checkKYCValidation: " + kycEmailStatus + " " + kycPANStatus + " " + kycAadhaarStatus + " " + kycDOBStatus + " " + kycADDRESSStatus + " ");

        if (kycEmailStatus && kycPANStatus && kycAadhaarStatus && kycAadhaarStatus && kycDOBStatus && kycADDRESSStatus) {

            tvUpdateDetails.setEnabled(false);
            tvUpdateDetails.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
            tvUpdateDetails.setTextColor(getResources().getColor(R.color.colorTextH2));
        } else {
            tvUpdateDetails.setEnabled(true);
            tvUpdateDetails.setBackground(getResources().getDrawable(R.drawable.bg_button));
            tvUpdateDetails.setTextColor(getResources().getColor(R.color.colorTextH3));

        }
    }

    private void doUpdateProfile() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String photo = imageURL;
        String uid = firebaseAuth.getCurrentUser().getUid();
        String name = etFullName.getText().toString();
        String email = etEmail.getText().toString();
        String pancard = etPanNumber.getText().toString();
        String aadhaar = etAadhaarNumber.getText().toString();
        String dob = etDateOfBirth.getText().toString();
        String gender = etGender.getText().toString();
        String address = etCompleteAddress.getText().toString();
        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
        String phoneTrim = phone.replace("+91", "");
        int gen = 0;

        Profile profile = new Profile();
        profile.setUid(uid);
        profile.setName(name);
        profile.setPhoto(imageURL);
        profile.setEmail(email);
        profile.setPhone(phoneTrim);
        profile.setPancard(pancard);
        profile.setAadhaar(aadhaar);
        profile.setDob(dob);
        profile.setGender(gender);
        if (gender.equals("Male")) {
            gen = 1;
        } else if (gender.equals("Female")) {
            gen = 2;
        } else if (gender.equals("Other")) {
            gen = 0;
        }

        profile.setAddress(address);

        disableUI();
        saveToPreference(uid, name, photo, email, phoneTrim, pancard, aadhaar, dob, gender, address);
        tvName.setText(name);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirestoreConstant.INVESTORS_COLLECTION)
                .document(uid)
                .set(profile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            tvUpdateDetails.setVisibility(View.GONE);
                        }
                    }
                });

        KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
        Call<KYCResponse> call = kycInterface.sendKYCDetails(uid, email, gen, dob, pancard, aadhaar, name, address);
        call.enqueue(new Callback<KYCResponse>() {
            @Override
            public void onResponse(Call<KYCResponse> call, Response<KYCResponse> response) {
                Log.e(TAG, "onResponse: " + response.message() + response.code());
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: DATA ADDED");
                    startActivity(new Intent(KycActivity.this, MainActivity.class));
                    finish();
                } else if (response.code() == 400) {
                    Log.e(TAG, "onResponse: This User Is Already Exists");
                } else {
                    Log.e(TAG, "onResponse: Something Wnt Wrong");
                }
            }

            @Override
            public void onFailure(Call<KYCResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void disableUI() {
        tvUpdateDetails.setEnabled(false);
        tvUpdateDetails.setBackground(getResources().getDrawable(R.drawable.bg_text_box));
        tvUpdateDetails.setTextColor(getResources().getColor(R.color.colorTextH2));
        etFullName.setEnabled(false);
        etEmail.setEnabled(false);
        etPanNumber.setEnabled(false);
        etAadhaarNumber.setEnabled(false);
        etDateOfBirth.setEnabled(false);
        etGender.setEnabled(false);
        etCompleteAddress.setEnabled(false);
    }

    private void saveToPreference(String uid, String name, String photo, String email, String phone, String pancard, String aadhaar, String dob, String gender, String address) {
        SharedPreferences preferences = getSharedPreferences("profile", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", uid);
        editor.putString("name", name);
        editor.putString("photo", photo);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("pancard", pancard);
        editor.putString("aadhaar", aadhaar);
        editor.putString("dob", dob);
        editor.putString("gender", gender);
        editor.putString("address", address);
        editor.apply();
    }

    private void setProfile() {

        SharedPreferences preferences = getSharedPreferences("profile", 0);
        final String name = preferences.getString("name", "Complete KYC");
        if (name.equals("Complete KYC")) {
            ivKycStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_error_24));
            tvUpdateDetails.setVisibility(View.VISIBLE);
        } else {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            String uid = firebaseAuth.getCurrentUser().getUid();
//            ivKycStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_check_circle_24));
//            tvUpdateDetails.setVisibility(View.GONE);
//            etFullName.setText(preferences.getString("name", ""));
//            etEmail.setText(preferences.getString("email", ""));
//            etPanNumber.setText(preferences.getString("pancard", ""));
//            etAadhaarNumber.setText(preferences.getString("aadhaar", ""));
//            etDateOfBirth.setText(preferences.getString("dob", ""));
//            etGender.setText(preferences.getString("gender", ""));
//            etCompleteAddress.setText(preferences.getString("address", ""));

            KYCInterface kycInterface = APIClient.getRetrofitInstance().create(KYCInterface.class);
            Call<GetProfile> call = kycInterface.getProfile(uid);
            call.enqueue(new Callback<GetProfile>() {
                @Override
                public void onResponse(Call<GetProfile> call, Response<GetProfile> response) {
                    Log.e(TAG, "onResponse: " + response.code() + response.message());

                    String email = response.body().getGetProfileResponse().getEmail();

                    String panNumber = response.body().getGetProfileResponse().getPancard();
                    String aadhaar = response.body().getGetProfileResponse().getAadhaar();
                    String DOB = response.body().getGetProfileResponse().getDob();
                    String address = response.body().getGetProfileResponse().getAddress();

                    Log.e(TAG, "onResponse: " + email + panNumber + aadhaar + DOB + address);
                    if (response.code() == 200) {
                        etFullName.setText("" + response.body().getGetProfileResponse().getFullName());
                        etEmail.setText(email);
                        etPanNumber.setText(panNumber);
                        etAadhaarNumber.setText(aadhaar);
                        etDateOfBirth.setText(DOB);
                        etCompleteAddress.setText(address);
                        if (!name.isEmpty() && !email.isEmpty() && !panNumber.isEmpty() && !aadhaar.isEmpty() && !DOB.isEmpty() && !address.isEmpty()) {
                            disableUI();
                        }
                        Log.e(TAG, "onResponseAGENDER: " + response.body().getGetProfileResponse().getGender() + "");
                        if (response.body().getGetProfileResponse().getGender() == 1) {
                            etGender.setText("Male");
                        } else if (response.body().getGetProfileResponse().getGender() == 2) {
                            etGender.setText("Female");
                        }
//                        disableUI();

                    } else if (response.code() == 400) {
                        Log.e(TAG, "onResponse: user not found");
                    } else {
                        Log.e(TAG, "onResponse: Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetProfile> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        }
        Glide.with(KycActivity.this).load(preferences.getString("photo", "")).placeholder(R.drawable.ic_baseline_account_circle_24).into(ivUserPhoto);
        tvName.setText(name);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
        String phoneTrim = phone.replace("+91", "");
        tvMobile.setText(phoneTrim);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        int months = month + 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = year + "-" + months + "-" + dayOfMonth;
        etDateOfBirth.setText(currentDateString);
    }
}