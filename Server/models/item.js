var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var itemSchema = mongoose.Schema({
    name: String,
    photo: String,
    count: String,
    institution_id : {type: mongoose.Schema.Types.ObjectId, default: "5ffa9c7365fc7b6bd6f70047"} //default institution 추후 설정 예정
});

module.exports = mongoose.model('item', itemSchema)