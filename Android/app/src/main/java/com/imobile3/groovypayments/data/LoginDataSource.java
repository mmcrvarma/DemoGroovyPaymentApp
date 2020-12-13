package com.imobile3.groovypayments.data;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.entities.UserEntity;
import com.imobile3.groovypayments.data.model.LoggedInUser;

import java.io.IOException;
import java.util.List;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String email, String password) {

        try {
            //Call database to verify the user  details
            LoggedInUser loggedInUser = null;
            List<UserEntity> results =
                    DatabaseHelper.getInstance().getDatabase().getUserDao().getUsers();
            for(UserEntity user: results)
            {
                if(email.contentEquals(user.getEmail())
                        && password.contentEquals(user.getPassword()))
                {
                    loggedInUser = new LoggedInUser(String.valueOf(user.getId()),
                            user.getFirstName(), user.getEmail(), user.getUsername());
                    return new Result.Success<>(loggedInUser);
                }
            }
            return new Result.Error(new Exception(MainApplication.getInstance()
                    .getString(R.string.login_user_not_found)));
        } catch (Exception e) {
            return new Result.Error(new Exception(MainApplication.getInstance()
                    .getString(R.string.login_other_error)));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
