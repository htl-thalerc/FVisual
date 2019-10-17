class Mitglied {
    constructor(id, dienstgrad, vorname, nachname, stuetzpunkt, username, password, isAdmin) {
        this.id = id;
        this.dienstgrad = dienstgrad;
        this.vorname = vorname;
        this.nachname = nachname;
        this.stuetzpunkt = stuetzpunkt;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}

module.exports = Mitglied;