package com.playdate.app.util.image_crop

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.playdate.app.R
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {
    private var mCurrentFragment: MainFragment? = null
    private var mCropImageUri: Uri? = null
    private var mCropImageViewOptions = CropImageViewOptions()

    // endregion
    fun setCurrentFragment(fragment: MainFragment?) {
        mCurrentFragment = fragment
    }

    fun setCurrentOptions(options: CropImageViewOptions) {
        mCropImageViewOptions = options
    }

    var isForProfile = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mIntent = intent
        isForProfile = mIntent.getBooleanExtra("isForProfile", false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        if (savedInstanceState == null) {
            setMainFragmentByPreset(CropDemoPreset.MIN_MAX_OVERRIDE)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mCurrentFragment!!.updateCurrentCropViewOptions()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (mCurrentFragment != null && mCurrentFragment!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
            && resultCode == RESULT_OK
        ) {
            val imageUri = CropImage.getPickImageResultUri(this, data)
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
                )
            } else {
                mCurrentFragment!!.setImageUri(imageUri)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this)
            } else {
                Toast.makeText(
                    this,
                    "Cancelling, required permissions are not granted",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mCurrentFragment!!.setImageUri(mCropImageUri)
            } else {
                Toast.makeText(
                    this,
                    "Cancelling, required permissions are not granted",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun setMainFragmentByPreset(demoPreset: CropDemoPreset) {
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.newInstance(demoPreset, isForProfile))
            .commit()
    }
}