module.exports = function(app, Item)
{
    // GET ALL ITEM
    app.get('/item', function(req,res){
        Item.find(function(err, item){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(item);
        })
    });

    // GET SINGLE ITEM
    app.get('/item/:item_id', function(req, res){
        Item.findOne({_id: req.params.institution_id}, function(err, item){
            if(err) return res.status(500).json({error: err});
            if(!item) return res.status(404).json({error: 'item not found'});
            res.json(item);
        })
    });

    // GET ITEM BY NAME
    app.get('/item/name/:name', function(req, res){
        Item.find({name: req.params.name}, {_id: 0, name: 1, photo: 1, count: 1},  function(err, item){
            if(err) return res.status(500).json({error: err});
            if(item.length === 0) return res.status(404).json({error: 'item not found'});
            res.json(item);
        })
    });

    // CREATE ITEM
    app.post('/item', function(req, res){
        var item = new Item();
        item.name = req.body.name;
        item.photo = req.body.photo;
        item.count = req.body.count;
        // institution.published_date = new Date(req.body.published_date);

        item.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});

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
     
}