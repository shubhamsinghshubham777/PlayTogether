# PlayTogether
<img src="https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogetherKMP/main/art/app_logo.png" width=150/>

An open-source `shared media player` that allows multiple participants to watch their local media files together, in real-time.

So now you can watch Movies, TV shows, or any other media with your friends, in real-time as if you were `Play`ing-it-`Together` 💃

**The flow is simple:**

- `Authenticate (Login or Register)` 🔐
- `Create/Join a Room` 🚪
- `Invite Friends` 👯‍♀️
- `Load a media file` 🎬
- `Enjoy!` 🥳

## Work in Progress 🚧
This project is a work in progress and is not in a production (or even working prototype) stage right now. Continuous development is going on but delays are expected. Thank you for your patience 😇

## Screenshots

| Platform | Screenshot |
| --- | --- |
| Android | <img src="https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogether/main/art/screenshots/android.png" width=200/>|
| Desktop | <img src="https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogether/main/art/screenshots/desktop.png" width=600/>|
| iOS | <img src="https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogether/main/art/screenshots/iOS.png" width=200/>|
| JS | <img src="https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogether/main/art/screenshots/js.png" width=600/>|

## Features 🌟

#### Multiplatform 📲 🖥 🌐 
PlayTogether uses Kotlin Multiplatform to support the following platforms:

- **JVM** - Android, Desktop, Server
- **Native** - iOS

#### Compose ❤️
PlayTogether uses common Composables for `Android` and `Desktop` that reduces the UI development effort and leaves the opportunity to introduce iOS and Web for Compose while reusing the same code.

#### Real-Time Interaction ⚡️
PlayTogether uses WebRTC and WebSockets to establish a real-time audio/video/text communication between users to play/pause/seek media together.

## Tech Stack 🚀

- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Ktor (Client and Server)](ktor.io/)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [JetBrains Compose](https://www.jetbrains.com/lp/compose-mpp/) (for Android and Desktop UI)
- [SwiftUI](https://developer.apple.com/documentation/swiftui/) (for iOS UI)
- [KVision](https://kvision.gitbook.io/) (for Web UI)
- [AWS S3](https://aws.amazon.com/sdk-for-kotlin/) (for image storage)

## Architecture
![](https://raw.githubusercontent.com/shubhamsinghshubham777/PlayTogetherKMP/chore/create_readme/art/architecture.png)

## Environment Variables

### Ktor (Server)
To be able to run the server on your local environment, you must add these environment variables to your IDE:

> `Note`: This project uses the PostgreSQL Dialect therefore you will have to set up your own PostgreSQL environment. A simple google search on `setup postgresql on mac` (or any other OS) should help.

- `AWS_BUCKET_NAME`: Sets the AWS bucket name. You will have to create your own AWS account for this (e.g. `mybucket`).
- `AWS_REGION`: Sets the region name for your AWS bucket. You will have to create your own AWS account for this (e.g. `ap-east-1`).
- `DB_HOST`: Hostname for your Postgres DB server (e.g. `localhost`).
- `DB_NAME`: Name of your Postgres DB (e.g. `mydatabase`)
- `DB_PASSWORD`: Password to access your Postgres DB (e.g. `mypassword`).
- `DB_PORT`: Port on which your Postgres DB would be accessible (generally `5432`).
- `DB_USER`: Username that has access to the above said DB instance (e.g. `myuser`).
- `JWT_DOMAIN`: The domain name used by Ktor for generating the Json Web Token (e.g. `http://0.0.0.0:8080`).
- `JWT_ISSUER`: The issuer name used by Ktor to ensure who has created the JWT (e.g. `http://0.0.0.0:8080`).
- `JWT_SECRET`: The secret phrase that is used to decrypt a JWT and obtain the contained data (e.g. `mysecret`).
- `SERVER_PORT`: The port on which your Ktor server runs (e.g. `8080`).

> Be sure to replace these sample values with your own real values.

#### For IntelliJ IDEA (or Android Studio)
- open `Run/Debug Configurations`
- create a new `gradle` configuration
- type `run` in the `Run` section
- select `PlayTogetherKMP:run` in the `Gradle project` section
- Paste the following in the `Environment variables` section: **SERVER_PORT=8080;AWS_REGION=ap-east-1;AWS_BUCKET_NAME=mybucket;DB_PORT=5432;JWT_SECRET=mysecret;DB_USER=myuser;JWT_ISSUER=http://0.0.0.0:8080;JWT_DOMAIN=http://0.0.0.0:8080;DB_NAME=mydatabase;DB_HOST=localhost;DB_PASSWORD=mypassword**
- **Optional** - Paste the following in the `VM options` section if you want to see all dev logs while Ktor is running: `-Dio.ktor.development=true`

## License
[GNU GPL](https://github.com/shubhamsinghshubham777/PlayTogether/blob/main/LICENSE)
