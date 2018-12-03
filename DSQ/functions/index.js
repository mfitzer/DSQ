// The Cloud Functions for Firebase SDK to create Cloud Functions and setup triggers.
const functions = require('firebase-functions');

// The Firebase Admin SDK to access the Firebase Realtime Database.
const admin = require('firebase-admin');
admin.initializeApp();

exports.testNotification = functions.database.instance('dynamic-scheduling-system').ref('/Users/{UserId}/title').onWrite((snapshot, context) => {

    const userId = context.params.UserId;

    // Get the list of device notification token.
    const getDeviceTokensPromise = admin.database().ref(`/Users/${userId}/NotificationToken`).once('value');

    // The snapshot to the user's tokens.
    let tokensSnapshot;

    // The array containing all the user's tokens.
    let tokens;

    return Promise.all([getDeviceTokensPromise]).then(results => {
        tokensSnapshot = results[0];
        const notificationToken = tokensSnapshot.val();

        // Check if there are any device tokens.
        /*if (!tokensSnapshot.hasChildren()) {
            return console.log('There are no notification tokens to send to.');
        }
        console.log('There are', tokensSnapshot.numChildren(), 'tokens to send notifications to.');*/

        // Notification details.
        const payload = {
            notification: {
            title: 'Database triggered notification!',
            body: "Something was changed!"
            }
        };

        // Listing all tokens as an array.
        console.log("registrationToken: " + notificationToken);
        //tokens = Object.keys(tokenSnapshot.val());
        // Send notifications to all tokens.
        return admin.messaging().sendToDevice(notificationToken, payload);
    }).then((response) => {
        // For each message check if there was an error.
        const tokensToRemove = [];
        response.results.forEach((result, index) => {
            const error = result.error;
            if (error) {
                console.error('Failure sending notification to', tokens[index], error);
                // Cleanup the tokens who are not registered anymore.
                if (error.code === 'messaging/invalid-registration-token' || error.code === 'messaging/registration-token-not-registered')
                {
                    tokensToRemove.push(tokensSnapshot.ref.child(tokens[index]).remove());
                }
            }
            else
            {
                console.log("No errors! Notification sent!");
            }
        });
    return Promise.all(tokensToRemove);
    });
});