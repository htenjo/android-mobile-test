
package co.zero.android.armyofones.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {

    @SerializedName("GBP")
    @Expose
    private Double gbp;
    @SerializedName("JPY")
    @Expose
    private Double jpy;
    @SerializedName("EUR")
    @Expose
    private Double eur;

    /**
     * 
     * @return
     *     The gbp
     */
    public Double getGBP() {
        return gbp;
    }

    /**
     * 
     * @param gbp
     *     The GBP
     */
    public void setGBP(Double gbp) {
        this.gbp = gbp;
    }

    /**
     * 
     * @return
     *     The jpy
     */
    public Double getJPY() {
        return jpy;
    }

    /**
     * 
     * @param jpy
     *     The JPY
     */
    public void setJPY(Double jpy) {
        this.jpy = jpy;
    }

    /**
     * 
     * @return
     *     The eur
     */
    public Double getEUR() {
        return eur;
    }

    /**
     * 
     * @param eur
     *     The EUR
     */
    public void setEUR(Double eur) {
        this.eur = eur;
    }

}
