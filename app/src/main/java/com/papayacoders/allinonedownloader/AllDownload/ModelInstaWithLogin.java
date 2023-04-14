package com.papayacoders.allinonedownloader.AllDownload;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ModelInstaWithLogin implements Serializable {
        @SerializedName("items")
        private List<Item> items;
        @SerializedName("num_results")
        private long numResults;
        @SerializedName("more_available")
        private boolean moreAvailable;
        @SerializedName("auto_load_more_enabled")
        private boolean autoLoadMoreEnabled;

        public ModelInstaWithLogin(){

        }

        @SerializedName("items")
        public List<Item> getItems() {
                return items;
        }

        @SerializedName("items")
        public void setItems(List<Item> value) {
                this.items = value;
        }

        @SerializedName("num_results")
        public long getNumResults() {
                return numResults;
        }

        @SerializedName("num_results")
        public void setNumResults(long value) {
                this.numResults = value;
        }

        @SerializedName("more_available")
        public boolean getMoreAvailable() {
                return moreAvailable;
        }

        @SerializedName("more_available")
        public void setMoreAvailable(boolean value) {
                this.moreAvailable = value;
        }

        @SerializedName("auto_load_more_enabled")
        public boolean getAutoLoadMoreEnabled() {
                return autoLoadMoreEnabled;
        }

        @SerializedName("auto_load_more_enabled")
        public void setAutoLoadMoreEnabled(boolean value) {
                this.autoLoadMoreEnabled = value;
        }
}


