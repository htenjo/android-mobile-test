
package co.zero.android.armyofones.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Rates {

    @SerializedName("BRL")
    @Expose
    private Double bRL;
    @SerializedName("GBP")
    @Expose
    private Double gBP;
    @SerializedName("JPY")
    @Expose
    private Double jPY;
    @SerializedName("EUR")
    @Expose
    private Double eUR;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
