module.exports = function(app, Item)
{
    // GET ALL ITEM
    app.get('/item', function(req,res){
        Item.find(function(err, item){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(item);
        })
    });

    // GET ITEMS BY INSTITUTION ID
    app.get('/item/institution_id/:institution_id', function(req, res){
        Item.find({institution_id: req.params.institution_id},{_id: 1, name: 1, photo: 1, count: 1}, function(err, item){
            if(err) return res.status(500).json({error: err});
            if(item.length === 0) return res.status(404).json({error: 'item not found'});
            res.json(item);
        })
    });

    // GET ITEM BY ITEM ID
    app.get('/item/item_id/:item_id', function(req, res){
        Item.findOne({_id: req.params.item_id}, function(err, item){
            if(err) return res.status(500).json({error: err});
            if(item == null ) return res.status(404).json({error: 'item not found'});
            if(item.length === 0) return res.status(404).json({error: 'item not found'});
            res.json(item);
        })
    });

    // CREATE ITEM
    app.post('/item', function(req, res){
        var item = new Item();
        var bodyParser = require('body-parser');
        item.name = req.body.name;
        item.photo = req.body.photo;
        item.count = req.body.count;
        item.institution_id = req.body.institution_id;
        // institution.published_date = new Date(req.body.published_date);

        app.use(bodyParser.json({limit:"50mb"}));
        app.use(bodyParser.urlencoded({limit: "50mb", extended: false, parameterLimit: 50000}));
        item.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json(item);

        });
    });

    // UPDATE THE ITEM
    app.put('/item/:item_id', function(req, res){
        Item.update({ _id: req.params.item_id }, { $set: req.body }, function(err, output){
            if(err) res.status(500).json({ error: 'database failure' });
            console.log(output);
            if(!output.n) return res.status(404).json({ error: 'item not found' });
            res.json( { message: 'item updated' } );
        })
    /* [ ANOTHER WAY TO UPDATE THE institution ]
            institution.findById(req.params.institution_id, function(err, institution){
            if(err) return res.status(500).json({ error: 'database failure' });
            if(!institution) return res.status(404).json({ error: 'institution not found' });
            if(req.body.title) institution.title = req.body.title;
            if(req.body.name) institution.name = req.body.name;
            if(req.body.published_date) institution.published_date = req.body.published_date;
            institution.save(function(err){
                if(err) res.status(500).json({error: 'failed to update'});
                res.json({message: 'institution updated'});
            });
        });
    */
    });

    // DELETE ITEM
    app.delete('/item/:item_id', function(req, res){
        Item.remove({ _id: req.params.item_id }, function(err, output){
            if(err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "institution not found" });
            res.json({ message: "institution deleted" });
            */

            res.status(204).end();
        })
    });

    //-------------------------------------사진 저장-----------------------------------------------
    //사진 저장용 load packages
    // var multer, storage, path, crypto;
    // multer = require('multer');
    // path = require('path');
    // crypto = require('crypto');
    // var form = "<!DOCTYPE HTML><html><body>" +
    // "<form method='post' action='/upload' enctype='multipart/form-data'>" +
    // "<input type='file' name='upload'/>" +
    // "<input type='submit' /></form>" +
    // "</body></html>";

    // //사진 불러오기 form?
    // app.get('/', function(req, res){
    //     res.writehead(200, {'Content-Type': 'text/html'});
    //     res.end(form);
    // })

    // // Include the node file module
    // var fs = require('fs');

    // storage = multer.diskStorage({
    //     destination: './uploads/',
    //     filename: function(req, file, cb) {
    //     return crypto.pseudoRandomBytes(16, function(err, raw) {
    //         if (err) {
    //         return cb(err);
    //         }
    //         return cb(null, "" + (raw.toString('hex')) + (path.extname(file.originalname)));
    //     });
    //     }
    // });
    
    // //사진 저장
    // app.post( "/upload",
    //     multer({
    //     storage: storage
    //     }).single('upload'), function(req, res) {
    //     console.log(req.file);
    //     console.log(req.body);
    //     res.redirect("/uploads/" + req.file.filename);
    //     console.log(req.file.filename);
    //     return res.status(200).end();
    // });
    
    // //사진 불러오기?
    // app.get('/uploads/:upload', function (req, res){
    //     file = req.params.upload;
    //     console.log(req.params.upload);
    //     var img = fs.readFileSync(__dirname + "/uploads/" + file);
    //     res.writeHead(200, {'Content-Type': 'image/png' });
    //     res.end(img, 'binary');
    
    // });
  
     
}