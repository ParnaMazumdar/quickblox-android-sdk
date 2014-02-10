package com.quickblox.snippets.modules;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.core.QBRequestCanceler;
import com.quickblox.internal.core.exception.QBResponseException;
import com.quickblox.internal.core.helper.StringifyArrayList;
import com.quickblox.internal.core.request.QBPagedRequestBuilder;
import com.quickblox.internal.module.content.Consts;
import com.quickblox.module.auth.model.QBProvider;
import com.quickblox.module.users.QBUsers;
import com.quickblox.module.users.model.QBUser;
import com.quickblox.snippets.AsyncSnippet;
import com.quickblox.snippets.Snippet;
import com.quickblox.snippets.Snippets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vfite on 04.02.14.
 */

public class SnippetsUsersNew extends Snippets{
    private static final String TAG = SnippetsUsers.class.getSimpleName();

    public SnippetsUsersNew(Context context) {
        super(context);

        snippets.add(signInUserWithLogin);
        snippets.add(signInUserWithEmail);
        snippets.add(signInUsingSocialProvider);
        snippets.add(signInUserWithEmailSync);
        snippets.add(signOut);
        snippets.add(signOutSync);
        snippets.add(signUpUser);
        snippets.add(signUpUserSync);
        snippets.add(signUpSignInUser);

        snippets.add(getAllUsers);
        snippets.add(getUsersByLoginsSync);
        snippets.add(getUsersByIds);
        snippets.add(getUsersByLogins);
        snippets.add(getUsersByEmails);
        snippets.add(getUsersWithFullName);
        snippets.add(getUsersWithTags);

        snippets.add(getUserById);
        snippets.add(getUserWithLogin);
        snippets.add(getUserWithTwitterId);
        snippets.add(getUserWithFacebookId);
        snippets.add(getUserWithEmail);
        snippets.add(getUserWithEmailSync);
        snippets.add(getUserWithExternalId);

        snippets.add(updateUser);

        snippets.add(deleteUserById);
        snippets.add(deleteUserByIdSync);
        snippets.add(deleteUserByExternalId);

        snippets.add(resetPassword);
    }

    Snippet signInUserWithLogin = new Snippet("sign in user (login)") {
        @Override
        public void execute() {

            final QBUser user = new QBUser("testuser", "testpassword");

            final QBRequestCanceler canceler = QBUsers.signIn(user, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle params) {
                    Log.i(TAG, ">>> lastRequestedAt, " + user.getLastRequestAt());

                    Log.i(TAG, ">>> User was successfully signed in, " + user.toString());
                }

                @Override
                public void onError(List<String> errors) {
                       handleErrors(errors);
                }
            });
        }
    };

    Snippet signInUserWithEmail = new Snippet("sign in user (email)") {
        @Override
        public void execute() {

            final QBUser user = new QBUser();
            user.setEmail("test987@test.com");
            user.setPassword("testpassword");

            QBUsers.signIn(user, new QBEntityCallbackImpl<QBUser>() {
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User was successfully signed in, " + user);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet signInUserWithEmailSync = new AsyncSnippet("sign in user (email) syncronize", context) {
        @Override
        public void executeAsync() {
            QBUser user = new QBUser();
            user.setEmail("test987@test.com");
            user.setPassword("testpassword");
            QBUser userResult = null;
            try {
                userResult =  QBUsers.signIn(user);
            } catch (QBResponseException e) {
                setException(e);
            }
            if(userResult != null){
                Log.i(TAG, "User was successfully signed in,"+userResult);
            }
        }
    };

    Snippet signInUsingSocialProvider = new Snippet("sign in using social provider") {
        @Override
        public void execute() {
            String facebookAccessToken = "AAAEra8jNdnkBABYf3ZBSAz9dgLfyK7tQNttIoaZA1cC40niR6HVS0nYuufZB0ZCn66VJcISM8DO2bcbhEahm2nW01ZAZC1YwpZB7rds37xW0wZDZD";

            QBUsers.signInUsingSocialProvider(QBProvider.TWITTER, facebookAccessToken, null, new QBEntityCallbackImpl<QBUser>() {
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User was successfully signed in, " + user);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }

            });
        }
    };

    Snippet signOut = new Snippet("sign out") {
        @Override
        public void execute() {
            QBUsers.signOut(new QBEntityCallbackImpl(){

                @Override
                public void onSuccess() {
                    Log.i(TAG, ">>> User was successfully signed out");
                }

                @Override
                public void onError(List errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet signOutSync = new AsyncSnippet("sign out syncronize", context) {
        @Override
        public void executeAsync() {
            try {
                QBUsers.signOut();
            } catch (QBResponseException e) {
                setException(e);
            }
        }
    };

    Snippet signUpUser = new Snippet("sign up user (register)") {
        @Override
        public void execute() {

            final QBUser user = new QBUser("testuser12344443", "testpassword");
            user.setEmail("test123456789w0@test.com");
            user.setExternalId("02345777");
            user.setFacebookId("1233453457767");
            user.setTwitterId("12334635457");
            user.setFullName("fullName5");
            user.setPhone("+18904567812");
            StringifyArrayList<String> tags = new StringifyArrayList<String>();
            tags.add("firstTag");
            tags.add("secondTag");
            tags.add("thirdTag");
            tags.add("fourthTag");
            user.setTags(tags);
            user.setWebsite("website.com");

            QBUsers.signUp(user, new QBEntityCallbackImpl<QBUser>() {
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User was successfully signed up, " + user);
                }

                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet signUpUserSync = new AsyncSnippet("sign up user (register) sync", context) {
        @Override
        public void executeAsync() {

            final QBUser user = new QBUser("testuser12344443", "testpassword");
            user.setEmail("test123456789w0@test.com");
            user.setExternalId("02345777");
            user.setFacebookId("1233453457767");
            user.setTwitterId("12334635457");
            user.setFullName("fullName5");
            user.setPhone("+18904567812");
            StringifyArrayList<String> tags = new StringifyArrayList<String>();
            tags.add("firstTag");
            tags.add("secondTag");
            tags.add("thirdTag");
            tags.add("fourthTag");
            user.setTags(tags);
            user.setWebsite("website.com");
            QBUser qbUserResult = null;
            try {
                qbUserResult = QBUsers.signUp(user);
            } catch (QBResponseException e) {
                setException(e);
            }
            if(qbUserResult != null){
                Log.i(TAG, ">>> User was successfully signed up, " + qbUserResult);
            }
        }
    };

    Snippet signUpSignInUser = new Snippet("sign up user (register) and sign in user") {
        @Override
        public void execute() {

            final QBUser user = new QBUser("testuser12344443", "testpassword");
            user.setEmail("test123456789w0@test.com");
            user.setExternalId("02345777");
            user.setFacebookId("1233453457767");
            user.setTwitterId("12334635457");
            user.setFullName("fullName5");
            user.setPhone("+18904567812");
            StringifyArrayList<String> tags = new StringifyArrayList<String>();
            tags.add("firstTag");
            tags.add("secondTag");
            tags.add("thirdTag");
            tags.add("fourthTag");
            user.setTags(tags);
            user.setWebsite("website.com");

            QBUsers.signUpSignInTask(user, new QBEntityCallbackImpl<QBUser>() {
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User was successfully signed up and signed in, " + user);
                }

                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };


    Snippet getAllUsers = new Snippet("get all users") {
        @Override
        public void execute() {

            QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
            pagedRequestBuilder.setPage(2);
            pagedRequestBuilder.setPerPage(5);

            QBUsers.getUsers(pagedRequestBuilder, new QBEntityCallbackImpl<ArrayList<QBUser>>() {

                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    Log.i(TAG, ">>> Users: " + users.toString());
                    Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                    Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                    Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                }

                @Override
                public void onError(List<String> errors) {
                   handleErrors(errors);
                }
            });

        }
    };


    Snippet getUsersByIds = new Snippet("get users by ids") {
        @Override
        public void execute() {
            QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
            pagedRequestBuilder.setPage(1);
            pagedRequestBuilder.setPerPage(10);

            List<Integer> usersIds = new ArrayList<Integer>();
            usersIds.add(378);
            usersIds.add(379);
            usersIds.add(380);

            QBUsers.getUsersByIDs(usersIds, pagedRequestBuilder, new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                    @Override
                    public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                        Log.i(TAG, ">>> Users: " + users.toString());
                        Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                        Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                        Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                    }

                    @Override
                    public void onError(List<String> errors) {
                        handleErrors(errors);
                    }

            });
        }
    };

    Snippet getUsersByLogins = new Snippet("get users by logins") {
        @Override
        public void execute() {
            QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
            pagedRequestBuilder.setPage(1);
            pagedRequestBuilder.setPerPage(10);

            ArrayList<String> usersLogins = new ArrayList<String>();
            usersLogins.add("bob");
            usersLogins.add("john");

            QBUsers.getUsersByLogins(usersLogins, pagedRequestBuilder, new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    Log.i(TAG, ">>> Users: " + users.toString());
                    Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                    Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                    Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }

            });
        }
    };

    Snippet getUsersByLoginsSync = new AsyncSnippet("get users by logins synchornous", context) {
        @Override
        public void executeAsync() {
            QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
            pagedRequestBuilder.setPage(1);
            pagedRequestBuilder.setPerPage(10);

            ArrayList<String> usersLogins = new ArrayList<String>();
            usersLogins.add("bob");
            usersLogins.add("john");
            Bundle params = new Bundle();
            ArrayList<QBUser> usersByLogins = null;
            try {
                usersByLogins = QBUsers.getUsersByLogins(usersLogins, pagedRequestBuilder, params);
            } catch (QBResponseException e) {
               setException(e);
            }

            if(usersByLogins != null){
                Log.i(TAG, ">>> Users: " + usersByLogins.toString());
                Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
            }
        }
    };


    Snippet getUsersByEmails = new Snippet("get users by emails") {
        @Override
        public void execute() {
            QBPagedRequestBuilder pagedRequestBuilder = new QBPagedRequestBuilder();
            pagedRequestBuilder.setPage(1);
            pagedRequestBuilder.setPerPage(10);

            ArrayList<String> usersEmails = new ArrayList<String>();
            usersEmails.add("asd@ffg.fgg");
            usersEmails.add("ghh@ggh.vbb");

            QBUsers.getUsersByEmails(usersEmails, pagedRequestBuilder, new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    Log.i(TAG, ">>> Users: " + users.toString());
                    Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                    Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                    Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }

            });
        }
    };

    Snippet getUsersWithFullName = new Snippet("get user with full name") {
        @Override
        public void execute() {
            String fullName = "fullName";
            QBUsers.getUsersByFullName(fullName, null,  new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    Log.i(TAG, ">>> Users: " + users.toString());
                    Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                    Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                    Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet getUsersWithTags = new Snippet("get users with tags") {
        @Override
        public void execute() {
            ArrayList<String> userTags = new ArrayList<String>();
            userTags.add("man");
            userTags.add("car");

            QBUsers.getUsersByTags(userTags, null, new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    Log.i(TAG, ">>> Users: " + users.toString());
                    Log.i(TAG, "currentPage: " + params.getInt(Consts.CURR_PAGE));
                    Log.i(TAG, "perPage: " + params.getInt(Consts.PER_PAGE));
                    Log.i(TAG, "totalPages: " + params.getInt(Consts.TOTAL_ENTRIES));
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };


    Snippet getUserById = new Snippet("get user by id") {
        @Override
        public void execute() {
            QBUsers.getUser(53779, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet getUserWithLogin = new Snippet("get user with login") {
        @Override
        public void execute() {
            String login = "testuser";
            QBUser qbUser = new QBUser();
            qbUser.setLogin(login);
            QBUsers.getUserByLogin(qbUser, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet getUserWithTwitterId = new Snippet("get user with twitter id") {
        @Override
        public void execute() {
            String twitterId = "56802037340";
            QBUsers.getUserByTwitterId(twitterId, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }

                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet getUserWithFacebookId = new Snippet("get user with facebook id") {
        @Override
        public void execute() {
            String facebookId = "100003123141430";
            QBUsers.getUserByFacebookId(facebookId, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }
                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet getUserWithEmail = new Snippet("get user with email") {
        @Override
        public void execute() {
            String email = "test123@test.com";
            QBUsers.getUserByEmail(email, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }
                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet getUserWithEmailSync = new AsyncSnippet("get user with email synchornous", context) {

        QBUser userByEmail;
        @Override
        public void executeAsync() {
            String email = "test123@test.com";
            try {
                userByEmail = QBUsers.getUserByEmail(email);
            } catch (QBResponseException e) {
                setException(e);
            }
        }

        @Override
        protected void postExecute() {
            super.postExecute();
            if( userByEmail != null){
                Log.i(TAG, ">>> User: " + userByEmail.toString());
            }
        }
    };

    Snippet getUserWithExternalId = new Snippet("get user with external id") {
        @Override
        public void execute() {
            String externalId = "123145235";
            QBUsers.getUserByExternalId(externalId, new QBEntityCallbackImpl<QBUser>() {

                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> User: " + user.toString());
                }
                @Override
                public void onError(List<String> errors) {
                    super.onError(errors);
                }
            });
        }
    };

    Snippet updateUser = new Snippet("update user") {
        @Override
        public void execute() {
            final QBUser user = new QBUser();
            user.setId(53779);
            user.setFullName("Monro");
            user.setEmail("test987@test.com");
            user.setExternalId("987");
            user.setFacebookId("987");
            user.setTwitterId("987");
            user.setFullName("galog");
            user.setPhone("+123123123");
            StringifyArrayList<String> tags = new StringifyArrayList<String>();
            tags.add("man");
            user.setTags(tags);

            user.setWebsite("google.com");


            QBUsers.updateUser(user, new QBEntityCallbackImpl<QBUser>(){
                @Override
                public void onSuccess(QBUser user, Bundle args) {
                    Log.i(TAG, ">>> updatedAt: " + user.getUpdatedAt());
                    Log.i(TAG, ">>> createdAt: " + user.getCreatedAt());

                    Log.i(TAG, ">>> User: " + user);
                }

                @Override
                public void onError(List<String> errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet deleteUserById = new Snippet("delete user by id") {
        @Override
        public void execute() {

            int userId = 562;
            QBUsers.deleteUser(new QBUser(userId), new QBEntityCallbackImpl() {

                @Override
                public void onSuccess() {
                    Log.i(TAG, ">>> User was successfully deleted");
                }

                @Override
                public void onError(List errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet deleteUserByIdSync = new AsyncSnippet("delete user by id synchronous", context) {
        @Override
        public void executeAsync() {

            int userId = 562;

            try {
                QBUsers.deleteUser(new QBUser(userId));
            } catch (QBResponseException e) {
                setException(e);
            }
        }
    };


    Snippet deleteUserByExternalId = new Snippet("delete user by external id") {
        @Override
        public void execute() {
            QBUsers.deleteByExternalId("568965444", new QBEntityCallbackImpl() {

                @Override
                public void onSuccess() {
                    Log.i(TAG, ">>> User was successfully deleted");
                }

                @Override
                public void onError(List errors) {
                    handleErrors(errors);
                }
            });
        }
    };

    Snippet resetPassword = new Snippet("reset password") {
        @Override
        public void execute() {
            QBUsers.resetPassword("test987@test.com", new QBEntityCallbackImpl() {

                @Override
                public void onSuccess() {
                    Log.i(TAG, ">>> Email was sent");
                }

                @Override
                public void onError(List errors) {
                    handleErrors(errors);
                }
            });
        }
    };

}
