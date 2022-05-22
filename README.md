# Mini Tumblr - Android

Android app showcasing a Tumblr feed. The app retrieves posts from Tumblr API and stores them in a local database supporting offline mode.

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