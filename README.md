##System

###Design and implement a basic mobile music recommendation service. 

- Have a prepopulated list of artists for when the user first signs up (either based on user's favorite genre, top 40, etc) 
- Users should be able to search for artists using https://developer.spotify.com/ on their mobile device (bonus points if you can play songs)
- You can use the echonest API for more metadata (http://static.echonest.com/enspex/)
- Recommend more relevant artists based on user behavior (clicks, search, etc)
- Displayed artists should somehow be scored (up to you) and only the top 5-10 relevant artists should be shown to the user
- The set of recommended artists should be explorable from the mobile interface (method of search, categorization, indexing, retrieval is up to you)
- Don't worry about necessarily implementing any machine learning - focus more on simple, but effective methods of determining relevance 
- There are lots of artists out there - see if you can make some simplifying assumptions to reduce the amount of data you have to deal with 

Some questions to think about:
- What metrics should you be monitoring to make sure that this system is working as it should? How would you optimize performance?
- How would you get smarter about searching over time?
- How do make sure that users are seeing the correct types of artists (that your search and/or categorization algorithms are working)?
- What metrics would you capture to quantify this product and measure user engagement and measure relevance?
- How would you scale search and think about reliability, performance when 100,000 users are on the mobile app?

###Coding:
Build an MVP (minimum viable product) of this. Deploy your backend/API to something like heroku, and send us a testflight invite or apk for the mobile interface. Don't worry too much about making the mobile interface fancy, if that's not your strength. 

*We'll cover any costs incurred during this*
