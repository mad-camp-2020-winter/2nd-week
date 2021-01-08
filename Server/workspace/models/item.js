var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var itemSchema = mongoose.Schema({
    name: String,
    photo: Buffer,
    count: Number,
    institution_id : mongoose.Schema.Types.ObjectId
});

module.exports = mongoose.model('item', itemSchema)