# MapTest

A simple Android app written in Kotlin that downloads a JSON array of geographical data, saves the data to a Room database, and displays the  markers for the data in a MapBox map.

Cards for each geographical point are displayed at the bottom of the map. The cards scroll horizontally. Tapping on a card will scroll the map to the map marker for that point.

Made in about 18 hours.

## Todo

- Add feature to add or remove map pins.
- Move some of the MapPinFragment logic to the MapViewModel.