package com.xishatou.bean;

/**
 * Created by Administrator on 2018/12/5.
 */

public class Heart {
    //应答流水号(2字节,4位)
//    String Response_Flowcode;
    //应答ID(3字节,6位)
//    String Response_ID;
    //车牌号(10字节,20位)
    String  Plate_number;
    //定位状态(1字节,2位)
    String Positioning_state;
    //纬度(4字节,8位)
    String  Latitude;
    //经度(4字节,8位)
    String  Longitude;
    //速度(2字节,4位)
    String  Speed;
    //车型(1字节,2位)
    String  Vehicle_type;
     //车辆状态(1字节,2位)
     String  Vehicle_condition;
    //车辆优先级(1字节,2位)
    String  Vehicle_priority;
    //特殊车辆信息(1字节,2位)
    String  Special_vehicle_information;
    //SOS救援信息(1字节,2位)
    String  SOS;
    //应答时间(6字节,12位)
    String  Response_Time;

    public String getPositioning_state() {
        return Positioning_state;
    }

    public void setPositioning_state(String positioning_state) {
        Positioning_state = positioning_state;
    }

    public   String    PutData(String plate_number, String positioning_state, String latitude, String longitude, String speed, String vehicle_type, String vehicle_condition, String vehicle_priority, String special_vehicle_information, String SOS, String response_Time) {
        Positioning_state=positioning_state;
        Plate_number = plate_number;
        Latitude = latitude;
        Longitude = longitude;
        Speed = speed;
        Vehicle_type = vehicle_type;
        Vehicle_condition = vehicle_condition;
        Vehicle_priority = vehicle_priority;
        Special_vehicle_information = special_vehicle_information;
        this.SOS = SOS;
        Response_Time = response_Time;
   return      Plate_number+Positioning_state+Latitude+Longitude+Speed+Vehicle_type+Vehicle_condition+Vehicle_priority+
          Special_vehicle_information+SOS+Response_Time;

    }





    public String getPlate_number() {
        return Plate_number;
    }

    public void setPlate_number(String plate_number) {
        Plate_number = plate_number;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getSpeed() {
        return Speed;
    }

    public void setSpeed(String speed) {
        Speed = speed;
    }

    public String getVehicle_type() {
        return Vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        Vehicle_type = vehicle_type;
    }

    public String getVehicle_condition() {
        return Vehicle_condition;
    }

    public void setVehicle_condition(String vehicle_condition) {
        Vehicle_condition = vehicle_condition;
    }

    public String getVehicle_priority() {
        return Vehicle_priority;
    }

    public void setVehicle_priority(String vehicle_priority) {
        Vehicle_priority = vehicle_priority;
    }

    public String getSpecial_vehicle_information() {
        return Special_vehicle_information;
    }

    public void setSpecial_vehicle_information(String special_vehicle_information) {
        Special_vehicle_information = special_vehicle_information;
    }

    public String getSOS() {
        return SOS;
    }

    public void setSOS(String SOS) {
        this.SOS = SOS;
    }

    public String getResponse_Time() {
        return Response_Time;
    }

    public void setResponse_Time(String response_Time) {
        Response_Time = response_Time;
    }
}
