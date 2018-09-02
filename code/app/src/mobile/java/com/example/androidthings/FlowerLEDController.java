package com.example.androidthings;

/*
 * Copyright 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;

/** This class controls the flower's LEDs. */
public class FlowerLEDController {
  private Context context;
    private int color;

    FlowerLEDController(Context context) {
    this.context = context;
  }

  void setFlowerHue(float hue, float saturation, float brightness) {
    setFlowerLEDs(this.color = hsvToColor(hue, saturation, brightness));
  }

  synchronized void setFlowerLEDs(int color) {
    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    // clear previous notification
    notificationManager.cancel(1);

    Notification.Builder builder = new Notification.Builder(context);
    builder.setSmallIcon(R.drawable.ic_account_circle_black_24dp);
    builder.setLargeIcon(Icon.createWithResource(context, R.drawable.ic_account_circle_black_24dp));
    builder.setLights(color, 1000, 250);

    notificationManager.notify(1, builder.build());

    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
  }

  private static int hsvToColor(float hue, float saturation, float brightness) {
    return Color.HSVToColor(0, new float[] {hue, saturation, brightness});
  }
}
