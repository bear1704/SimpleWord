package simpleword.aca.com;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class Notification
{
    private Context mContext;
    private NotificationCompat.Builder builder;
    public static final String channelId = "1";
    NotificationManager mNotificationManager;
    NotificationChannel mNotificationChannel;
    android.app.Notification repliedNotification;
    public static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final int REQUEST_REPLY = 244;

    public Notification(Context context)
    {
        mContext = context;

      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
      {

          String replyLabel = "단어 입력하기";
          RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                  .setLabel(replyLabel)
                  .build();

          PendingIntent replyPendingIntent =
                  PendingIntent.getBroadcast(context, 0,
                          new Intent(KEY_TEXT_REPLY),
                          PendingIntent.FLAG_UPDATE_CURRENT);

          NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_launcher_background,
                  replyLabel, replyPendingIntent).addRemoteInput(remoteInput)
                  .build();


          mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
          mNotificationChannel = new NotificationChannel(channelId, "channel_name", NotificationManager.IMPORTANCE_LOW);
          mNotificationChannel.setDescription("channel description");
          mNotificationChannel.enableLights(true);
          mNotificationChannel.enableVibration(false);
          //mNotificationChannel.setVibrationPattern(new long[]{100,200,100,200});
          mNotificationChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);
          mNotificationManager.createNotificationChannel(mNotificationChannel);

          builder = new NotificationCompat.Builder(context, channelId)
                  .setAutoCancel(false)
                  .setContentTitle("간단단어입력장")
                  .setSmallIcon(R.drawable.ic_launcher_background)
                  .setOngoing(true)
                  .addAction(action);


       /*   repliedNotification = new android.app.Notification.Builder(mContext, channelId)
                  .setSmallIcon(R.drawable.ic_launcher_background)
                  .setContentText("replied.")
                  .build();*/



      }


    }


    public void Go()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            mNotificationManager.notify(Integer.parseInt(channelId), builder.build());
        }
    }



}
