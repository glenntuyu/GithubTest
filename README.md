# Astro Test
## Astro Test App using Kotlin

## Features
- Show list of Github users with Paging
- Show detail of Github users
- Unit Test for ViewModel & PagingSource

## Tech stack & Open-source libraries
- Paging 3 - for infinite scrolling.
- ViewModel - UI related data holder, lifecycle aware.
- Lifecycles - Create a UI that automatically responds to lifecycle events.
- LiveData - Build data objects that notify views when the underlying database changes.
- Hilt- for dependency injection.
- Kotlin Coroutines - for managing background threads with simplified code and reducing needs for callbacks
- Flow - for managing data behind viewModel (use case, repository, etc)
- Glide - for image loading
- Retrofit2 & OkHttp3 - to make REST requests to the web service integrated.
- Unit Test - for unit testing view model & PagingSource
- MVI - for unidirectional data flow

## Open API
Astro Test uses the Github API ("https://api.github.com/")

## Notes
- Owner's current coding style is the one in userdetail and sortorder module
- Homepage is more focused on new tech stack  like MVI, Paging, and Flow

## Preview
<img src="/previews/home.png" width="50%" />
<img src="/previews/userDetail.png" width="50%"/>
