# ChecklistApp

This project will be used as a playground to recite my Android Development skills along with all new packages and best practices. Main goal of this project is to act as a To-Do app. User can filter, search, edit and add new tasks.

#### Key takeaways from this project

* 100% in Kotlin
* Room (SQLite)
* Jetpack DataStore
* Flow (Coroutines)
* Navigation component
* Dependency injection with Dagger Hilt
* View Binding
* MVVM (Model-View-ViewModel)
* ViewModel + LiveData

### Project initialization ([v0.0.1](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.1))

In this version project is completely setup and ready for work. All dependencies are inserted as well as drawable resources.

### Layout and Room entity ([v0.0.2](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.2))

In this version a couple of layouts have been added:

* **Fragment Tasks** - Entry point fragment that is visible as soon as you enter app. Responsible for showing a list of tasks added by the user. Inside of it is a Coordinator layout with added recycler view and floating action button.
* **Item task** - Simple small layout that keeps all the data responsible for representing task inside of recycler view. Inside of it is a Constraint layout with checkbox, task name and exclamation mark image that shows if task is important or not. 
* **Fragment Add Edit Task** -  Fragment which is used to edit or add task. Inside of it is a Coordinator layout with two children - linear layout and floating action button.

Also, data package has been created and along with it new data class Task, that will hold all the information important for tasks. This is a starting point for implementing Room and will be expanded with it in later versions.




