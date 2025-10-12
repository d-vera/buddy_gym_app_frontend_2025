# Fitness Studio Android App

A comprehensive Android fitness tracking application built with Java that connects to a REST API backend for managing workouts, exercises, and training sessions.

## Features

- **User Authentication**: Register and login with email/username and password
- **Muscle Group Selection**: Browse and select muscle groups to work on
- **Exercise Management**: Create, edit, and delete exercises for each muscle group
- **Training Registration**: Record training sessions with weight, sets, and reps
- **Training Statistics**: View low, high, and last weight for each exercise
- **Training History**: View complete training history with filtering options
- **Performance Analytics**: Track performance metrics by week, month, and year with visual charts

## Technology Stack

- **Language**: Java
- **Architecture**: Activity-based with Fragments
- **Networking**: Retrofit 2.9.0 with Gson converter
- **Charts**: MPAndroidChart v3.1.0
- **UI Components**: Material Design, RecyclerView, ViewPager2, TabLayout
- **Authentication**: Token-based (stored in SharedPreferences)

## Project Structure

```
app/src/main/java/com/example/buddy_gym_app_frontend_2025/
├── activities/
│   ├── LoginActivity.java          (Activity 1 - Login)
│   ├── RegisterActivity.java       (Activity 3 - Registration)
│   ├── HomeActivity.java           (Activity 2 - Main Screen)
│   ├── MuscleSelectionActivity.java (Activity 4 - Muscle Selection)
│   ├── ExerciseListActivity.java   (Activity 5 - Exercise Management)
│   ├── TrainingActivity.java       (Activity 6 - Training Registration)
│   └── HistoryActivity.java        (Activity 7 - History & Performance)
├── adapters/
│   ├── MuscleAdapter.java
│   ├── ExerciseAdapter.java
│   ├── TrainingHistoryAdapter.java
│   └── HistoryPagerAdapter.java
├── api/
│   ├── RetrofitClient.java
│   ├── AuthService.java
│   ├── MuscleService.java
│   ├── ExerciseService.java
│   └── TrainingService.java
├── fragments/
│   ├── HistoryFragment.java
│   └── PerformanceFragment.java
├── models/
│   ├── User.java
│   ├── Muscle.java
│   ├── Exercise.java
│   ├── Training.java
│   ├── TrainingStats.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   └── RegisterRequest.java
└── utils/
    └── TokenManager.java
```

## API Configuration

The app connects to a backend API running at:
- **Base URL**: `http://10.0.2.2:8000/api/` (Android emulator localhost)
- For physical devices, change to your computer's IP address in `RetrofitClient.java`

### API Endpoints Used

- `POST /auth/login/` - User login
- `POST /auth/register/` - User registration
- `GET /auth/profile/` - Get user profile
- `GET /muscles/` - List all muscle groups
- `GET /exercises/?muscle={id}` - List exercises for a muscle
- `POST /exercises/` - Create new exercise
- `PUT /exercises/{id}/` - Update exercise
- `DELETE /exercises/{id}/` - Delete exercise
- `GET /trainings/` - List all trainings
- `POST /trainings/` - Create new training
- `GET /trainings/history/` - Get training history with filters
- `GET /trainings/stats/` - Get training statistics

## Setup Instructions

1. **Prerequisites**:
   - Android Studio (latest version)
   - JDK 11 or higher
   - Backend API running at `http://localhost:8000/api/`

2. **Clone and Open**:
   ```bash
   git clone <repository-url>
   cd buddy_gym_app_frontend_2025
   ```
   Open the project in Android Studio

3. **Sync Gradle**:
   - Android Studio will automatically sync Gradle dependencies
   - If not, click "Sync Project with Gradle Files"

4. **Configure Backend URL** (if needed):
   - Open `app/src/main/java/com/example/buddy_gym_app_frontend_2025/api/RetrofitClient.java`
   - Change `BASE_URL` if your backend is running on a different address

5. **Run the App**:
   - Connect an Android device or start an emulator
   - Click "Run" or press Shift+F10

## User Flow

1. **Login/Register**:
   - Launch app → Login screen
   - New users: Click "Sign Up" → Fill registration form → Return to login
   - Existing users: Enter email/username and password → Sign In

2. **Start Workout**:
   - After login → Home screen with user name
   - Click green button → Select muscle group

3. **Manage Exercises**:
   - Select muscle → View exercises for that muscle
   - Add new exercise: Click FAB (+) button
   - Edit exercise: Click edit icon
   - Delete exercise: Click delete icon

4. **Record Training**:
   - Click on an exercise → Training screen
   - View stats: Low Weight, High Weight, Last Weight
   - Enter: Actual Weight, Sets, Reps
   - Click "Insert" to save training

5. **View History & Performance**:
   - From training screen → Click "History"
   - Switch between "History" and "Performance" tabs
   - History: View all past trainings in table format
   - Performance: View metrics and yearly chart

## Key Features Implementation

### Authentication
- Token-based authentication using JWT
- Token stored securely in SharedPreferences
- Auto-login if valid token exists
- Token included in all API requests via OkHttp interceptor

### Exercise Management
- Full CRUD operations (Create, Read, Update, Delete)
- Filtered by muscle group
- Custom dialogs for add/edit/delete operations

### Training Tracking
- Real-time statistics display
- Historical data with date formatting
- Performance metrics with visual charts
- Period filtering (current week, last week, last month, last year)

### UI/UX
- Material Design components
- Custom color scheme matching mockups
- Responsive layouts with ScrollView for smaller screens
- RecyclerView for efficient list rendering
- ViewPager2 with TabLayout for tabbed interface

## Dependencies

```gradle
// Retrofit for API communication
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

// Gson for JSON parsing
implementation 'com.google.code.gson:gson:2.10.1'

// MPAndroidChart for performance charts
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

// AndroidX libraries
implementation 'androidx.appcompat:appcompat'
implementation 'com.google.android.material:material'
implementation 'androidx.constraintlayout:constraintlayout'
```

## Troubleshooting

### Cannot connect to backend
- Ensure backend is running at `http://localhost:8000`
- For emulator: Use `10.0.2.2` instead of `localhost`
- For physical device: Use your computer's IP address
- Check `android:usesCleartextTraffic="true"` in AndroidManifest.xml

### Build errors
- Clean project: Build → Clean Project
- Rebuild: Build → Rebuild Project
- Invalidate caches: File → Invalidate Caches / Restart

### Network errors
- Check internet permissions in AndroidManifest.xml
- Verify backend API is accessible
- Check Logcat for detailed error messages

## Future Enhancements

- Offline mode with local database
- Social login integration (Twitter, Facebook)
- Push notifications for workout reminders
- Export training data to CSV
- Dark mode support
- Multi-language support

## License

This project is part of a fitness studio application development.
