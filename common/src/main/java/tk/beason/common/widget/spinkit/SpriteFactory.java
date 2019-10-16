/*
 * Copyright (C) 2016 The yuhaiyang Android Source Project
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

package tk.beason.common.widget.spinkit;


import tk.beason.common.widget.spinkit.sprite.Sprite;
import tk.beason.common.widget.spinkit.style.ChasingDots;
import tk.beason.common.widget.spinkit.style.Circle;
import tk.beason.common.widget.spinkit.style.CubeGrid;
import tk.beason.common.widget.spinkit.style.DoubleBounce;
import tk.beason.common.widget.spinkit.style.FadingCircle;
import tk.beason.common.widget.spinkit.style.FoldingCube;
import tk.beason.common.widget.spinkit.style.MultiplePulse;
import tk.beason.common.widget.spinkit.style.MultiplePulseRing;
import tk.beason.common.widget.spinkit.style.Pulse;
import tk.beason.common.widget.spinkit.style.PulseRing;
import tk.beason.common.widget.spinkit.style.RotatingCircle;
import tk.beason.common.widget.spinkit.style.RotatingPlane;
import tk.beason.common.widget.spinkit.style.ThreeBounce;
import tk.beason.common.widget.spinkit.style.WanderingCubes;
import tk.beason.common.widget.spinkit.style.Wave;

/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
