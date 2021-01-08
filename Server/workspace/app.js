//serverjs

//load packages
let express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mongoose = require('mongoose');

//connect to MongoDB
var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
    // CONNECTED TO MONGODB SERVER
    console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost:27017/testDB');

// DEFINE MODEL
var Institution = require('./models/institution');
var Item = require('./models/item');
var Login = require('./models/login');
var RentalDetail = require('./models/rentalDetail');

// [CONFIGURE APP TO USE bodyParser]
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// [CONFIGURE SERVER PORT]

var port = process.env.PORT || 8080;

// [CONFIGURE ROUTER]
var routerInstitution = require('./routes/institutionRouter')(app, Institution);
var routerItemInformation = require('./routes/itemRouter')(app, Item);
var routerLogin = require('./routes/loginRouter')(app, Login);
var routerRentalDetail = require('./routes/rentalDetailRouter')(app, RentalDetail);



// [RUN SERVER]
var server = app.listen(port, function(){
 console.log("Express server has started on port " + port)
});