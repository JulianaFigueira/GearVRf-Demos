/* Copyright 2015 Samsung Electronics Co., LTD
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

package org.gearvrf.video.overlay;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.video.focus.OnClickListener;
import org.gearvrf.video.movie.MovieManager;

import java.io.IOException;

public class VideoControl extends GVRSceneObject {

    private GVRSceneObject mButtonBoard = null;
    private Button mPlayButton = null;
    private Button mPauseButton = null;
    private Button mFrontButton = null;
    private Button mBackButton = null;
    private Button mImaxButton = null;
    private Button mSelectButton = null;
    private Seekbar mSeekbar = null;
    private MovieManager mMovieManager = null;

    public VideoControl(GVRContext context, final MovieManager movieManager) {
        super(context);
        mMovieManager = movieManager;
        try {
            // button board
            mButtonBoard = new GVRSceneObject(context, context.createQuad(8.2f, 1.35f),
                    context.loadTexture(new GVRAndroidResource(context, "button/button-board.png")));
            mButtonBoard.getTransform().setPosition(-0.1f, -0.6f, -8.0f);
            mButtonBoard.getRenderData().setRenderingOrder(
                    GVRRenderData.GVRRenderingOrder.TRANSPARENT);
            addChildObject(mButtonBoard);

            // play
            mPlayButton = new Button(context, context.createQuad(0.7f, 0.7f),
                    context.loadTexture(new GVRAndroidResource(context, "button/play-active.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/play-inactive.png")));
            mPlayButton.setPosition(0.0f, -0.8f, -8.0f);
            mPlayButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.getMediaPlayer().start();
                    mPlayButton.hide();
                    mPauseButton.show();
                }
            });
            addChildObject(mPlayButton);
            mPlayButton.hide();

            // pause
            mPauseButton = new Button(context, context.createQuad(0.7f, 0.7f),
                    context.loadTexture(new GVRAndroidResource(context, "button/pause-active.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/pause-inactive.png")));
            mPauseButton.setPosition(0.0f, -0.8f, -8.0f);
            mPauseButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.getMediaPlayer().pause();
                    mPauseButton.hide();
                    mPlayButton.show();
                }
            });
            addChildObject(mPauseButton);

            // next
            mFrontButton = new Button(context, context.createQuad(0.7f, 0.7f),
                    context.loadTexture(new GVRAndroidResource(context, "button/front-active.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/front-inactive.png")));
            mFrontButton.setPosition(1.2f, -0.8f, -8.0f);
            mFrontButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.getMediaPlayer().seekTo(movieManager.getMediaPlayer().getCurrentPosition() + 10000);
                }
            });
            addChildObject(mFrontButton);

            // prev
            mBackButton = new Button(context, context.createQuad(0.7f, 0.7f),
                    context.loadTexture(new GVRAndroidResource(context, "button/back-active.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/back-inactive.png")));
            mBackButton.setPosition(-1.2f, -0.8f, -8.0f);
            mBackButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.getMediaPlayer().seekTo(movieManager.getMediaPlayer().getCurrentPosition() - 10000);
                }
            });
            addChildObject(mBackButton);

            // imax
            mImaxButton = new Button(context, context.createQuad(0.9f, 0.35f),
                    context.loadTexture(new GVRAndroidResource(context, "button/imaxselect.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/imaxoutline.png")));
            mImaxButton.setPosition(2.5f, -0.9f, -7.5f);
            mImaxButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.getCurrentMovieTheater().switchToImax();
                }
            });
            addChildObject(mImaxButton);

            // select
            mSelectButton = new Button(context, context.createQuad(0.9f, 0.35f),
                    context.loadTexture(new GVRAndroidResource(context, "button/selectionselect.png")),
                    context.loadTexture(new GVRAndroidResource(context, "button/selectionoutline.png")));
            mSelectButton.setPosition(-2.5f, -0.9f, -7.5f);
            mSelectButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                    movieManager.switchMovieTheater();
                }
            });
            addChildObject(mSelectButton);

            // seekbar
            // TODO: improve seekbar design
            mSeekbar = new Seekbar(context);
            addChildObject(mSeekbar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        mButtonBoard.getRenderData().setRenderMask(GVRRenderData.GVRRenderMaskBit.Left
                | GVRRenderData.GVRRenderMaskBit.Right);
        mPlayButton.show();
        mPauseButton.show();
        mFrontButton.show();
        mBackButton.show();
        mImaxButton.show();
        mSelectButton.show();
        mSeekbar.setRenderMask(GVRRenderData.GVRRenderMaskBit.Left
                | GVRRenderData.GVRRenderMaskBit.Right);
    }

    public void hide() {
        mButtonBoard.getRenderData().setRenderMask(0);
        mPlayButton.hide();
        mPauseButton.hide();
        mFrontButton.hide();
        mBackButton.hide();
        mImaxButton.hide();
        mSelectButton.hide();
        mSeekbar.setRenderMask(0);
    }

    public void updateOverlayUI(GVRContext context) {
        Float seekBarRatio = mSeekbar.getRatio(context.getMainScene().getMainCameraRig().getLookAt());
        if (seekBarRatio != null) {
            mSeekbar.glow();
        } else {
            mSeekbar.unglow();
        }
        mSeekbar.setTime(context, mMovieManager.getMediaPlayer().getCurrentPosition(),
                mMovieManager.getMediaPlayer().getDuration());
    }

    public boolean isOverlayPointed(GVRContext context) {
        return mSeekbar.getRatio(context.getMainScene().getMainCameraRig().getLookAt()) != null ? true : false;
    }

    public void processTouch(GVRContext context) {
        Float seekBarRatio = mSeekbar.getRatio(context.getMainScene().getMainCameraRig().getLookAt());
        if (seekBarRatio != null) {
            int current = (int) (mMovieManager.getMediaPlayer().getDuration() * seekBarRatio);
            mMovieManager.getMediaPlayer().seekTo(current);
            mSeekbar.setTime(context, current, mMovieManager.getMediaPlayer().getDuration());
        }
    }
}
