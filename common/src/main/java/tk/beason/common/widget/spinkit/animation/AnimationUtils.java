/*
 * Copyright (C) 2016 The beasontk Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.common.widget.spinkit.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;

import tk.beason.common.widget.spinkit.sprite.Sprite;


/**
 * Created by ybq.
 */
public class AnimationUtils {

    public static void start(Animator animator) {
        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    public static void stop(Animator animator) {
        if (animator != null && !animator.isRunning()) {
            animator.end();
        }
    }

    public static void start(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            sprite.start();
        }
    }

    public static void stop(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            sprite.stop();
        }
    }

    public static boolean isRunning(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            if (sprite.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRunning(ValueAnimator animator) {
        return animator != null && animator.isRunning();
    }

    public static boolean isStarted(ValueAnimator animator) {
        return animator != null && animator.isStarted();
    }
}
