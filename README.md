The Open Movie Database API
===========================

Author: Stuart Boston (Omertron AT Gmail DOT com)

This API uses the [omdbapi.com API](http://omdbapi.com/)

Originally written for use by Yet Another Movie Jukebox [(YAMJ)](http://code.google.com/p/moviejukebox/)

But anyone can use it for other projects as well.

[![Flattr this git repo](http://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=Omertron&url=https://github.com/Omertron/api-omdb&title=Open Movie Database API&language=&tags=github&category=software)

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/Omertron/api-omdb/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

***
### OMDBAPI.com
This is an excellent open database for movie and film content.

I *strongly* encourage you to check it out and contribute to keep it growing.

### http://www.omdbapi.com
***
Project Usage
-------------

The API usage is very simple. Just instantiate the object:

    OmdbApi omdb = new OmdbApi();

Then call one of the search or movie info methods to get what you are looking for.
For example, searching for "Star Wars"

    WrapperSearch result = omdb.search("Star Wars");

will yield a list of all movies that match "Star Wars".
If there are no results or an error, the `result.isResponse()` will be `false`.

You can also search for a specific movie using the IMDB ID or title & year

    OmdbVideoFull result = omdb.movieInfo("tt0083658");
    OmdbVideoFull result = omdb.movieInfo("Blade Runner");
    OmdbVideoFull result = omdb.movieInfo("Blade Runner", 1982);

###Configuration
By default the plot returned is short and Rotten Tomatoes information is omitted from the MovieInfo results.
You can change the default settings for both of these, which will affect all the results returned, by using:

    omdb.setTomatoes(true);     // Add Rotten Tomatoes information
    omdb.setTomatoes(false);    // Don't add Rotten Tomatoes information (default)
    omdb.setLongPlot();         // Return the longer plot
    omdb.setShortPlot();        // Return the short plot (default)

There is a method for movie information that allows you to specify all of these in the method call:

    boolean rottenTomsRequired = true;
    OmdbVideoFull result = omdb.movieInfo("Blade Runner", 1982, PlotType.LONG, rottenTomsRequired);


***
Project Logging
---------------
This project uses [SLF4J](http://www.slf4j.org) to abstract the logging in the project.

To use the logging in your own project you should add one of the bindings listed [HERE](http://www.slf4j.org/manual.html#swapping)

Project Documentation
---------------------
The automatically generated documentation can be found [HERE](http://omertron.github.com/api-omdb/)
