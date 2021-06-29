package com.mre.ligheh.Model.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemAnswer {
    private String type="";
    private JSONArray options;
    private String tiles;

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

    public JSONArray getAnswer() {
        switch (type) {
            case "optional":
                return options;
            case "optional_images":
                return options;
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
}
