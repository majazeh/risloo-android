package com.majazeh.risloo.Utils.Entities;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.mre.ligheh.Model.TypeModel.CaseModel;
import com.mre.ligheh.Model.TypeModel.CenterModel;
import com.mre.ligheh.Model.TypeModel.SampleModel;
import com.mre.ligheh.Model.TypeModel.ScheduleModel;
import com.mre.ligheh.Model.TypeModel.SessionModel;
import com.mre.ligheh.Model.TypeModel.TypeModel;
import com.mre.ligheh.Model.TypeModel.UserModel;

public class Permissoon {

    // Objects
    private final Activity activity;

    /*
    ---------- Intialize ----------
    */

    public Permissoon(@NonNull Activity activity) {
        this.activity = activity;
    }

    /*
    ---------- Main ----------
    */

    public boolean showUsers(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showBulkSamples(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    public boolean showScales(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin") || model.getUserType().equals("operator"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    /*
    ---------- Create ----------
    */

    // -------------------- CreateUser

    public boolean showCreateUserBirthday() {
        return false;
    }

    // -------------------- ReserveSchedule

    public boolean showReserveScheduleClientType(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getAcceptation() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getAcceptation().getPosition().equals("manager") || model.getRoom().getAcceptation().getPosition().equals("operator") || model.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getAcceptation() != null)
            return model.getRoom().getAcceptation().getPosition().equals("manager") || model.getRoom().getAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    public boolean showReserveScheduleCase(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getCenter().getAcceptation().getPosition().equals("client") && model.getRoom().getCenter().getAcceptation().getKickedAt().equals("");
        else
            return false;
    }

    public boolean showReserveScheduleName(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getCenter().getAcceptation().getPosition().equals("client") && !model.getRoom().getCenter().getAcceptation().getKickedAt().equals("");
        else
            return true;
    }

    public boolean showReserveScheduleTreasury(ScheduleModel model) {
        if (model != null && model.getRoom() != null && model.getRoom().getAcceptation() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getAcceptation().getPosition().equals("manager") || model.getRoom().getAcceptation().getPosition().equals("operator") || model.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getCenter() != null && model.getRoom().getCenter().getAcceptation() != null)
            return model.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || model.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (model != null && model.getRoom() != null && model.getRoom().getAcceptation() != null)
            return model.getRoom().getAcceptation().getPosition().equals("manager") || model.getRoom().getAcceptation().getPosition().equals("operator");
        else
            return false;
    }

    /*
    ---------- Edit ----------
    */



    /*
    ---------- Index ----------
    */

    // -------------------- Centers

    public boolean showCentersCreateCenter(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    // -------------------- CenterSchedules

    public boolean showCenterSchedulesFragmentReserveSchedule(UserModel userModel, ScheduleModel scheduleModel) {
        if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator")) || (scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getCenter() != null && scheduleModel.getRoom().getCenter().getAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null && scheduleModel != null && scheduleModel.getRoom() != null && scheduleModel.getRoom().getAcceptation() != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system") || (scheduleModel.getRoom().getAcceptation().getPosition().equals("manager") || scheduleModel.getRoom().getAcceptation().getPosition().equals("operator"));
        else if (userModel != null)
            return !userModel.getUserType().equals("admin") && !userModel.getUserType().equals("system");
        else
            return false;
    }

    // -------------------- Samples

    public boolean showSamplesCreateSample(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    public boolean showSamplesFragmentSample(UserModel userModel, SampleModel sampleModel) {
        if (userModel != null && sampleModel != null && sampleModel.getSampleCase() != null && sampleModel.getSampleCase().getCaseRoom() != null && sampleModel.getSampleCase().getCaseRoom().getAcceptation() != null && sampleModel.getSampleCase().getCaseRoom().getCenter() != null && sampleModel.getSampleCase().getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sampleModel.getSampleCase().getCaseRoom().getAcceptation().getPosition().equals("manager") || sampleModel.getSampleCase().getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || sampleModel.getSampleCase().getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sampleModel != null && sampleModel.getSampleRoom() != null && sampleModel.getSampleRoom().getAcceptation() != null && sampleModel.getSampleRoom().getCenter() != null && sampleModel.getSampleRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sampleModel.getSampleRoom().getAcceptation().getPosition().equals("manager") || sampleModel.getSampleRoom().getCenter().getAcceptation().getPosition().equals("manager") || sampleModel.getSampleRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sampleModel != null && sampleModel.getSampleCase() != null && sampleModel.getSampleCase().getCaseRoom() != null && sampleModel.getSampleCase().getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sampleModel.getSampleCase().getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null && sampleModel != null && sampleModel.getSampleRoom() != null && sampleModel.getSampleRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sampleModel.getSampleRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    // -------------------- Scales

    public boolean showScalesCreateSample(UserModel model) {
        if (model != null) {
            if (model.getUserType().equals("admin"))
                return true;

            if (!model.getCenterList().data().isEmpty())
                for (TypeModel typeModel : model.getCenterList().data()) {
                    CenterModel centerModel = (CenterModel) typeModel;

                    if (centerModel != null && centerModel.getAcceptation() != null)
                        if (centerModel.getAcceptation().getPosition().equals("manager") || centerModel.getAcceptation().getPosition().equals("operator") || centerModel.getAcceptation().getPosition().equals("psychologist"))
                            return true;
                }

        } return false;
    }

    /*
    ---------- Show ----------
    */

    // -------------------- Case

    public boolean showCaseDropdownReports(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null && caseModel.getCaseRoom().getCenter() != null && caseModel.getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseDropdownEdit(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null && caseModel.getCaseRoom().getCenter() != null && caseModel.getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateReference(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null && caseModel.getCaseRoom().getCenter() != null && caseModel.getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSession(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null && caseModel.getCaseRoom().getCenter() != null && caseModel.getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showCaseCreateSample(UserModel userModel, CaseModel caseModel) {
        if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null && caseModel.getCaseRoom().getCenter() != null && caseModel.getCaseRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("manager") || caseModel.getCaseRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && caseModel != null && caseModel.getCaseRoom() != null && caseModel.getCaseRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || caseModel.getCaseRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    // -------------------- Center

    public boolean showCenterDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownEdit(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownRooms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterDropdownAccounting(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showCenterCreateRoom(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager");
        else
            return position.equals("manager");
    }

    // -------------------- Dashboard

    public boolean showDashboardData(UserModel model) {
        if (model != null)
            return !model.getUserType().equals("admin");
        else
            return false;
    }

    // -------------------- Room

    public boolean showRoomDropdownUsers(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownCreateSchedule(UserModel model, String position, String type) {
        if (model != null)
            return (model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator")) && type.equals("room");
        else
            return (position.equals("manager") || position.equals("operator")) && type.equals("room");
    }

    public boolean showRoomDropdownEdit(UserModel model, String position, String type) {
        if (model != null)
            return (model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator")) && !type.equals("room");
        else
            return (position.equals("manager") || position.equals("operator")) && !type.equals("room");
    }

    public boolean showRoomDropdownPlatforms(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownTags(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomDropdownAccounting(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomCreateCase(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || position.equals("manager") || position.equals("operator");
        else
            return position.equals("manager") || position.equals("operator");
    }

    public boolean showRoomCases(UserModel model, String position) {
        if (model != null)
            return model.getUserType().equals("admin") || (!position.equals("psychologist") && !position.equals("client"));
        else
            return !position.equals("psychologist") && !position.equals("client");
    }

    // -------------------- Session

    public boolean showSessionDropdownReports(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionDropdownEdit(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateUser(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreatePractice(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateSample(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showSessionCreateBill(UserModel userModel, SessionModel sessionModel) {
        if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null && sessionModel.getRoom().getCenter() != null && sessionModel.getRoom().getCenter().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("manager") || sessionModel.getRoom().getCenter().getAcceptation().getPosition().equals("operator");
        else if (userModel != null && sessionModel != null && sessionModel.getRoom() != null && sessionModel.getRoom().getAcceptation() != null)
            return userModel.getUserType().equals("admin") || sessionModel.getRoom().getAcceptation().getPosition().equals("manager");
        else if (userModel != null)
            return userModel.getUserType().equals("admin");
        else
            return false;
    }

    /*
    ---------- Tab ----------
    */

    // -------------------- EditUserTabPassword

    public boolean showEditUserTabPasswordCurrent(UserModel model) {
        if (model != null)
            return !model.getUserType().equals("admin") && !model.isNo_password();
        else
            return false;
    }

    // -------------------- EditUserTabPersonal

    public boolean showEditUserTabPersonalMobile(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalEmail(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalBirthday() {
        return false;
    }

    public boolean showEditUserTabPersonalStatus(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

    public boolean showEditUserTabPersonalType(UserModel model) {
        if (model != null)
            return model.getUserType().equals("admin");
        else
            return false;
    }

}