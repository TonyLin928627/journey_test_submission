###journey_test_submission

This repository contains an Android app for the test required by Journey for Tony Lin's job application. 

ANDROID TEST
- Your task is to create a native Android app. If you can, please use Kotlin, MVVM and Android Jetpack.</br>
		Requirment comlpeted.  The app was written in Kotlin with MVVM. The following Jetpack compontents are introduced</br>
		1. Hilt/Dagger2 </br>
		2. Room</br>
		3. Navigation</br>
		4. ViewModel</br>
		5. LiveData</br>
		6. Corotine/Flow</br>
- Use the endpoints at https://jsonplaceholder.typicode.com </br>
		Requirment comlpeted. The follwoing two endpoints are used for fetching data with Retrofit 2.9</br>
		 1. https://jsonplaceholder.typicode.com/posts</br>
		 2. https://jsonplaceholder.typicode.com/comment</br>
- Display posts in a list</br>
		Requirment comlpeted. Please see the implementation of  PostsFratments and PostsViewModelin the project</br>
- Open a detailed view with associated comments when user selects an item on the posts list.
		Requirment comlpeted. Please see the implementation of  CommentsOfPostFragment and PostsViewModel in the project</br>
- Save the data into local storage.</br>
		Requirment comlpeted. Please see the implementation of  DownloadFragment and DownloadViewModel in the project</br>
- Nice to have:</br>
Search functionality on the posts list and comments list. </br>
		Requirment implemented.  There is input field at the top of PostsFratments and CommentsOfPostFragment.</br>
		1. In PostsFratments:  Populate the posts that have body containing the input search key OR have comments of which the title or body contains the search key</br>
		2. In DownloadFragment: Populate the comments that have the title or body contains the search key.</br>
