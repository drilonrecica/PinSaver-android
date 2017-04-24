package com.reqica.drilon.pinsaver.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class EntryModel implements Parcelable {
    private String card;
    private String pin;

    public EntryModel(@NonNull String card, @NonNull String pin) {
        this.card = card;
        this.pin = pin;
    }

    public String getCard() {
        return card;
    }

    public String getPin() {
        return pin;
    }

    private EntryModel(@NonNull Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.card = data[0];
        this.pin = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.card,
                this.pin});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public EntryModel createFromParcel(@NonNull Parcel in) {
            return new EntryModel(in);
        }

        public EntryModel[] newArray(int size) {
            return new EntryModel[size];
        }
    };
}