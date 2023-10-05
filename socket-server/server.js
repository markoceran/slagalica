const express = require('express');
const http = require('http');
const socketIO = require('socket.io');

const cors = require('cors');

const app = express();
app.use(cors()); // Enable CORS for all routes

const server = http.createServer(app);
const io = socketIO(server);

app.use(express.static('public')); 


// podaci za igru
let igraci = [];
let igracSocket = {};
let bodovi = {};
let currentTurn = 0;
let koZnaZnaAnswers = [];
let switcher = 0;
let inverter = 0;


io.on('connection', (socket) => {

    console.log('connected with socket id: ' + socket.id);

    socket.on('pridruziSeIgri', (ja) => {
      if (igraci.length < 2) {
        igraci.push(ja);
        igracSocket[socket.id] = ja;
        bodovi[ja] = 0;
        console.log('Player joined: ' + ja + '. Total players: ' + igraci.length);
        
  
        if (igraci.length === 2) {
          io.emit('zapocniIgru', igracSocket);
        }
      }
    });
});



server.listen(3000, () => {
    console.log('Server is running on port 3000');
});
