# Web Page Cleaner

This project search web pages in the web by keywords using [SERP API](https://wwww.serpapi.com), PhantomJs to get all page content (static and dynamically content) and Jsoup to clean the web page (remove ads, images, etc).

#### Project setup

_____

**Windows**
1. Execute the commands bellow:

```bash
git clone https://github.com/kaio-giovanni/webpage-cleaner.git
cd webpage-cleaner
./gradlew clean build
```

2. Create a Serp API account and get the API key.

3. Create an `.env` file in the project root and enter your credentials based on the `.env.example` file.

_____

**Linux**

1. Execute the commands bellow:

```bash
git clone https://github.com/kaio-giovanni/webpage-cleaner.git
cd webpage-cleaner
./gradlew clean build
```

2. Install Web driver

   2.1 Using docker (Chrome Driver)

   ```bash

   docker pull selenium/standalone-chrome
   docker run -d -p 4444:4444 -v /dev/shm:/dev/shm selenium/standalone-chrome
   ```

   2.2 Installing on ubuntu

   ```bash

   RUN apt-get install chromium-driver -y
   ```

3. Add path of web driver in PropertiesUtils.java class
4. Create a Serp API account and get the API key.
5. Create an `.env` file in the project root and enter your credentials based on the `.env.example` file.


#### Run project

1. Execute the commands bellow:

```bash
./gradlew bootRun
```

#### Author

| ![user](https://avatars1.githubusercontent.com/u/64810260?v=4&s=150) |
| ----------------------------- |
| <p align="center"> <a href="https://github.com/kaio-giovanni"> @kaio-giovanni </a> </p>|
