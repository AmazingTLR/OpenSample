## OpenSample

### Description
This Android project is structured according to the principles of **Clean Architecture**.
I didn't implement models for each layers. In a more advanced project, I would have done it.

It uses :
- **Ktor** for network requests
- **Hilt** for dependency injection
- **Jetpack Compose** for the UI layer
- **Coroutines** for asynchronous programming (with SharedFlow and StateFlow)
- **Compose Navigation** for easier UI navigation with Compose
- **Coil** for image loading
- **Mockk** for mocking objects in tests

Some UT Tests are implemented in the **network.ktor** & **usecase** modules.
With a bit more time, I would have implemented more tests in the **app** module.

### How to run the project
Paste your Github's token in the **local_template.properties**.
Gradle will automatically create a **local.properties** file with your token.
You can also comment the code in **KtorJsonPlaceHolderClient** but you will be limited to 60 requests per hour.

### Architecture
The architecture consists of multiple modules, representing a layer of the Clean Architecture :
- **app** module (UI Layer) contains the UI layer with a classic MVVM architecture. The ViewModel hold the data and exposes it to the UI layer.
- **useCase** module (Domain Layer) contains the business logic
- **network** module (Data Layer) contains two submodules. **api** contains the models and API repository interface. **ktor** implements repository and fetch the data from Github's rest API.

Note : Only the **app** module is a Android module. The other modules are pure Kotlin modules.

### Improvements
- The pagination is implemented but not in a traditional way. Github's Api does expose pagination through a Link header. Parsing it is a bit tricky with Ktor.
Therefore, I relied on a pagination with the data hold in the ViewModels. 
That is not efficient but works thanks to the small amount of data. 
With more time I would have parsed the header and exposed the pagination data in NetworkResponse.
- The network error handling is not optimal. I would have expanded the http status codes in KtorNetworkResultExt