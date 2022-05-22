# Mini Tumblr - Android

Android app showcasing a Tumblr feed. The app retrieves posts from Tumblr API and stores them in a local database supporting offline mode.



https://user-images.githubusercontent.com/14865130/169718112-7d65ebb6-535a-4710-9497-848cbaa2a250.mp4



The application uses the following:
- [JumblrClient](https://github.com/tumblr/jumblr): Client to retrieve posts from Tumblr remote API.
- [GreenDao](https://github.com/greenrobot/greenDAO): ORM to store Tumblr posts locally.

### Login
To log in use the following mocked user:
- Username: diego93
- Password: 123456

### Next steps
- Migrate to Kotlin
- Migrate MVC to MVVM
- Replace ButterKnife with ViewBinding
- Replace GreenDao with Room
