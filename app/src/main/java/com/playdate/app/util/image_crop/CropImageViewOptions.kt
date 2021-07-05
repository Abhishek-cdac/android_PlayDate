// "Therefore those skilled at the unorthodox
// are infinite as heaven and earth,
// inexhaustible as the great rivers.
// When they come to an end,
// they begin again,
// like the days and months;
// they die and are reborn,
// like the four seasons."
//
// - Sun Tsu,
// "The Art of War"
package com.playdate.app.util.image_crop

import android.util.Pair
import com.theartofdev.edmodo.cropper.CropImageView
import com.theartofdev.edmodo.cropper.CropImageView.CropShape
import com.theartofdev.edmodo.cropper.CropImageView.Guidelines

/** The crop image view options that can be changed live.  */
class CropImageViewOptions {
    @JvmField
    var scaleType = CropImageView.ScaleType.CENTER_INSIDE
    @JvmField
    var cropShape = CropShape.RECTANGLE
    @JvmField
    var guidelines = Guidelines.ON_TOUCH
    @JvmField
    var aspectRatio = Pair(1, 1)
    @JvmField
    var autoZoomEnabled = false
    @JvmField
    var maxZoomLevel = 0
    @JvmField
    var fixAspectRatio = false
    @JvmField
    var multitouch = false
    @JvmField
    var showCropOverlay = false
    @JvmField
    var showProgressBar = false
    @JvmField
    var flipHorizontally = false
    @JvmField
    var flipVertically = false
}