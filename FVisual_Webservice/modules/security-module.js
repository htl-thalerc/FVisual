'use strict';

/* login */
function login(req, res) {
    res.status(200).send(req.body.username);
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