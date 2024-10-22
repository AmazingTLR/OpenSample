## OpenSample

### Description
This Android project is structured according to the principles of **Clean Architecture**.
I didn't implement models for each layers. In a more advanced project, I would have done it.

It uses :
- **Ktor** for network requests
- **Hilt** for dependency injection
- **Jetpack Compose** for the UI layer
- **Coroutines** for asynchronous programming (with SharedFlow and StateFlow)

The architecture consists of multiple modules :
- **useCase** module contains the business logic
- **network** module contains two submodules. **api** contains the models and API repository interface. **ktor** implements repository and fetch the data from Github's rest API.
- **app** module contains the UI layer with a classic MVVM architecture. The ViewModel fetches the data from the useCase and exposes it to the UI layer.

Some UI Tests are implemented in the **network.ktor** & **usecase** modules.
With a bit more time, I would have implemented more tests in the **app** module.

### How to run the project
Paste your Github's token in the **local_template.properties**.
Gradle will automatically create a **local.properties** file with your token.
You can also comment the code in **KtorJsonPlaceHolderClient** but you will be limited to 60 requests per hour.