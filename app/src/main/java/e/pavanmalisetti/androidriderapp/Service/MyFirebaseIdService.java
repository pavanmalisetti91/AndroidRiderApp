package e.pavanmalisetti.androidriderapp.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import e.pavanmalisetti.androidriderapp.Common.Common;
import e.pavanmalisetti.androidriderapp.Model.Token;

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        updateTokenToServer(refreshedToken);  //when we have refresh token,we need update to our realtime database

    }

    private void updateTokenToServer(String refreshedToken) {
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference tokens=db.getReference(Common.token_tbl);

        Token token=new Token(refreshedToken);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(token);
        }
    }
}

