package me.taishi_y.englishsensei.Model;

import android.graphics.Bitmap;

/**
 * Created by yamasakitaishi on 2016/04/18.
 */
public class CustomData {
    private Bitmap imageData_;
    private String textData_, userData_, timeData,textJapaneseData_,textEnglishData_,textObjectId_;

    public void setImagaData(Bitmap image) {
        imageData_ = image;
    }

    public Bitmap getImageData() {
        return imageData_;
    }

    public void setTextData(String text) {
        textData_ = text;
    }

    public String getTextData() {
        return textData_;
    }

    public void setEnglishTextData(String text) {
        textEnglishData_ = text;
    }

    public String getEnglishTextData() {
        return textEnglishData_;
    }

    public void setJapaneseTextData(String text) {
        textJapaneseData_ = text;
    }

    public String getJapaneseTextData() {
        return textJapaneseData_;
    }

    public void setObjectId(String text) {
        textObjectId_ = text;
    }

    public String getObjectId() {
        return textObjectId_;
    }


}