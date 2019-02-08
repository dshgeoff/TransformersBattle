package com.aequilibrium.transformersbattle.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Transformers extends BaseEntity implements Parcelable {

    @SerializedName("transformers")
    public List<Transformer> transformers = new ArrayList<>();

    protected Transformers(Parcel in) {
        transformers = in.createTypedArrayList(Transformer.CREATOR);
    }

    public static final Creator<Transformers> CREATOR = new Creator<Transformers>() {
        @Override
        public Transformers createFromParcel(Parcel in) {
            return new Transformers(in);
        }

        @Override
        public Transformers[] newArray(int size) {
            return new Transformers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(transformers);
    }

    public static class Transformer extends BaseEntity implements Parcelable {

        @SerializedName("id") //Uniquely generated ID.
        public String id = "";

        @SerializedName("name") //Transformer name.
        public String name = "";

        @SerializedName("team") //Transformer team, either “A” or “D” (Autobot or Decepticon).
        public String team = "";

        @SerializedName("strength") //must be between 1 and 10.
        public String strength = "";

        @SerializedName("intelligence") //must be between 1 and 10.
        public String intelligence = "";

        @SerializedName("speed") //must be between 1 and 10.
        public String speed = "";

        @SerializedName("endurance") //must be between 1 and 10.
        public String endurance = "";

        @SerializedName("rank") //must be between 1 and 10.
        public String rank = "";

        @SerializedName("courage") //must be between 1 and 10.
        public String courage = "";

        @SerializedName("firepower") //must be between 1 and 10.
        public String firepower = "";

        @SerializedName("skill") //must be between 1 and 10.
        public String skill = "";

        @SerializedName("team_icon") //An image URL that represents what team the Transformer is on.
        public String team_icon = "";

        @SerializedName("overall") //An image URL that represents what team the Transformer is on.
        public String overall = "";

        public Transformer() {

        }

        public Transformer(Parcel in) {
            id = in.readString();
            name = in.readString();
            team = in.readString();
            strength = in.readString();
            intelligence = in.readString();
            speed = in.readString();
            endurance = in.readString();
            rank = in.readString();
            courage = in.readString();
            firepower = in.readString();
            skill = in.readString();
            team_icon = in.readString();
            overall = in.readString();
        }

        public static final Creator<Transformer> CREATOR = new Creator<Transformer>() {
            @Override
            public Transformer createFromParcel(Parcel in) {
                return new Transformer(in);
            }

            @Override
            public Transformer[] newArray(int size) {
                return new Transformer[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(team);
            dest.writeString(strength);
            dest.writeString(intelligence);
            dest.writeString(speed);
            dest.writeString(endurance);
            dest.writeString(rank);
            dest.writeString(courage);
            dest.writeString(firepower);
            dest.writeString(skill);
            dest.writeString(team_icon);
            dest.writeString(overall);
        }

        @Override
        public String toString() {
            return "id: " + id + " name: " + name + " team: " + team
                    + " strength: " + strength + " intelligence: " + intelligence + " speed: " + speed
                    + " endurance: " + endurance + " rank: " + rank + " courage: " + courage
                    + " firepower: " + firepower + " skill: " + skill + " team_icon: " + team_icon;
        }

        public String getOverall() {

            return "" + ((Integer.parseInt(strength) + Integer.parseInt(intelligence)
                    + Integer.parseInt(speed) + Integer.parseInt(endurance)
                    + Integer.parseInt(firepower)) / 5);
        }
    }
}
