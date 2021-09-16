package com.example.suareapp.firebase;

import java.util.HashMap;

public class Constants {

    public static final String KEY_COLLECTIONS_USERS="users";
    public static final String KEY_NAME="name";
    public static final String KEY_PHONE="mobile";
    public static final String KEY_PREFERENCE_NAME="suarePreference";
    public static final String KEY_IS_SIGNED_IN="isSignedIn";
    public static final String KEY_USER_ID="userID";
    public static final String KEY_FCM_TOKEN="fcmToken";
    public static final String KEY_COMMENT="comment";

    public static final String REMOTE_MSG_AUTHORIZATION="Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE="Content-Type";

    public static final String REMOTE_MSG_TYPE="type";
    public static final String REMOTE_MSG_INVITATION="invitation";
    public static final String REMOTE_MSG_MEETING_TYPE="meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN="inviterToken";
    public static final String REMOTE_MSG_DATA="data";
    public static final String REMOTE_MSG_REGISTRATION_IDS="registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE="invitationResponse";
    public static final String REMOTE_MSG_INVITATION_ACCEPTED="accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED="rejected";
    public static final String REMOTE_MSG_INVITATION_CANCELLED="cancelled";
    public static final String RECEIVER_TOKEN="";

    public static final String REMOTE_MSG_MEETING_ROOM="meetingRoom";


    public static final String KEY_LOCATION ="location" ;


    public static HashMap<String ,String> getRemoteMessageHeaders(){
        HashMap<String,String> headers=new HashMap<>();
        headers.put(
                Constants.REMOTE_MSG_AUTHORIZATION,
                "key=AAAAMpsWAus:APA91bEum_YvkFqXvXttwNwVYnThygBeGHiUsOIjbVoisnd5VntetZdraqUOI9qDnLWS4iHWPZjLLQjf0fSG6ph4VzJC5pfDmke35lMt45W-FvZiqHLcKEi82HclIeoG3LUJcX2opafx "
        );
        headers.put(Constants.REMOTE_MSG_CONTENT_TYPE,"application/json");
        return headers;
    }

}
