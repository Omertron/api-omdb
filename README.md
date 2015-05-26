The Open Movie Database API
===========================

Author: Stuart Boston (Omertron AT Gmail DOT com)

This API uses the [omdbapi.com API](http://omdbapi.com/)

Originally written for use by YetAnotherMovieJukebox ([YAMJv2](https://github.com/YAMJ/yamj-v2) & [YAMJv3](https://github.com/YAMJ/yamj-v3)), but anyone can feel free to use it for other projects as well.

[![Build Status](http://jenkins.omertron.com/job/API-OMDB/badge/icon)](http://jenkins.omertron.com/job/API-OMDB)

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

Then call the `search` or `getInfo` methods to get what you are looking for.

This API uses a builder function to allow you to specify all the parameters that are relevant to your search.

For example, searching for "Star Wars"

    SearchResults results = omdb.search(new OmdbBuilder().setSearchTerm("Star Wars").build());

This will yield a list of all movies that match "Star Wars".
If there are no results or an error, the `results.isResponse()` will be `false`.

You can also get information for a specific movie using the IMDB ID or title & year:

    OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setImdbId("tt0083658");
    OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle("Blade Runner").build());
    OmdbVideoFull result = omdb.getInfo(new OmdbBuilder().setTitle("Blade Runner").setYear(1982).build());

###Builder Methods
By default results are returned for all video types (movie, series & episode), the plot is short and Rotten Tomatoes information is omitted from the results.

You can change this behaviour with the OmdbBuilder call, the following methods are supported:
####Main Parameters
Only one of these should be chosen

    .setSearchTerm(string)  // Setting this will perform a search
    .setTitle(string)       // Setting this will get information on the title
    .setImdbId(string)      // Setting this will get information on the IMDB ID
####Additional Parameters
The following parameters will limit the searches returned:

    .setYear(integer)           // The year
    .setResultType(ResultType)  // One of ALL (default), MOVIE, SERIES or EPISODE
    .setTypeMovie()             // A shortcut for the ResultType.MOVIE
    .setTypeSeries()            // A shortcut for the ResultType.SERIES
    .setTypeEpisode()           // A shortcut for the ResultType.EPISODE
    .setPlot(PlotType)          // One of SHORT or LONG for the length of the plot
    .setPlotLong()              // A shortcut for the PlotType.LONG
    .setPlotShort()             // A shortcut for the PlotType.SHORT
    .setTomatoes(boolean)       // Include or exclude Rotten Tomatoes ratings
    .setTomatoesOn()            // Include Rotten Tomatoes ratings
    .setTomatoesOff()           // Exclude Rotten Tomatoes ratings

####Examples
Get information on Blade Runner with long plot and Rotten Tomatoes information:

    OmdbVideoFull result = omdb.getInfo(new OmdbBuilder()
        .setTitle("Blade Runner")
        .setYear(1982)
        .setPlot(PlotType.LONG)
        .setTomatoes(true)
        .build());

The following call does the same using the IMDB ID and the shortcut calls:

    OmdbVideoFull result = omdb.getInfo(new OmdbBuilder()
        .setImdbId("tt0083658")
        .setPlotLong()
        .setTomatoesOn()
        .build());

***
Project Logging
---------------
This project uses [SLF4J](http://www.slf4j.org) to abstract the logging in the project.

To use the logging in your own project you should add one of the bindings listed [HERE](http://www.slf4j.org/manual.html#swapping)

Project Documentation
---------------------
The automatically generated documentation can be found [HERE](http://omertron.github.com/api-omdb/)
