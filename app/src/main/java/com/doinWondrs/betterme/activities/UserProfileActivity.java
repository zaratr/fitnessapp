package com.doinWondrs.betterme.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.ActivityEnum;
import com.amplifyframework.datastore.generated.model.User;
import com.doinWondrs.betterme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private String s3ImageKey = "";
    private CompletableFuture<User> userFuture;
    private User userInfo;
    private String userEmail = null;
    private String userNickName = null;
    private TextView usernameView;
    private EditText profileAgeInput;
    private EditText profileHeightInput;
    private EditText profileTargetWeightInput;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private Spinner activitySpinner = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activityResultLauncher = getImagePickingActivityResultLauncher();

        navGoTo();//Bottom NAVBAR
        getUserAttributes();
        getUser();
        fillUser();
        getS3ImageKey();
        setUpAddImageButton();
        setUpSpinner();
        setUpInfo();
        setUpUpdateBtn();
        setUpLogout();
        setUpAboutUs();
    }//END onCreate

    // Makes the Profile Image clickable to upload profile pic
    private void setUpAddImageButton(){

        ImageView addImageButton = findViewById(R.id.userProfileImg);
        addImageButton.setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
    }

    private void setUpSpinner() {
        activitySpinner = findViewById(R.id.profileActivityLevelSpinner);
        ArrayAdapter<ActivityEnum> enums = new ArrayAdapter<>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ActivityEnum.values());
        activitySpinner.setAdapter(enums);
        int actLevelPosition = enums.getPosition(userInfo.getActivityLevel());
        activitySpinner.setSelection(actLevelPosition);
    }

    private void getUserAttributes(){
        Amplify.Auth.fetchUserAttributes(
                success -> {
                    for(AuthUserAttribute attribute : success){
                        if(attribute.getKey().getKeyString().equals("email")){
                            userEmail = attribute.getValue();
                        }
                        if(attribute.getKey().getKeyString().equals("nickname")){
                            userNickName = attribute.getValue();
                        }
                    }
                },
                error -> Log.e(TAG, "failed")
        );
    }

    private void getUser(){
        userFuture = new CompletableFuture<>();
        Amplify.API.query(
                ModelQuery.list(User.class),
                success -> {
                    Log.i(TAG, "Read Users successfully");
                    for(User user : success.getData()){
                        if(user.getUsername().equals(userNickName)){
                            userFuture.complete(user);
                        }
                    }
                },
                failure -> Log.i(TAG,"Failed to read Users")
        );
    }

    private void fillUser(){
        try {
            userInfo = userFuture.get();
        } catch (InterruptedException ie) {
            Log.e(TAG, "InterruptedException while getting product");
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            Log.e(TAG, "ExecutionException while getting product");
        }
    }

    private void setUpInfo(){
         EditText profileEmailInput = findViewById(R.id.profileEmailInput);
         usernameView = findViewById(R.id.profileUsernameDisplay);
         profileAgeInput = findViewById(R.id.profileAgeInput);
         profileHeightInput = findViewById(R.id.profileHeightInput);
         profileTargetWeightInput = findViewById(R.id.profileTargetWeightInput);

        profileEmailInput.setText(userEmail);
        usernameView.setText(String.valueOf(userInfo.getUsername()));
        if(userInfo.getAge() != null){
                profileAgeInput.setText(String.valueOf(userInfo.getAge()));
        }else{
                profileAgeInput.setText("N/A");
        }
        if(userInfo.getHeight() != null){
            profileHeightInput.setText(String.valueOf(userInfo.getHeight()));
        }else{
            profileHeightInput.setText("N/A");
        }
        if(userInfo.getTargetWeight() != null){
            profileTargetWeightInput.setText(String.valueOf(userInfo.getTargetWeight()));
        }else{
            profileTargetWeightInput.setText("N/A");
        }
    }

    private void setUpUpdateBtn(){
        Button updateBtn = findViewById(R.id.updateUserInfoBtn);
        updateBtn.setOnClickListener(v -> {
        Spinner profileActSpin = findViewById(R.id.profileActivityLevelSpinner);
        ActivityEnum actlevel = (ActivityEnum) profileActSpin.getSelectedItem();
            User userToUpdate = User.builder()
                    .id(userInfo.getId())
                    .username(userInfo.getUsername())
                    .age(Integer.parseInt(profileAgeInput.getText().toString()))
                    .height(Integer.parseInt(profileHeightInput.getText().toString()))
                    .targetWeight(Integer.parseInt(profileTargetWeightInput.getText().toString()))
                    .activityLevel(actlevel)
                    .profileImgKey(s3ImageKey)
                    .build();

        Amplify.API.mutate(
                ModelMutation.update(userToUpdate),
                successResponse -> Log.i(TAG, "Updated User successfully"),
                failureResponse -> Log.i(TAG, "Update failed with this response: " + failureResponse)
        );

        Toast.makeText(UserProfileActivity.this, "User Updated", Toast.LENGTH_SHORT).show();
        });
    }


    // Grabs s3Image key from current user
    private void getS3ImageKey(){
        s3ImageKey = userInfo.getProfileImgKey();
        if (s3ImageKey != null && !s3ImageKey.isEmpty()){
            Amplify.Storage.downloadFile(
                    s3ImageKey,
                    new File(getApplication().getFilesDir(), s3ImageKey),
                    success -> {

                        ImageView profileImage = findViewById(R.id.userProfileImg);
                        profileImage.setImageBitmap(BitmapFactory.decodeFile(success.getFile().getPath()));

                    },
                    failure -> Log.e(TAG, "Unable to get image from S3 for the task with s3 key: " + s3ImageKey + "with error: " + failure.getMessage(), failure)
            );
        }
    }

    // Launches image selector
    //TODO: Inform User only PNG and JPEG work
    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg","image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    // Grabs input stream from selected file on our phone and calls to upload to S3
    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Uri pickedImageUri = result.getData().getData();
                    try{
                        InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageUri);
                        String pickedImageFileName = getFileNameFromUri(pickedImageUri);
                        uploadInputStreamToS3(pickedImageInputStream, pickedImageFileName, pickedImageUri);
                        Log.i(TAG, "Succeeded in getting input stream from a file on our phone");
                    } catch (FileNotFoundException fnfe){
                        Log.e(TAG, "Could not get file from phone: " + fnfe.getMessage(), fnfe);
                    }
                }
        );
    }

    // Uploads image to S3 buckets and sets the image immediately as user profile image
    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFileName, Uri pickedImageUri){
        Amplify.Storage.uploadInputStream(
                pickedImageFileName,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in uploading file to s3: " + success.getKey());
                    s3ImageKey = success.getKey();

                    ImageView profileImg = findViewById(R.id.userProfileImg);

                    InputStream pickedImageInputStreamCopy = null;
                    try{
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageUri);
                    } catch(FileNotFoundException fnfe){
                        Log.e(TAG, "Could not get input stream from uri: " + fnfe.getMessage(), fnfe);
                    }
                    profileImg.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFileName + " with error: " + failure.getMessage())
        );
    }

    // Taken from https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                assert cursor != null;
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Attaches onClick listener to logout container. Clears backstack and starts loginpage activity
    private void setUpLogout(){
        ConstraintLayout logout = findViewById(R.id.profileLogoutContainer);
        logout.setOnClickListener(v -> {
            Amplify.Auth.signOut(
                    () ->
                    {
                        Log.i(TAG, "Logout succeeded!");
                    },
                    failure ->
                    {
                        Log.i(TAG, "Logout failed: " + failure);
                    }
            );
            Toast.makeText(UserProfileActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            Intent goToLoginIntent = new Intent(UserProfileActivity.this, LogInActivity.class);
            goToLoginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(goToLoginIntent);
        });
    }//END: setupLogout

    private void setUpAboutUs(){
        ConstraintLayout aboutUs = findViewById(R.id.profileAboutUsContainer);
        aboutUs.setOnClickListener(v ->{
            Intent goToAboutUs = new Intent(UserProfileActivity.this, AboutUsActivity.class);
            startActivity(goToAboutUs);
        });
    }

    // TODO: Setup Intent to go to AboutUs Activity

    public void navGoTo()
    {
        //notes: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        //TODO: bottomnavbar is deprecated: https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView.OnNavigationItemSelectedListener

        //initialize, instantiate
        NavigationBarView navigationBarView;//new way to do nav's but more research needed
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected: home
        bottomNavigationView.setSelectedItemId(R.id.settings_nav);
        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.calendar_nav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.gps_nav:
                        startActivity(new Intent(getApplicationContext(), GPSActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.workouts_nav:
                        startActivity(new Intent(getApplicationContext(), WorkoutPageFirst.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.settings_nav:
                        //we are here right now
                        break;
                    default: return false;// this is to cover all other cases if not working properly
                }

                return true;
            }
        });//end lambda: bottomNavview
    }//end method: navGoTo

}//END: Class