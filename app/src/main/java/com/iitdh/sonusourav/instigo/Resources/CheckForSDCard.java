package com.iitdh.sonusourav.instigo.Resources;

import android.os.Environment;

/**
 * Created by Sonu on 21 Oct 2018 011.
 */

class CheckForSDCard {
    //Method to Check If SD Card is mounted or not
    static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED);
    }
}