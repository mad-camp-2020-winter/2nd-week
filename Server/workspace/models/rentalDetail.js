var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var rentalDetailSchema = mongoose.Schema({
    phone: {type:String},
    item_id: mongoose.Schema.Types.ObjectId,
    institution_id: mongoose.Schema.Types.ObjectId,
    approval: {type: Number, max:2, default:0},
    date:{type:Date, default: Date.now}

});

module.exports = mongoose.model('rentalDetail', rentalDetailSchema)