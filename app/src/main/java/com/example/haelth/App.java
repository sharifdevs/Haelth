package com.example.haelth;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;

public class App extends Application {

    // General Used Enumerators
    public enum Style {PORTRAIT, LANDSCAPE}
    public enum JSONValueType {J_OBJ, J_ARR, J_STR, J_NUM, J_BOOL}
    public enum Gender {MALE, FEMALE}
    public enum AccountType {USER, GOOGLE, OFFLINE}

    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!

    /* Client used to interact with Google APIs. */
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInAccount googleSignInAccount;
    public static Patient patient = new Patient();
    public static AccountType accountType;
    public static boolean isLoggedIn = false;
    public static String reqJsonString = "";
    public static JSONObject userObj;
    public static JSONParser jsonParser;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        // Check if user is Already Signed In
        googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(googleSignInAccount != null) {
            String uid = "1589gs"; // = App.patient.getUserId();
            App.getPatientJSONObj("https://api.myjson.com/bins/" + uid);
            App.accountType = App.AccountType.GOOGLE;
            isLoggedIn = true;
        }
        else isLoggedIn = false;

        // Check if Local JSON Exists
        if(isLocalJsonExist()) {
            App.getPatientJSONObj(reqJsonString);
            App.accountType = AccountType.OFFLINE;
            isLoggedIn = true;
        } else if (googleSignInAccount == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent); // only if connects it goes to next line
        }
    }

    public boolean isLocalJsonExist() {
        Context context = this;
        String userInfoPath = context.getFilesDir() + "/info";
        File file = new File(userInfoPath);
        if(file.canRead()) {
            App.reqJsonString = userInfoPath;
            return true;
        }
        return false;
    }

    /** This Initializes Necessary UserData from JSONObj (local/online) **/
    public static void getPatientJSONObj(String url) {
        // Initial Json data
        try {
            jsonParser = new JSONParser(url);
            jsonParser.execute().get();
            userObj = jsonParser.jsonObject;

            App.patient.setfName(userObj.getString("fName"));
            App.patient.setlName(userObj.getString("lName"));
            App.patient.setNatID(userObj.getString("natID"));
            App.patient.setCountry(userObj.getString("country"));
            App.patient.setCity(userObj.getString("city"));
            App.patient.setBirthDate(userObj.getString("birthDate"));
            App.patient.setCellPhone(userObj.getString("cellPhone"));
            App.patient.setGender(((userObj.getString("gender").equals("male") ||
                    (userObj.getString("gender").equals("Male")))?Gender.MALE:Gender.FEMALE));
            App.patient.setAddress(userObj.getString("address"));
            App.patient.setHomePhone(userObj.getString("homePhone"));
            App.patient.setEmail(userObj.getString("email"));
            App.patient.setLocation(userObj.getString("location"));

            /*
            JSONArray arrPeople = userObj.getJSONArray("ClosePeople");
            JSONObject people[] = new JSONObject[arrPeople.length()];
            for(int i=0; i< arrPeople.length(); i++) {
                people[i] = new JSONObject();
                people[i] = arrPeople.getJSONObject(i);
                App.patient.getPeople()[i].setCellPhone(people[i].getString("cellPhone"));
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
