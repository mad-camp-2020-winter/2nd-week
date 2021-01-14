var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var rentalDetailSchema = mongoose.Schema({
    item_id: mongoose.Schema.Types.ObjectId,
    institution_id: mongoose.Schema.Types.ObjectId,
    approval: {type: Number, max:2, default:0}, // 0:승인대기, 1:승인, 2:반려
    server_date:{type:Number},
    rental_date: {type:Number},
    user_phone: {type:String},
    user_name: String,
    comment: {type: String, default:null},
    count: Number
});

module.exports = mongoose.model('rentalDetail', rentalDetailSchema)