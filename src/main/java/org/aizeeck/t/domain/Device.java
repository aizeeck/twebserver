package org.aizeeck.t.domain;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "getAllDeviceMonthly",
                procedureName = "get_monthly_all",
                resultClasses = Device.class,
                parameters = {
                        @StoredProcedureParameter(name = "startDay", type = Integer.class, mode = ParameterMode.IN)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "getDeviceHourly",
                procedureName = "get_hourly",
                resultClasses = Device.class,
                parameters = {
                        @StoredProcedureParameter(name = "tId", type = String.class, mode = ParameterMode.IN)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "getDeviceDaily",
                procedureName = "get_daily",
                resultClasses = Device.class,
                parameters = {
                        @StoredProcedureParameter(name = "tId", type = String.class, mode = ParameterMode.IN)
                }
        ),
        @NamedStoredProcedureQuery(
                        name = "getAllDeviceDaily",
                        procedureName = "get_daily_all",
                        resultClasses = Device.class,
                        parameters = {}
                )
})
@Entity
 public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private DeviceType type;
    private String tId;
    private String deviceSerial;
    private String name;
    private String mode;
    private String power_sw;
    private short watts;
    private double stc;
    private String tz;
    private String text;
    private double gradus;
    private double ref_gradus;
    private String heater_state;
    private Date date;
    private Date obsDate;
    private int sum;

    public Device() {
    }

    public Device(JSONObject devJSONObject) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            sum = devJSONObject.getInt("sum");

            JSONObject jsonDeviceStatus = devJSONObject.getJSONObject("DeviceStatus");

            type = DeviceType.valueOf(devJSONObject.getJSONObject("HardSettings").getString("Type").toUpperCase());
            tId = devJSONObject.getString("id");
            deviceSerial = devJSONObject.getString("DeviceSerial");
            name = devJSONObject.getString("DeviceShortName");

            heater_state = jsonDeviceStatus.getString("heater_state");
            mode = jsonDeviceStatus.getString("mode");
            power_sw = jsonDeviceStatus.getString("power_sw");
            gradus = Double.parseDouble(jsonDeviceStatus.getString("gradus"));
            watts = Short.parseShort(jsonDeviceStatus.getString("watts"));
            ref_gradus = Double.parseDouble(jsonDeviceStatus.getString("ref_gradus"));
            date = formatter.parse(jsonDeviceStatus.getString("date"));
            obsDate = new Date();
            text = jsonDeviceStatus.getString("Text");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPower_sw() {
        return power_sw;
    }

    public void setPower_sw(String power_sw) {
        this.power_sw = power_sw;
    }

    public short getWatts() {
        return watts;
    }

    public void setWatts(short watts) {
        this.watts = watts;
    }

    public double getStc() {
        return stc;
    }

    public void setStc(double stc) {
        this.stc = stc;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getGradus() {
        return gradus;
    }

    public void setGradus(double gradus) {
        this.gradus = gradus;
    }

    public double getRef_gradus() {
        return ref_gradus;
    }

    public void setRef_gradus(double ref_gradus) {
        this.ref_gradus = ref_gradus;
    }

    public String getHeater_state() {
        return heater_state;
    }

    public void setHeater_state(String heater_state) {
        this.heater_state = heater_state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getObsDate() {
        return obsDate;
    }

    public void setObsDate(Date obsDtate) {
        this.obsDate = obsDtate;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
