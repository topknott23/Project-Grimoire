# Project Grimoire

I've nothing to do and decided to throw this together to see if I could make a desktop app talk to Google's Gemini AI without the UI freezing up. It's mostly just a sandbox project I built for fun to mess around with JavaFX and backend API calls.

## What's in it
If you actually want to run it, there are a few random tools I built into the dashboard:
* **Game Guide:**A simple chat interface I use to look up game strats without having to alt-tab to a browser. It runs on a background thread so the app doesn't lock up.(using AI)
* **RAM Optimizer:** I was getting annoyed with Minecraft modpack lag, so I made this to calculate the exact JVM garbage collection arguments needed for different PC specs.
* **World Builder:** This was mostly just an experiment to see if I could force the AI to return strict JSON data instead of normal text, and then have the Java code parse it to build UI cards dynamically.

## How it's built

* Java 21
* JavaFX for the UI (with some custom CSS to make it dark mode)
* Maven
* Google Gemini API for the brains

## Running it
If you want to mess around with it on your own machine, it's pretty simple to set up:
1. Clone the repo.
2. Get a free API key from Google AI Studio. 
3. Rename the `.env.example` file to `.env` and paste your key in there (I don't push my actual keys to GitHub).
4. Run `mvn clean javafx:run` in your terminal.

askdaoshdoashdoashdoashdoasihdoiashdoashdoashodashidoaishdasd
