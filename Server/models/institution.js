var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var institutionSchema = mongoose.Schema({
    name: String,
    number: String,
    location: String
});

module.exports = mongoose.model('institution', institutionSchema)