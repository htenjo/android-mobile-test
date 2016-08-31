# Android-mobile-test
This is the solution given to a mobile test at H

## How it works?
You can just click on the "Convert" button to get the current Rates. If you need to convert an specific
amount of dollars you can write it in the text field and click on the key "Done" or in the button "Convert",
then the exchange values should be updated next to the flag of each country and the chart should update the values.
The app takes care of your resources, because of that it verify if there are previous values saved in
the internal database, if that is true then it uses that values, otherwise get the values from the
external service and save them in the database for future uses! If you need to get the last exchanges rates
of the day, just click on the "Update Rates" in the menu (You need internet for this action).

## Features:
- Specific icon that represents the App (Branded Icon)
- Splash screen: This activity shows the brand (Huge) in an animated way each time the app is opened!
- Menu options:
    - Update Rates: Force the call to the remote service to update the rate values
    - Daily Chart: Configure the chart to show the different values of the day for each currency
    - Monthly Chart: Configure the chart to show the average values per day for each currency in the month
- Text field: Field where the user can write an amount of dollars to convert to each currency
- Country Rates: Each country is represented by its flag followed by the current exchange rate and the converted money
- Chart: The chart can display the following information depending of the configured option
    - Different exchange rate values per current day
    - Average x day of the exchange rate values in the current month
- Convert button: Update the dollars converted with the local rates and display the chart

### UnitTest:
- Only the utility methods have been tested