package com.mre.ligheh.Model.TypeModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.mre.ligheh.Model.Madule.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserModel extends TypeModel implements Parcelable {

    private String id="";
    private String userId="";
    private String name="";
    private String email="";
    private String mobile="";
    private String gender="";
    private String userStatus="";
    private boolean no_password;
    private String position="";
    private String userType="";
    private JSONObject groups;
    private String username="";
    private UserModel creator;
    private String public_key="";
    private String birthday="";
    private int userCreated_at;
    private int userUpdated_at;
    private int userAccepted_at;
    private int userKicked_at;
    private JSONObject meta;
    private AvatarModel avatar;
    private List centerList;
    private List roomList;
    private List caseList;
    private List sampleList;
    private JSONArray treasuries;

    public UserModel() {
        super();
    }

    public UserModel(JSONObject jsonObject) throws JSONException {
        super(jsonObject);
        setId(jsonObject.getString("id"));
        if (!jsonObject.isNull("name"))
            setName(jsonObject.getString("name"));
        if (!jsonObject.isNull("user_id"))
            setUserId(jsonObject.getString("user_id"));
        if (!jsonObject.isNull("email"))
            setEmail(jsonObject.getString("email"));
        if (!jsonObject.isNull("mobile"))
            setMobile(jsonObject.getString("mobile"));
        if (!jsonObject.isNull("gender"))
            setGender(jsonObject.getString("gender"));
        if (!jsonObject.isNull("status"))
            setUserStatus(jsonObject.getString("status"));
        if (!jsonObject.isNull("position"))
            setPosition(jsonObject.getString("position"));
        if (!jsonObject.isNull("public_key"))
            setPublic_key(jsonObject.getString("public_key"));
        if (!jsonObject.isNull("no_password"))
            setNo_password(jsonObject.getBoolean("no_password"));
        if (!jsonObject.isNull("type"))
            setUserType(jsonObject.getString("type"));
        if (!jsonObject.isNull("groups"))
            setGroups(jsonObject.getJSONObject("groups"));
        if (!jsonObject.isNull("username"))
            setUsername(jsonObject.getString("username"));
        if (!jsonObject.isNull("public_key"))
            setPublic_key(jsonObject.getString("public_key"));
        if (!jsonObject.isNull("birthday"))
            setBirthday(jsonObject.getString("birthday"));
        if (!jsonObject.isNull("created_at"))
            setUserCreated_at(jsonObject.getInt("created_at"));
        if (!jsonObject.isNull("updated_at"))
            setUserUpdated_at(jsonObject.getInt("updated_at"));
        if (!jsonObject.isNull("accepted_at"))
            setUserAccepted_at(jsonObject.getInt("accepted_at"));
        if (!jsonObject.isNull("kicked_at"))
            setUserKicked_at(jsonObject.getInt("kicked_at"));
        if (!jsonObject.isNull("meta"))
            setMeta(jsonObject.getJSONObject("meta"));
        if (!jsonObject.isNull("creator"))
            setCreator(new UserModel(jsonObject.getJSONObject("creator")));
        if (!jsonObject.isNull("avatar"))
            setAvatar(new AvatarModel(jsonObject.getJSONArray("avatar")));


        if (!jsonObject.isNull("centers")) {
            List centers = new List();
            for (int i = 0; i < jsonObject.getJSONArray("centers").length(); i++) {
                centers.add(new CenterModel(jsonObject.getJSONArray("centers").getJSONObject(i)));
            }
            setCenterList(centers);
        } else {
            setCenterList(new List());
        }
        if (!jsonObject.isNull("rooms")) {
            List rooms = new List();
            for (int i = 0; i < jsonObject.getJSONArray("rooms").length(); i++) {
                rooms.add(new RoomModel(jsonObject.getJSONArray("rooms").getJSONObject(i)));
            }
            setRoomList(rooms);
        } else {
            setRoomList(new List());
        }
        if (!jsonObject.isNull("cases")) {
            List cases = new List();
            for (int i = 0; i < jsonObject.getJSONArray("cases").length(); i++) {
                cases.add(new CaseModel(jsonObject.getJSONArray("cases").getJSONObject(i)));
            }
            setCaseList(cases);
        } else {
            setCaseList(new List());
        }
        if (!jsonObject.isNull("samples")) {
            List samples = new List();
            for (int i = 0; i < jsonObject.getJSONArray("samples").length(); i++) {
                samples.add(new SampleModel(jsonObject.getJSONArray("samples").getJSONObject(i)));
            }
            setSampleList(samples);
        } else {
            setSampleList(new List());
        }
        if (!jsonObject.isNull("treasuries")){
            setTreasuries(jsonObject.getJSONArray("treasuries"));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isNo_password() {
        return no_password;
    }

    public void setNo_password(boolean no_password) {
        this.no_password = no_password;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public JSONObject getGroups() {
        return groups;
    }

    public void setGroups(JSONObject groups) {
        this.groups = groups;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getUserCreated_at() {
        return userCreated_at;
    }

    public void setUserCreated_at(int userCreated_at) {
        this.userCreated_at = userCreated_at;
    }

    public int getUserUpdated_at() {
        return userUpdated_at;
    }

    public void setUserUpdated_at(int userUpdated_at) {
        this.userUpdated_at = userUpdated_at;
    }

    public AvatarModel getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarModel avatar) {
        this.avatar = avatar;
    }

    public List getCenterList() {
        return centerList;
    }

    public void setCenterList(List centerList) {
        this.centerList = centerList;
    }

    public UserModel getCreator() {
        return creator;
    }

    public void setCreator(UserModel creator) {
        this.creator = creator;
    }

    public List getRoomList() {
        return roomList;
    }

    public void setRoomList(List roomList) {
        this.roomList = roomList;
    }

    public List getCaseList() {
        return caseList;
    }

    public void setCaseList(List caseList) {
        this.caseList = caseList;
    }

    public List getSampleList() {
        return sampleList;
    }

    public void setSampleList(List sampleList) {
        this.sampleList = sampleList;
    }

    public JSONArray getTreasuries() {
        return treasuries;
    }

    public void setTreasuries(JSONArray treasuries) {
        this.treasuries = treasuries;
    }

    public int getUserAccepted_at() {
        return userAccepted_at;
    }

    public void setUserAccepted_at(int userAccepted_at) {
        this.userAccepted_at = userAccepted_at;
    }

    public int getUserKicked_at() {
        return userKicked_at;
    }

    public void setUserKicked_at(int userKicked_at) {
        this.userKicked_at = userKicked_at;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "UserId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", position='" + position + '\'' +
                ", userType='" + userType + '\'' +
                ", groups=" + groups +
                ", username='" + username + '\'' +
                ", public_key='" + public_key + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userCreated_at=" + userCreated_at +
                ", userUpdated_at=" + userUpdated_at +
                ", avatar=" + avatar +
                ", centerList=" + centerList +
                '}';
    }

    /*
    ---------- Parcelable Implements ----------
    */

    protected UserModel(Parcel in) {
        id = in.readString();
        userId = in.readString();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
        gender = in.readString();
        userStatus = in.readString();
        no_password = in.readByte() != 0;
        position = in.readString();
        userType = in.readString();
        username = in.readString();
        creator = in.readParcelable(UserModel.class.getClassLoader());
        public_key = in.readString();
        birthday = in.readString();
        userCreated_at = in.readInt();
        userUpdated_at = in.readInt();
        userAccepted_at = in.readInt();
        userKicked_at = in.readInt();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(gender);
        dest.writeString(userStatus);
        dest.writeByte((byte) (no_password ? 1 : 0));
        dest.writeString(position);
        dest.writeString(userType);
        dest.writeString(username);
        dest.writeParcelable(creator, flags);
        dest.writeString(public_key);
        dest.writeString(birthday);
        dest.writeInt(userCreated_at);
        dest.writeInt(userUpdated_at);
        dest.writeInt(userAccepted_at);
        dest.writeInt(userKicked_at);
    }

}