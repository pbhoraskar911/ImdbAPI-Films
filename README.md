# ImdbAPI-Films (Under Development)

The following app is the part of the Udacity Android Developer Nanodegree. Features - 
  - The app fetches the data from The MovieDb api and displays latest, popular and highest rated movies to the user.
  - Displays details about the movie such as Name, Release Date, Rating and Poster. (New Fratures coming)
  
Getting API_KEY for the project and adding to the project -
  - Sign in to TheMovieDb api get and your api key for the project. 
  - Open the file "gradle.properties" in the root directory.
  - Replace API_KEY with your original api key.

Technical features used - 
  - Dependency Injection (Dagger2) to separate configuration and UI
  - Butterknife for view injection and Picasso for Image loading
  - Retrofit2 and Gson for making API calls.
  - Recycler view and Cardview - to display list of repositories and contributors. 
  - Animation for the recycler view.
  - Bottom Navigation menu for easy navigation.

Quality Assurance
  - Checkstyle, Findbugs, PMD and Lint for static code analysis
