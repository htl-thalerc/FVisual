'use strict';

/* node modules */
const oracledb = require('oracledb');

/* oracle configuration */
oracledb.autoCommit = true;
oracledb.outFormat = oracledb.OUT_FORMAT_OBJECT;
const usn = `d5a20`;
const pwd = `d5a`;
const cStr = `10.0.6.111:1521/ora11g`;

/* single execute */
var execute = async function (querystring, params, cbf) {
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