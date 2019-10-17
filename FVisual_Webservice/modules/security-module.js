'use strict';

function login(req, res) {
    res.status(200).send(req.body.username);
}

function logout(req, res) {

    var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        res.status(401).send('Unauthorized');
        return;
    }

    res.status(200).send();
}

function authenticate(req, res, next) {
    var token = req.headers['authorization'];

    if (token == undefined || token == '') {
        res.status(401).send('Unauthorized');
        return;
    }

    req.login = token;

    next();
}

module.exports.login = login;
module.exports.logout = logout;
module.exports.authenticate = authenticate;