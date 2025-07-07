📘 **DriveMate - UK Theory Practice App**

DriveMate is a modern Android application built with a clean and scalable architecture that allows users to practice the official UK Driving Theory Test. The app offers categorized mock questions for car drivers, all fetched from a structured public dataset.

📱 **Features**

🚗 Practice official driving theory test questions

📂 Categorized questions (Car, Motorcycle, Lorry, Bus)

🔁 Randomized quiz generation

📝 Review correct answers after each session

📊 Score tracking and session history (coming soon)

🧱 **Built With**

| Layer                    | Tech Stack                                             |
| ------------------------ | ------------------------------------------------------ |
| **UI**                   | Jetpack Compose, Material 3                            |
| **Architecture**         | Clean Architecture (Presentation, Domain, Data layers) |
| **Networking**           | Retrofit, Kotlin Coroutines, OkHttp                    |
| **Dependency Injection** | Hilt                                                   |
| **Persistence**          | Room Database                                          |
| **Others**               | Coil (for image loading), ViewModel, LiveData/Flow     |


🧩 **Architecture Overview**

The project follows Clean Architecture, ensuring each module has a single responsibility and is easy to scale, test, and maintain:

Presentation (UI)

↑

Domain (UseCases, Entities)

↑

Data (Repositories, Network & Cache)

👨‍💻 **Author**

Rahmon Akanbi

Android Developer passionate about clean code and user-first design

