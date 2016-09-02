
package co.zero.android.armyofones.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

import co.zero.android.armyofones.util.Constants;

public class Rates implements Serializable{
    @SerializedName(Constants.POJO_FIELD_BRL)
    @Expose
    private Double bRL;
    @SerializedName(Constants.POJO_FIELD_GBP)
    @Expose
    private Double gBP;
    @SerializedName(Constants.POJO_FIELD_JPY)
    @Expose
    private Double jPY;
    @SerializedName(Constants.POJO_FIELD_EUR)
    @Expose
    private Double eUR;

    private Date date;

    /**
     *
     * @return
     * The bRL
     */
    public Double getBRL() {
        return bRL;
    }

    /**
     *
     * @param bRL
     * The BRL
     */
    public void setBRL(Double bRL) {
        this.bRL = bRL;
    }

    /**
     *
     * @return
     * The gBP
     */
    public Double getGBP() {
        return gBP;
    }

    /**
     *
     * @param gBP
     * The GBP
     */
    public void setGBP(Double gBP) {
        this.gBP = gBP;
    }

    /**
     *
     * @return
     * The jPY
     */
    public Double getJPY() {
        return jPY;
    }

    /**
     *
     * @param jPY
     * The JPY
     */
    public void setJPY(Double jPY) {
        this.jPY = jPY;
    }

    /**
     *
     * @return
     * The eUR
     */
    public Double getEUR() {
        return eUR;
    }

    /**
     *
     * @param eUR
     * The EUR
     */
    public void setEUR(Double eUR) {
        this.eUR = eUR;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
