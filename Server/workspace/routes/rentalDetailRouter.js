module.exports = function(app, RentalDetail)
{
    // GET ALL ITEM
    app.get('/rentalDetail', function(req,res){
        RentalDetail.find(function(err, rentalDetail){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(rentalDetail);
        })
    });

    // GET SINGLE ITEM
    app.get('/rentalDetail/:rentalDetail_id', function(req, res){
        RentalDetail.findOne({_id: req.params.institution_id}, function(err, rentalDetail){
            if(err) return res.status(500).json({error: err});
            if(!rentalDetail) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(rentalDetail);
        })
    });

    // GET ITEM BY NAME
    app.get('/rentalDetail/name/:name', function(req, res){
        RentalDetail.find({name: req.params.name}, {_id: 0, phone: 1, item_id: 1, institution_id: 1, approval: 1, date: 1},  function(err, rentalDetail){
            if(err) return res.status(500).json({error: err});
            if(rentalDetail.length === 0) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(rentalDetail);
        })
    });

    // CREATE ITEM
    app.post('/rentalDetail', function(req, res){
        var rentalDetail = new RentalDetail();
        rentalDetail.phone = req.body.phone;
        rentalDetail.item_id = req.body.item_id;
        rentalDetail.institution_id = req.body.institution_id;
        rentalDetail.approval = req.body.approval;
        
        // date는 default로 서버에 찍히는 시각 저장
        // rentalDetail.date = req.body.date; 

        rentalDetail.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});

        });
    });

    // UPDATE THE ITEM
    app.put('/rentalDetail/:rentalDetail_id', function(req, res){
        RentalDetail.update({ _id: req.params.rentalDetail_id }, { $set: req.body }, function(err, output){
            if(err) res.status(500).json({ error: 'database failure' });
            console.log(output);
            if(!output.n) return res.status(404).json({ error: 'rentalDetail not found' });
            res.json( { message: 'rentalDetail updated' } );
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
    app.delete('/rentalDetail/:rentalDetail_id', function(req, res){
        RentalDetail.remove({ _id: req.params.rentalDetail_id }, function(err, output){
            if(err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "institution not found" });
            res.json({ message: "institution deleted" });
            */

            res.status(204).end();
        })
    });
     
}