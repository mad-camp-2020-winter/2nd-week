module.exports = function(app, Login)
{
    // GET ALL ITEM
    app.get('/login', function(req,res){
        Login.find(function(err, login){
            if(err) return res.status(500).send({error: 'database failure'});
            res.json(login);
        })
    });

    // GET SINGLE ITEM
    app.get('/login/:login_id', function(req, res){
        Login.findOne({_id: req.params.institution_id}, function(err, login){
            if(err) return res.status(500).json({error: err});
            if(!login) return res.status(404).json({error: 'login not found'});
            res.json(login);
        })
    });

    // GET ITEM BY NAME
    app.get('/login/name/:name', function(req, res){
        Login.find({name: req.params.name}, {_id: 0, id: 1, password: 1},  function(err, login){
            if(err) return res.status(500).json({error: err});
            if(login.length === 0) return res.status(404).json({error: 'login not found'});
            res.json(login);
        })
    });

    // CREATE ITEM
    app.post('/login', function(req, res){
        var login = new Login();
        login.id = req.body.id;
        login.password = req.body.password;

        login.save(function(err){
            if(err){
                console.error(err);
                res.json({result: 0});
                return;
            }

            res.json({result: 1});

        });
    });

    // UPDATE THE ITEM
    app.put('/login/:login_id', function(req, res){
        Login.update({ _id: req.params.login_id }, { $set: req.body }, function(err, output){
            if(err) res.status(500).json({ error: 'database failure' });
            console.log(output);
            if(!output.n) return res.status(404).json({ error: 'login not found' });
            res.json( { message: 'login updated' } );
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
    app.delete('/login/:login_id', function(req, res){
        Login.remove({ _id: req.params.login_id }, function(err, output){
            if(err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "institution not found" });
            res.json({ message: "institution deleted" });
            */

            res.status(204).end();
        })
    });
     
}