
package co.zero.android.armyofones.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import co.zero.android.armyofones.util.Constants;

public class CurrencyBase implements Serializable{

    @SerializedName(Constants.POJO_FIELD_BASE)
    @Expose
    private String base;
    @SerializedName(Constants.POJO_FIELD_DATE)
    @Expose
    private String date;
    @SerializedName(Constants.POJO_FIELD_RATES)
    @Expose
    private Rates rates;

    /**
     * 
     * @return
     *     The base
     */
    public String getBase() {
        return base;
    }

    /**
     * 
     * @param base
     *     The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The rates
     */
    public Rates getRates() {
        return rates;
    }

    /**
     * 
     * @param rates
     *     The rates
     */
    public void setRates(Rates rates) {
        this.rates = rates;
    }

}
