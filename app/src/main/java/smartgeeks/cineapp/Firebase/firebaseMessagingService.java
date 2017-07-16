package smartgeeks.cineapp.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import smartgeeks.cineapp.R;

/**
 * Created by cesarlizcano on 06/03/17.
 */

public class firebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";
    String message, title , action;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        title = remoteMessage.getNotification().getTitle();
        message=  remoteMessage.getNotification().getBody();

        showNotification(title, message);
    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(action);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notiticationBuilder = new NotificationCompat.Builder(this);
        notiticationBuilder.setContentTitle(title);
        notiticationBuilder.setContentText(message);
        notiticationBuilder.setAutoCancel(true);
        notiticationBuilder.setVibrate(new long[]{ 1000, 1000, 1000, 1000, 1000 });
        notiticationBuilder.setSound(defaultSoundUri);
        notiticationBuilder.setSmallIcon(R.mipmap.ic_notification);
        notiticationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notiticationBuilder.build());
    }
}
