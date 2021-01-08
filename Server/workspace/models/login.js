var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var loginSchema = mongoose.Schema({
    id : {type : String, required: true},
    password: {type : String, required: true}
});

module.exports = mongoose.model('login', loginSchema)