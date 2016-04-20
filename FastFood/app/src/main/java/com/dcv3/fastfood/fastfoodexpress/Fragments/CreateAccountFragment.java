/*
This class inflates the create account screen and allows first time
users to create an account when they first download the app -DJ
*/

package com.dcv3.fastfood.fastfoodexpress.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.dcv3.fastfood.fastfoodexpress.R;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by dezereljones on 11/17/15.
 */
public class CreateAccountFragment extends Fragment {
    private EditText firstNameET;
    private EditText lastNameET;
    private EditText emailAddressET;
    private EditText passWordET;
    private Button createAcctButton;
    FragmentManager fm;
    FragmentTransaction ft;

    /*
    this method inflates the view and attaches the variables
    to the input fields and activates the button -DJ
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_createacct, container, false);

        firstNameET = (EditText)v.findViewById(R.id.fname);
        lastNameET = (EditText)v.findViewById(R.id.lname);
        emailAddressET = (EditText)v.findViewById(R.id.Emailaddressfield2);
        passWordET = (EditText)v.findViewById(R.id.passwordField2);
        createAcctButton = (Button)v.findViewById(R.id.sendbutton);

        createAcctButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createAcct();
            }
        });

        return v;
    }

    /*
    This method gets the input from each field when the button is clicked
    and verifies that the input is valid.  If the input is valid then it creates a new user in
    the database and sends the user to the log in screen. -DJ
    */
    private void createAcct() {
        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String emailAddress = emailAddressET.getText().toString().trim();
        String password = passWordET.getText().toString().trim();

        // Verify the sign up data - DJ
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (firstName.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_firstname));
        }
        if (lastName.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_lastname));
        }
        if (emailAddress.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_email));
        }
        if (password.length() < 5) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append("Password Invalid");
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is an error, display the error - DJ
        if (validationError) {
            Toast.makeText(getActivity(), validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        // Set up a progress dialog - DJ
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.progress_signup));
        dialog.show();

        // Set up a new Parse user - DJ
        ParseUser user = new ParseUser();
        user.setUsername(emailAddress);
        user.setPassword(password);
        user.setEmail(emailAddress);
        user.put("fname", firstName);
        user.put("lname", lastName);

        // Call the Parse signup method - DJ
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                   Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    //changes the fragment view to the login screen-DJ
                    switchFragment(new LoginActivityFragment());
                }
            }
        });
    }

    //switch the fragments-DJ
    public void switchFragment(Fragment fr){
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fr);
        ft.commit();
    }
}
