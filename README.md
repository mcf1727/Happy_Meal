# About this project "Happy meal"
We all want to eat healthy and keep fit. But how to know which nutrients the food contains. Food plays an important role in health as well as in disease. Before eating, you can use this APP to measure the calories and nutrients in the food.

## As a developer, what I used to build this project:
- Make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle). LiveData is used to observe changes in the database and update the UI accordingly. ViewModel are used when required and no unnecessary calls to the database are made
- Room database and sharedPreference to handle data persistence
- Retrofit to fetch Data from API
- Implement Google play services: Google Ads Admob, Firebase Crashlytics, Analytics
- Home screeen widget to dynamically display last opened food detail page
- Deal edge or corner cases for the search feature
- App applys Material Design and use an app bar and associated toolbars, simple transitions between activities
- App builds and deploys using the `installRelease`  `Gradle` task
- App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.
- App includes support for accessibility including content descriptions for pictures
- App uses Asynctask to load a progress bar after launching a search
- Realize User Interface Mocks with the help of www.ninjamock.com before building the APP

## Why this Project

To become a proficient Android Developer, I need to design apps and make plans for how to implement them. This will involve choices such as how to store data, how to display data to the user, and what functionality to include in the app.
In this project, I demonstrate also the skills I've learned in the Android developer Nanodegree journey, and apply them to creating a unique app experience of my own. By the end of this project, I  have an app to be able to be submit to the Google Play Store for distribution.

## Overview
In the Capstone project, I  build my own app of my own design in two stages. In Stage 1, I design and plan the app shown in the PDF file "Capstone_Stage1". UI flow mocks are also given. 
In stage 2, it's time to build it!

- Before launch the project, please go to the website https://developer.edamam.com/edamam-nutrition-api to ask for a Nutrition Analysis API key.
Then replace the APP_ID and APP_KEY in "DetailActivity" by yours. After that, you will be able to build the project.

- UI Main Activity
<div align="center">
    <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo1.jpg"/>     <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo2.jpg"/>
</div>

- UI Detail Activity and home screen widget
<div align="center">
    <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo4.jpg"/>     <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo5.jpg"/>
</div>

- UI Search Activity
<div align="center">
    <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo3.jpg"/>     <img width="300" alt="layout_phone" src="https://github.com/mcf1727/happy_meal/blob/master/photos/photo6.jpg"/>
</div>

## Intended User
Whether your goal is to get fit, lose weight or gain weight, use this APP to measure the food so that you know what you will be eating.
The targeted audience age range is 25-40 years old.