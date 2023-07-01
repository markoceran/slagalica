const express = require('express');
const socket = require('socket.io');
const fs = require('fs');
const app = express();
const port = 3000;

const server = app.listen(port);
app.use(express.static('public'));
console.log('Server is running');
const io = socket(server);

var admin = require("firebase-admin");

var serviceAccount = require("D:\slagalica-a1f39-firebase-adminsdk-k0h7u-29f516cd28.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://slagalica-a1f39-default-rtdb.firebaseio.com"
});

// Get a reference to the Firestore instance
var db = admin.firestore();


var List = require("collections/list");

/*class Message {
    constructor(_id, text, sender) {
      this._id = _id;
      this.text = text;
      this.sender = sender;
    }
  }

var messages = new List([]);
*/

io.on('connection', (socket) => {
    console.log("New socket connection: " + socket.id);

    /*socket.on('counter', () => {
        count++;
        console.log("counter " + count);
        io.emit('counter', count);
    })

    socket.on('message', (messageText) => {
        var randomBoolean = Math. random() >= 0.5; 
        messages.add(new Message(messages.length, messageText, randomBoolean));
        console.log(messages.toJSON());
        io.emit('message', messages.toJSON());
    })*/

    socket.on('zapocni igru', () => {

            socket.emit('zapocni igru');
            console.log('provera');
    })

    socket.on('dobavi ko zna zna', () => {

                // Retrieving data from Firestore collection "korak-po-korak"
                        db.collection("korak-po-korak")
                            .get()
                            .then((querySnapshot) => {
                                if (!querySnapshot.empty) {
                                    // Get a random document from the query snapshot
                                    const randomIndex = Math.floor(Math.random() * querySnapshot.size);
                                    const randomDocument = querySnapshot.docs[randomIndex];
                                    const data = randomDocument.data();

                                    // Emit the data to connected clients
                                    io.emit('data', data);
                                } else {
                                    console.log('No documents found in the collection "korak-po-korak"');
                                }
                            })
                            .catch((error) => {
                                console.error('Error retrieving documents from Firestore:', error);
                            });
    })


})
