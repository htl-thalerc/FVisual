'use strict';

/* node modules */
const oracledb = require('oracledb');

/* oracle configuration */
oracledb.autoCommit = true;
oracledb.outFormat = oracledb.OUT_FORMAT_OBJECT;
oracledb.queueTimeout = 3000;
//const usn = `rel`;
//const pwd = `rel`;

//Sandro testing
const usn = `scott`;
const pwd = `tiger`;
const cStr = `192.168.43.205:1521/ora11g`;

//const usn = `d5a20`;
//const pwd = `d5a`;

//intern backup
//const cStr = `192.168.193.243:1521/ora11g`;

//intern
//const cStr = `10.0.6.111:1521/ora11g`;

//extern
//const cStr = `212.152.179.117:1521/ora11g`;

/* single execute */
var execute = async function(querystring, params, cbf) {
    let connection;
    try {
        connection = await oracledb.getConnection({
            user: usn,
            password: pwd,
            connectString: cStr
        }, (err, connection) => {
            if (err) {
                cbf(err);
            } else {
                connection.execute(
                    querystring, params, cbf
                );
            }
        });
    } catch (err) {
        cbf(err);
    } finally {
        if (connection) {
            try {
                await connection.close();
            } catch (err) {
                cbf(err);
            }
        }
    }
}

/* multiple execute */
var bulk_execute = null;

/* exports */
module.exports.execute = execute;
module.exports.bulk_execute = bulk_execute;