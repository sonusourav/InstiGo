<img src="https://user-images.githubusercontent.com/47269634/55819333-2a623c80-5b16-11e9-9e68-7383086d8b80.png" align =right height='150'>


[![Issues](https://img.shields.io/github/issues-raw/oss2019/instigo-android.svg?color=red)](https://github.com/oss2019/instigo-android/issues) [![Pull Requests](https://img.shields.io/github/issues-pr/oss2019/instigo-android.svg?color=yellow)](https://github.com/oss2019/instigo-android/pulls) [![License](https://img.shields.io/github/license/oss2019/instigo-android.svg?logoColor=red)](https://github.com/oss2019/instigo-android/blob/master/LICENSE.md) [![Gitter](https://img.shields.io/badge/chat-on%20gitter-ff006f.svg?style=flat-square)](https://gitter.im/oss2019/instigo-android)


# InstiGo-Android
InstiGo is an android app for the students and faculties of IIT Dharwad that provide a one stop solution for matters related to mess, academia and hostel. 

![InstiGo_cover](https://user-images.githubusercontent.com/34706326/82624729-eab6c100-9c00-11ea-80eb-1ab052f4592c.jpg)

## InstiGo apk
[![button](https://user-images.githubusercontent.com/34706326/57187663-142a7f00-6f10-11e9-9d89-144e9f46e8f3.jpg)](https://drive.google.com/drive/folders/186EfacIxiywjDOTjxS0nG5ktbw2RuX66?usp=sharing)
## Libraries Used

- [Firebase](https://github.com/firebase/quickstart-android) - Firebase is Google's mobile platform that helps you quickly develop high-quality apps
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library for Android focused on smooth scrolling 
- [KensBurnView](https://github.com/flavioarfaria/KenBurnsView) - Android ImageViews animated by Ken Burns Effect
- [Boom Menu](https://github.com/Nightonke/BoomMenu) - A menu which can ... BOOM! - Android
- [Butterknife](http://jakewharton.github.io/butterknife/) - View "injection" library for Android
- [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.

## Project Setup
After cloning the project, you need to replace the existing Firebase json file with yours. Follow the steps below to add json file to your project:
1. Create Firebase account and go to console ( it is present in upper right side of main website).
2. Click on **New Project**. A dialog box opens up. Give a suitable name and click on **Create Project**.
3. In the new window, where you have to select the platform in which you want to add Firebase, select *Android*.
4. Register app window opens. Fill the details.  
  **Package Name :** *com.iitdh.sonusourav.instigo*    
  **App Nickname :** *InstiGo*   
  **Debug Signing Certificate SHA-1 :** Add your own SHA-1 key. Follow the steps if you don't know how to do so.   
  
![SHA-1 Key](https://user-images.githubusercontent.com/34706326/59098277-76c2d080-893d-11e9-8678-63fe5d30dfb6.JPG)   
                                        (a) Click on **Gradle** tab in the right side of Android studio.   
                                        (b) Click on Android and then signingReport.   
                                        (c) Run window will open up in which **SHA-1** key will be mentioned.   
                                        (d) Copy and paste it on the required field in Firebase.    
                                        
![SHA-1 Key2](https://user-images.githubusercontent.com/34706326/59098391-c43f3d80-893d-11e9-87ae-b3c3a2e92850.JPG)

5. Click on Register App button. 
6. Download the json file and replace the existing file with yours. ( For that switch from Android mode to Project in Android Studio)   
![Project mode](https://user-images.githubusercontent.com/34706326/59098907-4f6d0300-893f-11e9-8a2e-a7f6fdc5a77a.JPG)
7. Build the project in Android Studio. And click OK in Firebase and the project is created.
8. Next when the below window opens, go to **Sign-in Method** in **Authentication** tab.   

![firebase](https://user-images.githubusercontent.com/34706326/59099130-f18ceb00-893f-11e9-8cc7-943043cb9976.JPG)   

9. Enable *Email and Password* and *Google Sign-In*.
10. Setup **Realtime Database** from **Database** tab. Make sure you have the following rules in your rules section of database.

``{   
"rules": {   
  ".read": "auth != null",   
  ".write": "auth != null"   
          }     
 }``   
 
 11. Similarly, setup **Firebase Storage** from *Storage* tab. Check the rules again.   
 
``service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}``


## Contributing

Before contributing be sure to check out the [CONTRIBUTION GUIDELINES](https://github.com/oss2019/instigo-android/blob/master/Contribution-Guidelines.md) guidelines.

We currently have a series of automated Unit and Integration tests. These can be run locally and are also run when submitting a pull request.

## Code Style
For contributions please read the [CODESTYLE](https://github.com/oss2019/instigo-android/blob/master/Code-Style.md) carefully. Pull requests that do not match the style will be rejected.

## Commit Style
For writing commit messages please read the [COMMITSTYLE](https://github.com/oss2019/instigo-android/blob/master/Commit-Style.md) carefully. Kindly adhere to the guidelines. Pull requests not matching the style will be rejected.  

## Communication

Our other sources of communications include

- Email : sonusouravdx001@gmail.com    
For more information, please refer to [My personal Website](https://sonusourav.github.io/).


## LEGAL & DISCLAIMER

Please refer to [LICENSE](https://github.com/oss2019/instigo-android/blob/master/LICENSE.md).   
