module.exports = function(app, Institution)
{
    // GET ALL INSTITUTION
    app.get('/institution', function(req,res){
        Institution.find(function(err, institutions){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(institutions);
        })
    });

    // GET SINGLE INSTITUTION
    app.get('/institution/:institution_id', function(req, res){
        Institution.findOne({_id: req.params.institution_id}, function(err, institution){
            if(err) return res.status(500).json({error: err});
            if(!institution) return res.status(404).json({error: 'institution not found'});
            res.json(institution);
        })
    });

    // GET INSTITUTION BY NAME
    app.get('/institution/name/:name', function(req, res){
        Institution.find({name: req.params.name}, {_id: 1, name: 1, number: 1, location: 1},  function(err, institutions){
            if(err) return res.status(500).json({error: err});
            if(institutions.length === 0) return res.status(404).json({error: 'institution not found'});
            res.json(institutions);
        })
    });

    // CREATE INSTITUTION
    app.post('/institution', function(req, res){
        var institution = new Institution();
        institution.name = req.body.name;
        institution.number = req.body.number;
        institution.location = req.body.location;
        // institution.published_date = new Date(req.body.published_date);

        institution.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json(institution);

        });
    });

    // UPDATE THE INSTITUTION
    app.put('/institution/:institution_id', function(req, res){
        Institution.update({ _id: req.params.institution_id }, { $set: req.body }, function(err, output){
            if(err) res.status(500).json({ error: 'database failure' });
            console.log(output);
            if(!output.n) return res.status(404).json({ error: 'institution not found' });
            res.json( { message: 'institution updated' } );
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

    // DELETE INSTITUTION
    app.delete('/institution/:institution_id', function(req, res){
        Institution.remove({ _id: req.params.institution_id }, function(err, output){
            if(err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "institution not found" });
            res.json({ message: "institution deleted" });
            */

            res.status(204).end();
        })
    });
     
}