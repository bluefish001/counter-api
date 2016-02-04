Running Environment:
   JDK 1.7
   Spring 4
   Tomcat 7

File:
   File location is in application.properties file which in src/main/resource, please change the value before run it


Solution:

  solution1: for word searching only
      1. Load all word that user searched to the list
      2. Check whole file word by word, if the word existing in the list, the number increase by one
      3. Return the list

  solution2: can be used for word searching and get top N word
      1. Read whole file and calculate every word 
      2. Sort them by the numbers of word and store in memory
      3. When do search, search the memory and get correspond word 
      4. When get top number of word, because the list has been sort, only need return sub list according to the number


How to run
      1. Download the project counter-api
      2. Run mvn --install will create counter-api.war in target folder
      3. Copy counter-api.war to tomcat directory
      4. Use http://{hostname:port}/counter-api/search.json or http://{hostname:port}/counter-api/search for search
      5. Use http://{hostname:port}/counter-api/top/{N} for get top N word
