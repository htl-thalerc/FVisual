'use strict';

/* login */
function login(req, res) {
    if (req.headers.flow == "mobile")
        res.status(200).send({
            "username": req.body.username,
            "flow": req.headers.flow
        });
    else if (req.headers.flow == "management")
        res.status(200).send({
            "username": req.body.username,
            "flow": req.headers.flow
        });
    else
        res.status(400).send("invalid flow");
}

/* authenticate */
function authenticate(req, res, next) {
    var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        res.status(401).send('Unauthorized');
        return;
    }

    req.login = token;

    next();
}

/* exports */
module.exports.login = login;
module.exports.authenticate = authenticate;