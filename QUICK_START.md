# Quick Start Guide - Fitness Studio App

## Prerequisites
1. ✅ Android Studio installed
2. ✅ Backend API running at `http://localhost:8000/api/`
3. ✅ Android device or emulator (API level 26+)

## Step 1: Open Project
```bash
1. Open Android Studio
2. File → Open → Select "buddy_gym_app_frontend_2025" folder
3. Wait for Gradle sync to complete
```

## Step 2: Verify Backend Connection

### For Android Emulator (Default)
- Backend URL is already configured: `http://10.0.2.2:8000/api/`
- No changes needed!

### For Physical Device
1. Find your computer's IP address:
   - Windows: `ipconfig` (look for IPv4 Address)
   - Mac/Linux: `ifconfig` or `ip addr`
   
2. Update RetrofitClient.java:
   ```java
   // File: app/src/main/java/.../api/RetrofitClient.java
   // Change line 18:
   private static final String BASE_URL = "http://YOUR_IP:8000/api/";
   // Example: "http://192.168.1.100:8000/api/"
   ```

3. Ensure your phone and computer are on the same WiFi network

## Step 3: Run the App
```bash
1. Connect your Android device via USB (enable USB debugging)
   OR
   Start an Android emulator

2. Click the green "Run" button (▶) in Android Studio
   OR
   Press Shift + F10

3. Select your device/emulator from the list

4. Wait for app to install and launch
```

## Step 4: Test the App

### First Time Setup
1. **Register a new user**:
   - Click "Sign Up" on login screen
   - Fill in all required fields:
     - Names: John
     - Last Names: Doe
     - Email: john@example.com
     - User: johndoe
     - Password: password123
   - Check "I agree to terms"
   - Click "Continue"

2. **Login**:
   - Email or Username: john@example.com (or johndoe)
   - Password: password123
   - Click "Sign In"

### Using the App
1. **Start Workout**:
   - Click the large green button on home screen

2. **Select Muscle Group**:
   - Choose from the list (e.g., "Chest", "Back", "Legs")

3. **Manage Exercises**:
   - View existing exercises
   - Add new: Click the red (+) button
   - Edit: Click pencil icon
   - Delete: Click trash icon

4. **Record Training**:
   - Click on an exercise
   - Enter weight, sets, and reps
   - Click "Insert"

5. **View History**:
   - Click "History" button
   - Switch between "History" and "Performance" tabs

## Troubleshooting

### "Unable to connect to backend"
**Problem**: Network error when making API calls

**Solutions**:
1. Verify backend is running: Open browser → `http://localhost:8000/api/docs/`
2. Check BASE_URL in RetrofitClient.java
3. For emulator: Use `10.0.2.2` not `localhost`
4. For device: Use computer's IP address
5. Disable firewall temporarily
6. Check both devices on same network

### "Build failed" or Gradle errors
**Solutions**:
1. File → Invalidate Caches / Restart
2. Build → Clean Project
3. Build → Rebuild Project
4. Check internet connection (Gradle needs to download dependencies)

### App crashes on launch
**Solutions**:
1. Check Logcat in Android Studio for error details
2. Verify minimum SDK version (API 26+)
3. Ensure all dependencies downloaded successfully
4. Try on a different emulator/device

### "Cannot resolve symbol" errors
**Solutions**:
1. File → Sync Project with Gradle Files
2. Build → Clean Project
3. Restart Android Studio

### Backend returns 401 Unauthorized
**Problem**: Token expired or invalid

**Solutions**:
1. Clear app data: Settings → Apps → Fitness Studio → Clear Data
2. Uninstall and reinstall the app
3. Login again

### Emulator is slow
**Solutions**:
1. Use x86 emulator image (faster than ARM)
2. Enable hardware acceleration in BIOS
3. Allocate more RAM to emulator
4. Use a physical device instead

## Testing Checklist

- [ ] App launches successfully
- [ ] Registration creates new user
- [ ] Login works with email
- [ ] Login works with username
- [ ] Muscle list loads
- [ ] Can add new exercise
- [ ] Can edit exercise
- [ ] Can delete exercise
- [ ] Can record training
- [ ] Stats update after training
- [ ] History shows past trainings
- [ ] Performance tab displays metrics
- [ ] Chart renders correctly

## Default Test Credentials

If backend has seed data, try:
- Email: `test@example.com`
- Password: `testpass123`

## API Endpoints to Verify

Test these in browser or Postman:
- `http://localhost:8000/api/muscles/` - Should return muscle list
- `http://localhost:8000/api/exercises/` - Should return exercises
- `http://localhost:8000/api/docs/` - API documentation

## Next Steps

Once app is running:
1. Explore all 7 screens
2. Create multiple exercises
3. Record several training sessions
4. Check history and performance analytics
5. Test edit and delete functionality

## Support

If you encounter issues:
1. Check Logcat in Android Studio (View → Tool Windows → Logcat)
2. Look for error messages in red
3. Verify backend API is responding
4. Check network connectivity
5. Review IMPLEMENTATION_SUMMARY.md for technical details

## Development Mode

For development and debugging:
1. Enable "Show layout bounds" on device
2. Use Android Studio Profiler for performance
3. Check Network Inspector for API calls
4. Use Logcat filters: `package:mine tag:MyApp`

---

**Ready to go!** 🎉 Your Fitness Studio app should now be running successfully.
