# J.A.M.M.A (Just Another Money Management App)

J.A.M.M.A is a clean, modern, and intuitive personal finance tracker for Android. Built with the
latest Android technologies, it helps you keep track of your income, expenses, and recurring
transactions with ease.

## 🚀 Features

- **Transaction Tracking**: Log your income and expenses quickly.
- **Recurring Transactions**: Support for Daily, Weekly, Monthly, and Yearly recurrences to automate
  your budget tracking.
- **Categorization**: Organize your spending with specific categories.
- **Dashboard Overview**: Get a quick glance at your financial health.
- **Modern UI**: A sleek, Material 3 design built entirely with Jetpack Compose.
- **Offline First**: All data is stored locally using Room database.

## 🛠 Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Design System**: [Material 3](https://m3.material.io/)
- **Architecture**: Clean Architecture with MVVM
- **Dependency Injection**: [Koin](https://insert-koin.io/)
- **Local Database**: [Room](https://developer.android.com/training/data-storage/room) (with KSP)
- **Background Work
  **: [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- **Image/Animation**: [Lottie](https://airbnb.io/lottie/)
- **Logging**: [Timber](https://github.com/JakeWharton/timber)
- **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

## 🏗 Project Structure

The project follows Clean Architecture principles, separated into:

- **Presentation**: Composable screens and ViewModels.
- **Domain**: Use Cases, Repository Interfaces, and Business Entities.
- **Data**: Repository implementations, Local Data Sources (Room), and Workers.

<!-- 
## 📸 Screenshots

*(Add your screenshots here to make your repository stand out!)*

<table>
  <tr>
    <td><img src="https://via.placeholder.com/200x400?text=Dashboard" width="200" alt="Dashboard Screen"></td>
    <td><img src="https://via.placeholder.com/200x400?text=Transactions" width="200" alt="Transactions List"></td>
    <td><img src="https://via.placeholder.com/200x400?text=Add+Transaction" width="200" alt="Add Transaction Screen"></td>
  </tr>
</table>
--->
## 🏁 Getting Started

### Prerequisites

- Android Studio Koala | 2024.1.1 or newer
- JDK 17+
- Android SDK 24+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/vanilson/J.A.M.M.A-Android.git
   ```
2. Open the project in Android Studio.
3. Sync Project with Gradle Files.
4. Run the `app` module on an emulator or physical device.

## 🤝 Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git checkout -b feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Made with ❤️ by [Vanilson Fernandes](https://github.com/vanilson)
