# Android Fitness App

A comprehensive fitness application for Android that provides workout videos, progress tracking, and personalized fitness content.

## Features

### 1. Video Workout Library
- **Discover Page**: Browse curated workout videos from popular fitness YouTubers
- **Trainers Page**: Access workout content organized by trainers
- **Category Browsing**: Find workouts by:
  - **Workout Types**: Cardio, Strength Training, Yoga, Pilates, Dance, Martial Arts, HIIT, Cycling, Running
  - **Fitness Goals**: Weight Loss, Muscle Building, General Fitness, Flexibility, Injury Recovery
  - **Duration**: Quick 5-min sessions, Express 10-min, 20-min, 30-min, Full 60-min workouts
  - **Equipment**: No Equipment, Dumbbells, Resistance Bands, Kettlebell, Yoga Mat, Bodyweight

### 2. Workout Player
- **In-App Video Playback**: Stream YouTube workout videos directly within the app
- **Browser Mode**: Fallback mode for restricted videos
- **External Player Support**: Option to open videos in YouTube app
- **Workout Controls**:
  - Start Workout button to begin tracking
  - End Workout button to save session
  - Automatic duration and calorie tracking

### 3. Workout History & Tracking
- **Automatic History**: All played videos are automatically saved to history
- **Workout Stats**: Track duration, calories burned, and completion percentage
- **History View**: Browse past workout sessions with thumbnails
- **Replay Workouts**: Easily replay any previous workout from history

### 4. Favorites System
- **Save Favorite Workouts**: Mark workouts as favorites with a star button
- **Quick Access**: Access your favorite workouts from a dedicated Favorites page
- **Persistent Storage**: Favorites are saved locally using Room database

### 5. Fitness Dashboard
- **Statistics Overview**:
  - Total workouts completed
  - Average workout time
  - Total calories burned
  - Workout streak tracking
  - Wellness score
  - Heart rate monitoring
- **Visual Charts**: View workout progress with interactive graphs
- **Weekly/Monthly Stats**: Track your fitness journey over time

### 6. User Profile
- **Profile Setup**: Create and customize your fitness profile
- **Edit Profile**: Update personal information and fitness goals
- **Achievement Tracking**: View your fitness achievements and milestones

### 7. Content Discovery
- **YouTube Integration**: Search and discover new workout videos
- **Fitness Articles**: Read fitness tips, nutrition guides, and workout techniques
- **Article Categories**: Browse articles by topic (nutrition, exercise, wellness, etc.)

### 8. Modern UI/UX
- **Material Design 3**: Modern, clean interface following Material Design guidelines
- **Dark Mode Support**: Automatic dark/light theme switching
- **Shimmer Loading**: Smooth skeleton loading animations
- **Lottie Animations**: Engaging animations for empty states and loading
- **Shared Element Transitions**: Smooth animations between screens
- **Card-based Layouts**: Intuitive workout browsing with card views

## Technical Details

### Technology Stack
- **Language**: Java
- **UI Framework**: XML layouts with Material Design 3
- **Architecture**: MVVM pattern with Repository
- **Database**: Room (SQLite wrapper)
- **Image Loading**: Glide
- **Video Playback**: WebView with YouTube embed API
- **Async Operations**: ExecutorService for background tasks
- **Animations**: Lottie, Material Transitions

### Minimum Requirements
- **Android Version**: Android 7.0 (API 24) or higher
- **Target SDK**: Android 14 (API 34)
- **Storage**: ~50 MB for app installation
- **Internet**: Required for streaming workout videos

### Key Dependencies
```gradle
// Material Design
implementation 'com.google.android.material:material:1.11.0'

// Room Database
implementation 'androidx.room:room-runtime:2.6.1'
annotationProcessor 'androidx.room:room-compiler:2.6.1'

// Glide Image Loading
implementation 'com.github.bumptech.glide:glide:4.16.0'

// Shimmer Loading Effect
implementation 'com.facebook.shimmer:shimmer:0.5.0'

// Lottie Animations
implementation 'com.airbnb.android:lottie:6.1.0'

// Firebase (optional analytics)
implementation 'com.google.firebase:firebase-bom:32.7.0'
```

## App Structure

### Main Activities
1. **LoginActivity**: User authentication and onboarding
2. **FitnessDashboardActivity**: Main dashboard with stats and overview
3. **MainActivity2**: Workout categories and browsing
4. **Discover**: Curated workout video discovery
5. **Discoveryoutubers**: Trainer-based workout browsing
6. **MainActivity4**: Video player with workout tracking
7. **WorkoutHistoryActivity**: View past workout sessions
8. **FavoritesActivity**: Access favorite workouts
9. **UserProfileActivity**: View and edit user profile
10. **DiscoverArticlesActivity**: Browse fitness articles

### Database Schema
- **WorkoutHistory**: Stores completed workout sessions
- **FavoriteWorkout**: Stores user's favorite workouts
- **WorkoutContent**: Workout metadata and video information
- **UserProfile**: User information and preferences

### Content Providers
- **WorkoutContentProvider**: Provides static workout content
- **30 Database Workouts**: Pre-populated workouts across multiple categories
- **5 Category Workouts**: Verified working YouTube videos for category browsing

## Building the App

### Prerequisites
1. Android Studio (Arctic Fox or newer)
2. JDK 8 or higher
3. Android SDK with API 34

### Build Steps
1. Clone the repository:
```bash
git clone https://github.com/yourusername/Android-Fitness-App-Development.git
```

2. Open the project in Android Studio

3. Sync Gradle files:
```bash
./gradlew build
```

4. Run on emulator or physical device:
```bash
./gradlew assembleDebug
```

### Build Variants
- **Debug**: Development build with debugging enabled
- **Release**: Production build with ProGuard optimization

## Installation

1. Build the APK:
```bash
./gradlew assembleDebug
```

2. Install on device:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

Or use Android Studio's "Run" button to build and install automatically.

## Usage

### Getting Started
1. Launch the app and complete profile setup
2. Browse workout categories or discover new content
3. Tap on any workout video to open the player
4. Click "Start Workout" to begin tracking
5. Click "End Workout" when finished to save to history
6. View your progress in the Dashboard

### Tips
- Mark frequently used workouts as favorites for quick access
- Check your workout history to track progress over time
- Explore different categories to find workouts that match your goals
- Use the search feature to find specific workout types

## Features in Detail

### Workout Tracking
When you play a video:
1. Video automatically saves to history
2. Click "Start Workout" to begin timing
3. App tracks duration in real-time
4. Click "End Workout" to save stats
5. Calories calculated at ~8 cal/min
6. View complete session in History tab

### Favorites Management
- Star icon appears on all workout cards
- Tap star to add/remove from favorites
- Favorites sync instantly using Room database
- Access favorites from dedicated Favorites screen
- Favorites include thumbnail, title, and trainer info

### Video Playback
- Primary: YouTube iframe embedding
- Fallback: Mobile YouTube web player
- External: Opens in YouTube app
- Supports full-screen playback
- Remembers playback position

## Known Limitations
- Requires internet connection for video streaming
- Some YouTube videos may be restricted and unable to play in-app
- Calorie calculations are estimates based on average intensity
- Workout stats are manually started/stopped (not automatic)

## Future Enhancements
- [ ] Workout plans and programs
- [ ] Social features (share workouts, follow friends)
- [ ] Offline video downloads
- [ ] Custom workout creation
- [ ] Integration with fitness wearables
- [ ] More accurate calorie tracking
- [ ] Push notifications for workout reminders
- [ ] Premium content subscription

## Contributing
Contributions are welcome! Please feel free to submit pull requests or open issues for bugs and feature requests.

### Development Guidelines
1. Follow Material Design principles
2. Use Room database for local storage
3. Implement proper error handling
4. Add comments for complex logic
5. Test on multiple Android versions
6. Keep dependencies up to date

## License
This project is available for educational and personal use.

## Acknowledgments
- Workout videos provided by popular fitness YouTubers
- Material Design icons from Google
- Lottie animations from LottieFiles community
- Shimmer effect by Facebook

## Support
For issues, questions, or feature requests, please open an issue on GitHub.

## Version History
- **v1.0** - Initial release with core features
  - Video workout library
  - Workout tracking and history
  - Favorites system
  - Fitness dashboard
  - User profiles
  - Material Design 3 UI

---

**Built with Android Studio** | **Powered by YouTube API** | **Material Design 3**
