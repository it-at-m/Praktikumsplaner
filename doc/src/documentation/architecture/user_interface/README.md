# User interface
Important aspects concerning the user interface will be documented here.

- [User Feedback](#user-feedback)
- [Loading Animations](#loading-animations)
- [Form Validation](#form-validation)

## User Feedback
Every time the user interacts with the application in a way that the state changes,
the user should be informed about the success of the interaction.
Currently, this is done via a snackbar if the interaction was successful, and an error dialog
if something went wrong. The error dialog displays the error message sent by the backend
so the messages generated in the backend should be comprehensible and really point out what went wrong.

## Loading Animations
Every time a user has to wait for the completion of a request sent to the backend, a loading animation should be displayed.
The application distinguishes between two scenarios. Retreiving data from the backend and providing data to the backend.
For the first scenario [skeleton loaders](#skeleton-loader) are used, for the second scenario a [circular loader](#circular-loader) is used.

### Skeleton Loader
The skeleton loader provides a first impression of the site so the user has a slight hint
of what to expect when the app is done loading.
So the loaders should represent the structure of the site, where will be content and where won't be content.

### Circular Loader
The circular loader provides the user with the information that his submitted data is being processed at the moment.
The loader will be hidden when the request ended.

## Form Validation
All forms that require user input shall validate their fields to ensure valid inputs.
This is not only to ensure valid data gets to the backend, but also to provide a good user experience
where the user is not able to submit a form until all data meets the requirements.
So if the user uses the app as intended, they will get information about invalid data before sending 
it and getting an error.

All the rules should match the limitations in the backend and should provide a comprehensible
message why the input is invalid.