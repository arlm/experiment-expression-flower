package com.example.androidthings.sequences;

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

import android.graphics.Color;
import android.util.Log;
import com.example.androidthings.Flower;
import com.example.androidthings.FlowerLEDController;
import java.io.IOException;

/** Rotates a rainbow sequence around a flower's LEDs */
public class RainbowSequence extends Sequence {

  private static final String TAG = RainbowSequence.class.getSimpleName();

  private int color = 0;
  private final float opening;

  public RainbowSequence(Flower flower, float opening, Runnable sequenceCompletedCallback) {
    super(flower, sequenceCompletedCallback);
    this.opening = opening;
  }

  @Override
  public boolean isInterruptible() {
    return true;
  }

  @Override
  boolean animateNextFrame(int frame) throws IOException {
    color = generateRainbow(frame);
    if (flower.getIsInConfigMode()) {
      flower.setOpening(1f);
      Log.i(TAG, "Configuration Mode Active.");
    } else {
      flower.setOpening(opening);
    }
    flower.setLEDs(color);
    return false;
  }

  // Assigns gradient colors.
  private static int generateRainbow(int frame) {
    int color = 0;

    float[] hsv = {1f, 1f, 1f};
    for (int i = 0; i < 6; i++) {
      int n = (i + frame) % 6;
      hsv[0] = n * 360f / 6;
      color = Color.HSVToColor(0, hsv);
    }

    return color;
  }
}

