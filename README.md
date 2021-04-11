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

### Navigation Component ([v0.0.3](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.3))

In this version navigation component has been added. Firstly, navigation graph is created along with a new Fragment class which is connected to **Fragment Tasks** layout. Also, Main activity has been made as a Navigation host i.e. where fragments will be viewed and act as a container for them. Now when installing on a device (simulator or a physical device) **Fragment Tasks** can be seen.

### Room and dependency injection ([v0.0.4](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.4))

In this version Room database and DAO has been setup. Also, as an important part of this project dependency injection using **Dagger Hilt** has been added for accessing database instance, DAO and other Android components (Fragments, Activities and Application class).

### List adapter and View binding ([v0.0.5](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.5))

In this version list adapter class has been added along with DiffUtil to properly show data for recycler view. In view model, using DAO data has been fetched and transformed into LiveData so that it can be observable from Fragment. 

### Room search ([v0.0.6](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.6))

In this version room search feature has been added using flow operators.

### Combine multiple flows ([v0.0.7](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.7))

In this version, as opposed to the previous version. multiple variables are taken into account when it comes to filtering recycler view items. Search query, sorting by name and date and hiding completed tasks all are taken into account.

### Jetpack DataStore ([v0.0.8](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.8))

### Update checked tasks in DB ([v0.0.9](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.9))

### Swipe to delete tasks and add undo action ([v0.0.10](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.10))

In this version swipe to delete feature has been added using **ItemTouchHelper** class and snackbar with **UNDO** action. Fragment and ViewModel have been losely coupled with ViewModel opening a Channel between them and exposing a value that channel sends as a flow. Which then **TasksFragment** collects it and shows a snackbar.

### Connect two fragments and enable navigation and handle saved state after process death ([v0.0.11](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.11))

### Add edit fragment logic ([v0.0.12](https://github.com/ivevasiljevic/ChecklistApp/releases/tag/v0.0.12))