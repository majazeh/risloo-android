package com.mre.ligheh.Model.TypeModel;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemAnswer {
    private String type = "";
    private JSONArray options;
    private String tiles = "";

    public ItemAnswer(JSONObject jsonObject) {
        try {
            if (!jsonObject.isNull("type"))
                setType(jsonObject.getString("type"));
            if (!jsonObject.isNull("options"))
                setOptions(jsonObject.getJSONArray("options"));
            if (!jsonObject.isNull("tiles"))
                setTiles(jsonObject.getString("tiles"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONArray getOptions() {
        switch (type) {
            case "optional":
                return options;
            case "optional_images":
                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < options.length(); i++) {
                    try {
                        jsonArray.put(options.getString(i) + ".png");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return jsonArray;
            default:
                return null;
        }
    }

    public void setOptions(JSONArray options) {
        this.options = options;
    }

    public String getTiles() {
        return tiles;
    }

    public void setTiles(String tiles) {
        this.tiles = tiles;
    }

    public boolean compareTo(ItemAnswer model) {
        if (model != null) {
            if (!type.equals(model.getType()))
                return false;

            if (options != model.getOptions())
                return false;

            if (!tiles.equals(model.getTiles()))
                return false;

            return true;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "ItemAnswer{" +
                "type='" + type + '\'' +
                ", options=" + options +
                ", tiles='" + tiles + '\'' +
                '}';
    }

}