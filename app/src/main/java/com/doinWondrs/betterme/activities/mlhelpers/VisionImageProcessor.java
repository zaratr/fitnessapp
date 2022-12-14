

package com.doinWondrs.betterme.activities.mlhelpers;

import android.graphics.Bitmap;

import com.doinWondrs.betterme.activities.mlhelpers.FrameMetadata;
import com.doinWondrs.betterme.activities.mlhelpers.GraphicOverlay;
import com.google.mlkit.common.MlKitException;

import java.nio.ByteBuffer;

import androidx.camera.core.ImageProxy;

/** An interface to process the images with different vision detectors and custom image models. */
public interface VisionImageProcessor {

  /** Processes a bitmap image. */
  void processBitmap(Bitmap bitmap, GraphicOverlay graphicOverlay);

  /** Processes ByteBuffer image data, e.g. used for Camera1 live preview case. */
  void processByteBuffer(
          ByteBuffer data, FrameMetadata frameMetadata, GraphicOverlay graphicOverlay)
      throws MlKitException;

  /** Processes ImageProxy image data, e.g. used for CameraX live preview case. */
  void processImageProxy(ImageProxy image, GraphicOverlay graphicOverlay) throws MlKitException;

  /** Stops the underlying machine learning model and release resources. */
  void stop();
}
