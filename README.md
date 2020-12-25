# Movies-coroutines-mvi-multimodule
# By Nour Tayeb
The project is a 2 module project (Per feature):
# App:
- Goal:
  Build an app to search for movies and add them to favorite. I rely on 'ads' Module to show Interstitial and Banner ads.
  This Module has its own Sqlite Room database for Movies and users.
- Architecture:
  Mvi + Clean Architecture + Livedata coroutines builder (One shot operations)
# Ads:
- Goal:
  Build an Ads Library.
  This Module has its own Sqlite Room database for Ads.
- Architecture:
  Mvi + Clean Architecture + Coroutines Flow for Stream of events (Ads) over the app layers.
  I use Flows to generate a stream of "Actions" from Views to the ViewModels which is converted to another Flow of "States" from the ViewModels to the Views (to be observed as LiveData)
  And this is how the MVI pattern in this Module is managed (as a stream using these Flows)

# Language
- Kotlin

# Technologies used:

- Android:
  Appcompat, 
  RecyclerView,
  Lifecycle architecture component, 
  Material.
  
- Dependency Injection:
  Dagger Hilt (For project and for instrumentation tests)
  
- Json parsing:
  GSON.
  
- Networking:
  Retrofit, 
  OkHttp, 
  Glide (image loading).
  
- Local storage:
  Room (sqlite) - (For project Favorite and searchFragment state saving + for instrumentation tests)
  
- Concurrency: 
  Coroutines (Flow and Livedata Coroutines builders)
  
- Reactive programming:
  Coroutines Flow

- Permissions:
  Dexter

- Navigation:
  Fragment Navigation Component

- Local messages:
  EventBus

# Testing:
The project contains unit and instrumentation tests
- Testing:
  JUnit, 
  Mockk,
  Robolectric
