module.exports = function(app, RentalDetail)
{
    // GET ALL RENTALDETAIL
    app.get('/rental_detail', function(req,res){
        RentalDetail.find(function(err, rentalDetail){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(rentalDetail);
        })
    });

    // GET RENTALDETAILS BY INSTITUTION ID
    app.get('/rental_detail/institution_id/:institution_id', function(req, res){
        RentalDetail.find({institution_id: req.params.institution_id},{_id: 1, user_phone: 1, item_id: 1, institution_id: 1, approval: 1, server_date: 1}, function(err, item){
            if(err) return res.status(500).json({error: err});
            if(item.length === 0) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(item);
        })
    });

    // GET RENTALDETAILS BY ITEM ID
    app.get('/rental_detail/item_id/:item_id', function(req, res){
        RentalDetail.find({item_id: req.params.item_id},{_id: 1, item_id: 1, institution_id: 1, approval: 1, server_date: 1, rental_date: 1, user_phone: 1, user_name: 1, comment: 1, count: 1}, function(err, item){
            if(err) return res.status(500).json({error: err});
            if(item.length === 0) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(item);
        })
    });

    // GET SINGLE RENTALDETAIL
    app.get('/rental_detail/:rentalDetail_id', function(req, res){
        RentalDetail.findOne({_id: req.params.institution_id}, function(err, rentalDetail){
            if(err) return res.status(500).json({error: err});
            if(!rentalDetail) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(rentalDetail);
        })
    });

    // GET RENTALDETAIL BY 전화번호
    app.get('/rental_detail/user_phone/:user_phone', function(req, res){
        RentalDetail.find({user_phone: req.params.user_phone},{_id: 1, item_id: 1, institution_id: 1, approval: 1, server_date: 1, rental_date: 1, user_phone: 1, user_name: 1, comment: 1, count: 1}, function(err, rentalDetail){
            if(err) return res.status(500).json({error: err});
            if(rentalDetail.length === 0) return res.status(404).json({error: 'rentalDetail not found'});
            res.json(rentalDetail);
        })
    });

    // CREATE RENTALDETAIL
    app.post('/rental_detail', function(req, res){
        var rentalDetail = new RentalDetail();
        rentalDetail.user_phone = req.body.user_phone;
        rentalDetail.item_id = req.body.item_id;
        rentalDetail.institution_id = req.body.institution_id;
        rentalDetail.approval = req.body.approval;
        rentalDetail.server_date = req.body.server_date;
        rentalDetail.rental_date = req.body.rental_date;
        rentalDetail.user_name = req.body.user_name;
        rentalDetail.count = req.body.count;
        
        // date는 default로 서버에 찍히는 시각 저장
        // rentalDetail.date = req.body.date; 

        rentalDetail.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json(rentalDetail);

        });
    });

    // UPDATE THE RENTALDETAIL
    app.put('/rental_detail/:rentalDetail_id', function(req, res){
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

    // DELETE RENTALDETAIL
    app.delete('/rental_detail/:rentalDetail_id', function(req, res){
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